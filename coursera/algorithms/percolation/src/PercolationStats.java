import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private static final double CONFIDENCE_RATE = 1.96;
  private final double[] result;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("Both n and trials should be more than zero!");
    }
    result = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation percolation = new Percolation(n);
      while (!percolation.percolates()) {
        int row = StdRandom.uniform(1, n+1);
        int col = StdRandom.uniform(1, n+1);
        percolation.open(row, col);
      }
      result[i] = ((double) percolation.numberOfOpenSites()) / (n * n);
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(result);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(result);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - ((CONFIDENCE_RATE) / Math.sqrt((double) result.length));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + ((CONFIDENCE_RATE) / Math.sqrt((double) result.length));
  }

  // test client (see below)
  public static void main(String[] args) {
    int gridSize = StdIn.readInt();
    int trialsNumber = StdIn.readInt();
    PercolationStats stats = new PercolationStats(gridSize, trialsNumber);
    StdOut.print("mean = ");
    StdOut.println(stats.mean());
    StdOut.print("stddev = ");
    StdOut.println(stats.stddev());
    StdOut.print("95% confidence interval = [");
    StdOut.print(stats.confidenceLo());
    StdOut.print(", ");
    StdOut.print(stats.confidenceHi());
    StdOut.println("]");
  }

}