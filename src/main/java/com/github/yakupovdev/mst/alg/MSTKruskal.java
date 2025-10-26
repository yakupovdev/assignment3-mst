package com.github.yakupovdev.mst.alg;


import com.github.yakupovdev.mst.model.Edge;
import com.github.yakupovdev.mst.model.Graph;
import com.github.yakupovdev.mst.model.MSTResult;
import com.github.yakupovdev.mst.util.DisjointSet;
import com.github.yakupovdev.mst.util.OperationCounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MSTKruskal {
    public MSTResult run(Graph g) {
        OperationCounter counter = new OperationCounter();
        long t0 = System.nanoTime();

        List<Edge> edges = new ArrayList<>(g.getEdges());
        Collections.sort(edges, (a,b) -> Integer.compare(a.getWeight(), b.getWeight()));
        counter.addComparisons(edges.size());

        DisjointSet<String> ds = new DisjointSet<>(counter);
        for (String v : g.getNodes()) ds.makeSet(v);

        List<Edge> mst = new ArrayList<>();
        for (Edge e : edges) {
            if (mst.size() == g.V() - 1) break;
            String ra = ds.find(e.getFrom());
            String rb = ds.find(e.getTo());
            counter.incComparisons();
            if (!ra.equals(rb)) {
                boolean joined = ds.union(e.getFrom(), e.getTo());
                if (joined) mst.add(e);
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


