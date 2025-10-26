package com.github.yakupovdev.mst.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Edge {
    private final String from;
    private final String to;
    private final int weight;

    @JsonCreator
    public Edge(@JsonProperty("from") String from,
                @JsonProperty("to") String to,
                @JsonProperty("weight") int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public int getWeight() { return weight; }

    public String other(String node) {
        return node.equals(from) ? to : from;
    }

    @Override
    public String toString() {
        return String.format("%s-%s:%d", from, to, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Edge)) return false;
        Edge e = (Edge)o;
        return this.weight == e.weight &&
                ((this.from.equals(e.from) && this.to.equals(e.to)) ||
                        (this.from.equals(e.to) && this.to.equals(e.from)));
    }

    @Override
    public int hashCode() {
        int a = from.hashCode(), b = to.hashCode();
        int min = Math.min(a,b), max = Math.max(a,b);
        return Objects.hash(min, max, weight);
    }
}

