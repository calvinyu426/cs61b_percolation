package hw2;


import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private int numberOfExpeiments;
    private Percolation perc;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        numberOfExpeiments = T;
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            perc = new Percolation(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                perc.open(row, col);
            }
            thresholds[i] = (double) perc.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLow() {
        return (mean() - 1.96 * stddev() / Math.sqrt(numberOfExpeiments));
    }

    public double confidenceHigh() {
        return (mean() + 1.96 * stddev() / Math.sqrt(numberOfExpeiments));
    }
    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(5, 200);
        System.out.println(p.mean());
    }
}
