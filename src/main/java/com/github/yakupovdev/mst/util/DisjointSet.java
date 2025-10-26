package com.github.yakupovdev.mst.util;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet<T> {
    private final Map<T, T> parent = new HashMap<>();
    private final Map<T, Integer> rank = new HashMap<>();
    private final OperationCounter counter;

    public DisjointSet(OperationCounter counter) {
        this.counter = counter;
    }

    public void makeSet(T x) {
        parent.put(x, x);
        rank.put(x, 0);
    }

    public T find(T x) {
        T p = parent.get(x);
        if (p == null) return null;
        if (!p.equals(x)) {
            T r = find(p);
            parent.put(x, r);
            return r;
        }
        return p;
    }

    public boolean union(T a, T b) {
        counter.incUnions();
        T ra = find(a), rb = find(b);
        if (ra == null || rb == null) return false;
        if (ra.equals(rb)) return false;
        int rka = rank.get(ra);
        int rkb = rank.get(rb);
        if (rka < rkb) parent.put(ra, rb);
        else if (rka > rkb) parent.put(rb, ra);
        else { parent.put(rb, ra); rank.put(ra, rka + 1); }
        return true;
    }
}



