package com.github.yakupovdev.mst.util;

import com.github.yakupovdev.mst.model.GraphResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtil {
    public static void writeResultsCsv(File out, List<GraphResult> results) throws IOException {
        try (FileWriter fw = new FileWriter(out)) {
            fw.write("graph_id,algorithm,vertices,edges,total_cost,operations,execution_time_ms\n");
            for (GraphResult gr : results) {
                fw.write(String.format("%d,Prim,%d,%d,%d,%d,%.6f\n",
                        gr.getGraph_id(),
                        gr.getInput_stats().vertices,
                        gr.getInput_stats().edges,
                        gr.getPrim().getTotal_cost(),
                        gr.getPrim().getOperations_count(),
                        gr.getPrim().getExecution_time_ms()));
                fw.write(String.format("%d,Kruskal,%d,%d,%d,%d,%.6f\n",
                        gr.getGraph_id(),
                        gr.getInput_stats().vertices,
                        gr.getInput_stats().edges,
                        gr.getKruskal().getTotal_cost(),
                        gr.getKruskal().getOperations_count(),
                        gr.getKruskal().getExecution_time_ms()));
            }
        }
    }
}

