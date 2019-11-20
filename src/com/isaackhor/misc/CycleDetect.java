package com.isaackhor.misc;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CycleDetect extends Digraph {
    public CycleDetect(In in) {
        super(in);
    }

    boolean hasCycle() {
        for (int i = 0; i < V(); i++) {
            if (search(i))
                return true;
        }
        return false;
    }

    boolean search(int node) {
        Stack<Integer> s = new Stack<>();
        boolean[] visited = new boolean[V()];
        Arrays.fill(visited, false);

        // Push neighbours of initial starting node
        for(int neighbour : adj(node)) {
            s.push(neighbour);
        }

        // Visit each node
        while(!s.empty()) {
            int cur = s.pop();
            visited[cur] = true;

            if(cur == node)
                return true;

            for(int neighbour : adj(cur)) {
                if(!visited[neighbour])
                    s.push(neighbour);
            }
        }

        return false;
    }

    int getRoot() {
        List<Integer> l = new ArrayList<>();
        for(int i=0; i<V(); i++) {
            if(indegree(i) == 0)
                l.add(i);
        }

        for(int node : l) {
            if(visitsAllNodes(node))
                return node;
        }

        return -1;
    }

    boolean visitsAllNodes(int node) {
        Stack<Integer> s = new Stack<>();
        boolean[] visited = new boolean[V()];
        Arrays.fill(visited, false);

        visited[node] = true;

        // Push neighbours of initial starting node
        for(int neighbour : adj(node)) {
            s.push(neighbour);
        }

        // Visit each node
        while(!s.empty()) {
            int cur = s.pop();
            visited[cur] = true;

            for(int neighbour : adj(cur)) {
                if(!visited[neighbour])
                    s.push(neighbour);
            }
        }

        boolean ret = true;
        for (boolean b : visited) {
            ret = ret && b;
        }

        return ret;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        CycleDetect cd = new CycleDetect(in);
        System.out.println(cd.hasCycle());
        System.out.println(cd.getRoot());
    }
}
