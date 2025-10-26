package com.github.yakupovdev.mst;

import com.github.yakupovdev.mst.alg.MSTKruskal;
import com.github.yakupovdev.mst.alg.MSTPrim;
import com.github.yakupovdev.mst.model.*;
import com.github.yakupovdev.mst.util.CSVUtil;
import com.github.yakupovdev.mst.util.JSONUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] inputs = {
                "input/small_input.json",
                "input/medium_input.json",
                "input/large_input.json"
        };

        List<GraphResult> results = new ArrayList<>();
        for (String path : inputs) {
            JSONInput ai = JSONUtil.readAssignmentInput(new File(path));
            if (ai == null || ai.getGraphs() == null) continue;

            for (GraphInput gi : ai.getGraphs()) {
                Graph g = new Graph();
                if (gi.getNodes() != null) for (String n : gi.getNodes()) g.addNode(n);
                if (gi.getEdges() != null) for (Edge e : gi.getEdges()) g.addEdge(e);

                MSTPrim primAlg = new MSTPrim();
                MSTKruskal kruskalAlg = new MSTKruskal();

                MSTResult primRes = primAlg.run(g, gi.getNodes() != null && !gi.getNodes().isEmpty() ? gi.getNodes().get(0) : null);
                MSTResult krRes = kruskalAlg.run(g);

                GraphResult gr = new GraphResult();
                gr.setGraph_id(gi.getId());
                GraphResult.InputStats stats = new GraphResult.InputStats();
                stats.vertices = g.V();
                stats.edges = g.E();
                gr.setInput_stats(stats);
                gr.setPrim(primRes);
                gr.setKruskal(krRes);
                results.add(gr);
            }
        }

        File outDir = new File("output");
        if (!outDir.exists()) outDir.mkdirs();

        JSONUtil.writeResults(new File("output/output.json"), results);
        CSVUtil.writeResultsCsv(new File("output/results.csv"), results);

        System.out.println("Done. Output written to output/output.json and output/results.csv");
    }
}

