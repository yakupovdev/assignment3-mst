package com.github.yakupovdev.mst.model;

import java.util.*;

public class Graph {
    private final LinkedHashSet<String> nodes = new LinkedHashSet<>();
    private final ArrayList<Edge> edges = new ArrayList<>();
    private final Map<String, List<Edge>> adj = new HashMap<>();

    public Graph() {}

    public void addNode(String v) {
        if (!nodes.contains(v)) {
            nodes.add(v);
            adj.put(v, new ArrayList<>());
        }
    }

    public void addEdge(Edge e) {
        addNode(e.getFrom());
        addNode(e.getTo());
        edges.add(e);
        adj.get(e.getFrom()).add(e);
        adj.get(e.getTo()).add(e);
    }

    public Set<String> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }
    public Map<String, List<Edge>> getAdjacency() { return adj; }
    public int V() { return nodes.size(); }
    public int E() { return edges.size(); }
}
