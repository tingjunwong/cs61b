package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
public class PercolationStats {
    private double[] WQU;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        WQU = new double[T];
        /* @source reference from  https://github.com/aatifjiwani/CS61B-Spring-2018/blob/
        *master/Homework/hw2/hw2/PercolationStats.java*/
        for (int i = 0; i < T; i++) {
            Percolation perc = pf.make(N);
            WQU[i] = generateStat(perc, N);
        }
    }
    /* @source reference from  https://github.com/aatifjiwani/CS61B-Spring-2018/
     *blob/master/Homework/hw2/hw2/PercolationStats.java
    *  I admitted it's kind of embarrassing  not to finish the  constructor part by my own thinking,
     *  but I finally understood the thinking, I must say I have studied a lot ,
    *
    *
    * */
    private double generateStat(Percolation p, int N) {
        while (!p.percolates()) {
            int row = StdRandom.uniform(0, N);
            int col = StdRandom.uniform(0, N);
            p.open(row, col);
        }

        return (double) p.numberOfOpenSites() / (N * N);
    }

      // perform T independent experiments on an N-by-N grid
    public double mean() {
        return StdStats.mean(WQU);

    }                                           // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(WQU);

    }                                        // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / (Math.sqrt(WQU.length));

    }                                 // low endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / (Math.sqrt(WQU.length));

    }                                // high endpoint of 95% confidence interval
}
