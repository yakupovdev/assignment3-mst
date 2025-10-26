package com.github.yakupovdev.mst.model;

public class GraphResult {
    private int graph_id;
    private InputStats input_stats;
    private MSTResult prim;
    private MSTResult kruskal;

    public static class InputStats {
        public int vertices;
        public int edges;
    }

    public GraphResult() {}

    public int getGraph_id() { return graph_id; }
    public InputStats getInput_stats() { return input_stats; }
    public MSTResult getPrim() { return prim; }
    public MSTResult getKruskal() { return kruskal; }

    public void setGraph_id(int graph_id) { this.graph_id = graph_id; }
    public void setInput_stats(InputStats input_stats) { this.input_stats = input_stats; }
    public void setPrim(MSTResult prim) { this.prim = prim; }
    public void setKruskal(MSTResult kruskal) { this.kruskal = kruskal; }
}
