package com.isaackhor.wordnet;

import edu.princeton.cs.algs4.Digraph;

import java.util.*;

public class ShortestCommonAncestor {
    private Digraph g;

    // Pre-allocate memory, because the Java compiler is dumb
    private boolean[] visited;
    private int[] marked;

    public ShortestCommonAncestor(Digraph g) {
        if (g == null)
            throw new IllegalArgumentException();

        this.g = g;

        // Check for cycles via topological sort
        List<Integer> roots = new LinkedList<>();
        boolean[] perm = new boolean[g.V()];
        boolean[] temp = new boolean[g.V()];
        for (int i = 0; i < g.V(); i++) {
            if (g.outdegree(i) == 0)
                roots.add(i);
            partialTopSort(perm, temp, i);
        }

        // Check for rootedness
        if (roots.size() != 1)
            throw new IllegalArgumentException("Graph not rooted");
    }

    // DFS through the graph to look for cycles
    private void partialTopSort(boolean[] perm, boolean[] temp, int node) {
        if (perm[node]) return;
        if (temp[node])
            throw new IllegalArgumentException("Found a cycle");

        temp[node] = true;
        for (int n : g.adj(node))
            partialTopSort(perm, temp, n);

        temp[node] = false;
        perm[node] = true;
    }

    // Build ancestor depth-map, or a map of the minimum distance before
    // reaching thot node from any of the origin nodes
    private Map<Integer, Integer> buildDmap(Iterable<Integer> origins) {
        Map<Integer, Integer> dmap = new HashMap<>();
        Queue<Integer> queue = new ArrayDeque<>();

        for (Integer origin : origins) {
            queue.add(origin);
            dmap.put(origin, 0);
        }

        while (!queue.isEmpty()) {
            Integer node = queue.remove();

            int depth = dmap.get(node);
            for (Integer neighbour : g.adj(node)) {
                // If we have previously visited this neighbour; skip it this
                // time around if the path we're currently exploring is
                // actually longer than the one we've already found
                if (depth >= dmap.getOrDefault(neighbour, Integer.MAX_VALUE))
                    continue;
                dmap.put(neighbour, depth + 1);
                queue.add(neighbour);
            }
        }

        return dmap;
    }

    // This will return the first LCA found. If there are multiple found, the
    // rest are ignored
    private LcaSearchRes search(
            Map<Integer, Integer> adm,
            Iterable<Integer> origins
    ) {
        Map<Integer, Integer> curAdm = new HashMap<>();
        Queue<Integer> queue = new ArrayDeque<>();
        List<LcaSearchRes> candidates = new ArrayList<>();

        for (Integer orig : origins) {
            queue.add(orig);
            curAdm.put(orig, 0);

            if (adm.containsKey(orig))
                // origin = destination, so we're already done
                return new LcaSearchRes(adm.get(orig), orig);
        }

        while (!queue.isEmpty()) {
            Integer node = queue.remove();
            int depth = curAdm.get(node);

            for (Integer neighbour : g.adj(node)) {
                // Same reasoning: if this path is longer, then skip
                if (depth >= curAdm.getOrDefault(neighbour, Integer.MAX_VALUE))
                    continue;

                // We found a shared common ancestor, so add it to the
                // candidates list and then skip
                if (adm.containsKey(neighbour)) {
                    int totalDepth = adm.get(neighbour) + depth + 1;
                    candidates.add(new LcaSearchRes(totalDepth, neighbour));
                    continue;
                }

                curAdm.put(neighbour, depth + 1);
                queue.add(neighbour);
            }
        }

        // Find the candidate with the least length
        LcaSearchRes min = candidates.get(0);
        for (LcaSearchRes candidate : candidates)
            if (candidate.totalLength < min.totalLength)
                min = candidate;

        return min;
    }


    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        validateNode(v);
        validateNode(w);
        return lengthSubset(
                Collections.singletonList(v),
                Collections.singletonList(w)
        );
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        validateNode(v);
        validateNode(w);
        return ancestorSubset(
                Collections.singletonList(v),
                Collections.singletonList(w)
        );
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA,
                            Iterable<Integer> subsetB) {
        validateNodes(subsetA);
        validateNodes(subsetB);
        Map<Integer, Integer> adm = buildDmap(subsetA);
        LcaSearchRes res = search(adm, subsetB);
        return res.totalLength;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA,
                              Iterable<Integer> subsetB) {
        validateNodes(subsetA);
        validateNodes(subsetB);
        Map<Integer, Integer> adm = buildDmap(subsetA);
        LcaSearchRes res = search(adm, subsetB);
        return res.ancestorNode;
    }

    private void validateNodes(Iterable<Integer> nodes) {
        Iterator<Integer> it = nodes.iterator();
        if (!it.hasNext())
            throw new IllegalArgumentException("Empty iterable");
        while (it.hasNext())
            validateNode(it.next());
    }

    private void validateNode(Integer node) {
        if (node == null || node < 0 || node >= g.V())
            throw new IllegalArgumentException();
    }

    private static class LcaSearchRes {
        final int totalLength;
        final int ancestorNode;

        private LcaSearchRes(int len, int node) {
            this.totalLength = len;
            this.ancestorNode = node;
        }
    }
}

