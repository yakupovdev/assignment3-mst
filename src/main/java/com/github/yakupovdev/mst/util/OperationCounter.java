package com.github.yakupovdev.mst.util;

public class OperationCounter {
    private long comparisons = 0;
    private long unions = 0;
    private long relaxations = 0;
    private long pushes = 0;

    public void incComparisons() { comparisons++; }
    public void incUnions() { unions++; }
    public void incRelaxations() { relaxations++; }
    public void incPushes() { pushes++; }

    public void addComparisons(long v) { comparisons += v; }
    public void addUnions(long v) { unions += v; }

    public long getComparisons() { return comparisons; }
    public long getUnions() { return unions; }
    public long getRelaxations() { return relaxations; }
    public long getPushes() { return pushes; }

    public long totalOperations() {
        return comparisons + unions + relaxations + pushes;
    }
}

