package com.github.yakupovdev.mst.alg;


import com.github.yakupovdev.mst.model.Edge;
import com.github.yakupovdev.mst.model.Graph;
import com.github.yakupovdev.mst.model.MSTResult;
import com.github.yakupovdev.mst.util.OperationCounter;

import java.util.*;

public class MSTPrim {
    public MSTResult run(Graph g, String start) {
        OperationCounter counter = new OperationCounter();
        long t0 = System.nanoTime();

        if (g.V() == 0) {
            MSTResult r = new MSTResult();
            r.setMst_edges(Collections.emptyList());
            r.setTotal_cost(0);
            r.setOperations_count(0);
            r.setExecution_time_ms(0.0);
            return r;
        }

        if (start == null) start = g.getNodes().iterator().next();

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        List<Edge> mst = new ArrayList<>();

        visited.add(start);
        List<Edge> initial = g.getAdjacency().getOrDefault(start, Collections.emptyList());
        pq.addAll(initial);
        counter.addComparisons(initial.size());

        while (!pq.isEmpty() && visited.size() < g.V()) {
            Edge e = pq.poll();
            counter.incComparisons();
            String next = visited.contains(e.getFrom()) ? e.getTo() : e.getFrom();
            if (visited.contains(next)) continue;
            visited.add(next);
            mst.add(e);
            counter.incRelaxations();
            for (Edge ne : g.getAdjacency().getOrDefault(next, Collections.emptyList())) {
                String other = ne.other(next);
                if (!visited.contains(other)) {
                    pq.add(ne);
                    counter.incPushes();
                }
            }
        }

        long t1 = System.nanoTime();
        MSTResult r = new MSTResult();
        r.setMst_edges(mst);
        r.setTotal_cost(mst.stream().mapToInt(Edge::getWeight).sum());
        r.setOperations_count(counter.totalOperations());
        r.setExecution_time_ms((t1 - t0) / 1_000_000.0);
        return r;
    }
}



