package com.isaackhor.puzzle8;

import edu.princeton.cs.algs4.MinPQ;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solver {
    private List<Board> solution = new ArrayList<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null || !initial.isSolvable())
            throw new IllegalArgumentException();

        MinPQ<SNode> queue = new MinPQ<>(15);
        queue.insert(new SNode(initial, 0, null));

        // A* search
        SNode curNode = queue.delMin();
        while (!curNode.brd.isGoal()) {
            Board curBrd = curNode.brd;
            int nextDepth = curNode.depth + 1;

            for (Board b : curBrd.neighbors()) {
                if (curNode.prev != null && b.equals(curNode.prev.brd))
                    continue;
                queue.insert(new SNode(b, nextDepth, curNode));
            }

            curNode = queue.delMin();
        }

        SNode n = curNode;
        while (n != null) {
            solution.add(n.brd);
            n = n.prev;
        }
        Collections.reverse(solution);
    }

    // min number of moves to solve initial board
    public int moves() {
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        String path = args[0];
        Scanner sc;
        try {
            sc = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found", path);
            return;
        }

        int n = Integer.parseInt(sc.next());
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = Integer.parseInt(sc.next());
            }
        }

        Board initial = new Board(tiles);
        Solver solver;
        try {
            solver = new Solver(initial);
        } catch (IllegalArgumentException e) {
            System.out.printf("\n%s\nUnsolveable puzzle", initial);
            return;
        }
        System.out.printf("Moves required: %d\n", solver.moves());
        for (Board b : solver.solution()) {
            System.out.println(b);
        }
    }

    private static class SNode implements Comparable<SNode> {
        Board brd;
        int depth;
        SNode prev;
        int prio;

        SNode(Board brd, int depth, SNode prev) {
            this.brd = brd;
            this.depth = depth;
            this.prev = prev;
            this.prio = depth + brd.manhattan();
        }

        @Override
        public int compareTo(SNode o) {
            return this.prio - o.prio;
        }
    }
}
