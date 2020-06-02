import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private final int[][] grid;
  private int count = 0;

  private final WeightedQuickUnionUF network;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Grid size should be more than zero!");
    }
    grid = new int[n][n];
    network = new WeightedQuickUnionUF(n * n + 2);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = -1;
      }
    }
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    validate(row, col);
    if (!isOpen(row, col)) {
      row--;
      col--;
      int elementIndex = count + 1;
      grid[row][col] = elementIndex;
      if (row == 0) {
        network.union(0, elementIndex);
      }
      if (row == grid.length - 1) {
        network.union(grid.length * grid.length + 1, elementIndex);
      }
      if (row > 0 && grid[row - 1][col] > 0) {
        network.union(grid[row - 1][col], elementIndex);
      }
      if (row < grid.length - 1 && grid[row + 1][col] > 0) {
        network.union(grid[row + 1][col], elementIndex);
      }
      if (col > 0 && grid[row][col - 1] > 0) {
        network.union(grid[row][col - 1], elementIndex);
      }
      if (col < grid.length - 1 && grid[row][col + 1] > 0) {
        network.union(grid[row][col + 1], elementIndex);
      }
      count++;
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    validate(row, col);
    return grid[row - 1][col - 1] > 0;
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    validate(row, col);
    if (isOpen(row, col)) {
      row--;
      col--;
      int elementIndex = grid[row][col];
      return network.connected(0, elementIndex);
    } else {
      return false;
    }
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return count;
  }

  // does the system percolate?
  public boolean percolates() {
    return network.connected(0, grid.length * grid.length + 1);
  }

  // test client (optional)
  public static void main(String[] args) {
    // not used
  }

  private void validate(int row, int col) {
    if (row <= 0 || col <= 0 || row > grid.length || col > grid.length) {
      throw new IllegalArgumentException("Both row and column should be in range of 1 and n!");
    }
  }
}