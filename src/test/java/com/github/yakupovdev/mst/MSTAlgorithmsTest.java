package com.github.yakupovdev.mst;

import com.github.yakupovdev.mst.alg.MSTKruskal;
import com.github.yakupovdev.mst.alg.MSTPrim;
import com.github.yakupovdev.mst.model.*;
import com.github.yakupovdev.mst.util.JSONUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MSTAlgorithmsTest {
    private Graph buildGraph(GraphInput gi) {
        Graph g = new Graph();
        if (gi.getNodes() != null) for (String n : gi.getNodes()) g.addNode(n);
        if (gi.getEdges() != null) for (Edge e : gi.getEdges()) g.addEdge(e);
        return g;
    }

    private boolean isAcyclic(List<Edge> edges, Set<String> nodes) {
        Map<String, String> parent = new HashMap<>();
        for (String n : nodes) parent.put(n, n);
        java.util.function.Function<String, String> find = new java.util.function.Function<>() {
            public String apply(String x) {
                String p = parent.get(x);
                if (p.equals(x)) return x;
                String r = apply(p);
                parent.put(x, r);
                return r;
            }
        };
        for (Edge e : edges) {
            String ra = find.apply(e.getFrom());
            String rb = find.apply(e.getTo());
            if (ra.equals(rb)) return false;
            parent.put(ra, rb);
        }
        return true;
    }

    private boolean isConnected(List<Edge> edges, Set<String> nodes) {
        if (nodes.isEmpty()) return true;
        Map<String, List<String>> adj = new HashMap<>();
        for (String n : nodes) adj.put(n, new ArrayList<>());
        for (Edge e : edges) {
            adj.get(e.getFrom()).add(e.getTo());
            adj.get(e.getTo()).add(e.getFrom());
        }
        Set<String> vis = new HashSet<>();
        Deque<String> dq = new ArrayDeque<>();
        String start = nodes.iterator().next();
        dq.add(start); vis.add(start);
        while (!dq.isEmpty()) {
            String u = dq.poll();
            for (String v : adj.get(u)) if (!vis.contains(v)) { vis.add(v); dq.add(v); }
        }
        return vis.size() == nodes.size();
    }

    @Test
    public void correctnessAndPerformanceTests() throws Exception {
        JSONInput ai = JSONUtil.readAssignmentInput(new File("input/small_input.json"));
        ai.getGraphs().addAll(JSONUtil.readAssignmentInput(new File("input/medium_input.json")).getGraphs());
        ai.getGraphs().addAll(JSONUtil.readAssignmentInput(new File("input/large_input.json")).getGraphs());

        for (GraphInput gi : ai.getGraphs()) {
            Graph g = buildGraph(gi);

            MSTPrim prim = new MSTPrim();
            MSTKruskal kr = new MSTKruskal();

            MSTResult p = prim.run(g, g.getNodes().isEmpty() ? null : g.getNodes().iterator().next());
            MSTResult k = kr.run(g);

            assertEquals(p.getTotal_cost(), k.getTotal_cost(), "Total cost must match for graph id=" + gi.getId());

            if (p.getMst_edges().size() == g.V() - 1) {
                assertEquals(g.V() - 1, p.getMst_edges().size());
                assertTrue(isAcyclic(p.getMst_edges(), g.getNodes()));
                assertTrue(isConnected(p.getMst_edges(), g.getNodes()));
            }

            if (k.getMst_edges().size() == g.V() - 1) {
                assertEquals(g.V() - 1, k.getMst_edges().size());
                assertTrue(isAcyclic(k.getMst_edges(), g.getNodes()));
                assertTrue(isConnected(k.getMst_edges(), g.getNodes()));
            }

            assertTrue(p.getExecution_time_ms() >= 0.0);
            assertTrue(k.getExecution_time_ms() >= 0.0);

            assertTrue(p.getOperations_count() >= 0);
            assertTrue(k.getOperations_count() >= 0);
        }
    }

    @Test
    public void disconnectedGraphHandled() {
        Graph g = new Graph();
        g.addNode("A"); g.addNode("B"); g.addNode("C"); g.addNode("D");
        g.addEdge(new Edge("A","B",1));
        g.addEdge(new Edge("C","D",2));
        MSTKruskal kr = new MSTKruskal();
        MSTResult k = kr.run(g);
        assertNotEquals(g.V() - 1, k.getMst_edges().size());
    }
}


