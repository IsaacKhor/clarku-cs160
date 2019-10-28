package com.isaackhor.percolation;

import java.util.*;

public class Percolation {
    private Site[][] grid;
    private int openSites = 0;
    private int size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        grid = new Site[n][n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                grid[i][j] = new Site(i, j);
            }
        }
        size = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        Site site = grid[row][col];
        if(!site.isOpen) openSites++;
        site.isOpen = true;

        // The algorithm: whenever we open a site, we check if one of its
        // neighbours have been marked as connected to the top. If they are,
        // then we do a DFS and label all nodes connected to the site as also
        // connected to the top. By definition, sites at the top row are
        // connected to the top.
        if(row == 0 || areNeighboursConnected(site))
            traverseAndLabel(site);

        return;
    }

    private boolean areNeighboursConnected(Site s) {
        List<Site> n = getOpenNeighbours(s.x,s.y);
        for(Site i : n) {
            if(i.isTopConnected) return true;
        }
        return false;
    }

    private List<Site> getOpenNeighbours(int x, int y) {
        List<Site> ret = new ArrayList<>(4);
        if(y+1<size && grid[x][y+1].isOpen) ret.add(grid[x][y+1]);
        if(y-1>=0   && grid[x][y-1].isOpen) ret.add(grid[x][y-1]);
        if(x+1<size && grid[x+1][y].isOpen) ret.add(grid[x+1][y]);
        if(x-1>=0   && grid[x-1][y].isOpen) ret.add(grid[x-1][y]);
        return ret;
    }

    private void traverseAndLabel(Site root) {
        // This is a depth-first traversal of the set
        // We check for all connected sites and repeat if they are neither
        // visited nor topConnected
        // Use an explicit stack, otherwise the jvm will stackoverflow with
        // big grids
        Stack<Site> stack = new Stack<>();
        Set<Site> visited = new HashSet<>();

        stack.push(root);
        while(!stack.empty()) {
            Site node = stack.pop();

            // Visit node on top of stack
            visited.add(node);
            node.isTopConnected = true;
            // System.out.printf("Visiting site %d %d\n", node.x, node.y);
            if(node.y == size-1) {
                // System.out.printf("Bottom %d %d\n", node.x, node.y);
            }

            // Push all non-connected and non-visited neighbours
            List<Site> neighbours = getOpenNeighbours(node.x, node.y);
            for(Site nb : neighbours) {
                if(visited.contains(nb) || nb.isTopConnected)
                    continue;
                stack.push(nb);
                // System.out.printf("Pushing %d %d\n", nb.x, nb.y);
            }
        }
    }

    public boolean isOpen(int row, int col) { return grid[row][col].isOpen; }
    public boolean isFull(int row, int col) { return grid[row][col].isTopConnected; }
    public int numberOfOpenSites() { return openSites; }

    public boolean percolates() {
        for(Site s : grid[size-1])
            if(s.isTopConnected) return true;
        return false;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.open(0,1);
        p.open(2,1);
        p.open(2,2);
        p.open(1,1);
        p.open(4,1);
        p.open(3,1);
        System.out.printf(
                "%b %b %b %d",
                p.isOpen(2,1),
                p.isFull(2,1),
                p.percolates(),
                p.numberOfOpenSites());
    }
}

class Site {
    boolean isOpen = false;
    boolean isTopConnected = false;
    int x,y;
    Site(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
