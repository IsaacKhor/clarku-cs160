package com.isaackhor.percolation;

import edu.princeton.cs.algs4.*;

public class PercolationStats {
    private double[] thresholds;
    private double trials;

    // Cached
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if(n<=0 || trials<=0)
            throw new IllegalArgumentException();

        thresholds = new double[trials];
        this.trials = trials;

        for(int i=0; i<trials; i++) {
            // System.out.printf("Trial %d: ", i);
            Percolation p = new Percolation(n);

            while(!p.percolates()) {
                int x = StdRandom.uniform(n);
                int y = StdRandom.uniform(n);
                p.open(x,y);
            }
            int open = p.numberOfOpenSites();
            double threshold = (double)open / (n*n);
            thresholds[i] = threshold;
            // System.out.printf("open %d, threshold %f\n", open, threshold);
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
    }

    // sample mean of percolation threshold
    public double mean() { return mean; }

    // sample standard deviation of percolation threshold
    public double stddev() { return stddev; }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean + ((1.96 * stddev) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean - ((1.96 * stddev) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        // System.out.printf("size %d trials %d\n", size, trials);
        Stopwatch sw = new Stopwatch();
        PercolationStats stats = new PercolationStats(size, trials);
        double elapsedTime = sw.elapsedTime();
        System.out.printf("mean()           = %f\n", stats.mean());
        System.out.printf("stddev()         = %f\n", stats.stddev());
        System.out.printf("confidenceLow()  = %f\n", stats.confidenceLow());
        System.out.printf("confidenceHigh() = %f\n", stats.confidenceHigh());
        System.out.printf("elapsedTime      = %f\n", elapsedTime);
    }
}
