package com.github.yakupovdev.mst.model;

import java.util.List;

public class GraphInput {
    private int id;
    private List<String> nodes;
    private List<Edge> edges;

    public GraphInput() {}

    public int getId() { return id; }
    public List<String> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }

    public void setId(int id) { this.id = id; }
    public void setNodes(List<String> nodes) { this.nodes = nodes; }
    public void setEdges(List<Edge> edges) { this.edges = edges; }
}


