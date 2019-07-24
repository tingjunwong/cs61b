package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    /* @source reference from  https://github.com/Zhenye-Na/
    *cs61b-ucb/blob/master/hw/hw2/hw2/Percolation.java
    * just have a reference about the Percolation class setting and constructor setting ,
     * giving me a little inspiration
    * */
    private WeightedQuickUnionUF WQU;
    private WeightedQuickUnionUF backwash;
    private int numOfOpenSites;
    private int head;
    private int tail;
    private int N;

    public Percolation(int N) {
        this.N = N;
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[N][N];
        backwash = new WeightedQuickUnionUF(N * N + 1);
        WQU = new WeightedQuickUnionUF(N * N + 2);
        numOfOpenSites = 0;
        head = N * N;
        tail = N * N + 1;


    }
    private int xyTo1D(int row, int col) {
        return N * row + col;
    }
        /* @source reference from  https://github.com/peleusj/cs61b/
        *blob/master/sp18/hw2/hw2/Percolation.java
        * The original open method has severe problems on idex out of bound exception ,
        * fixed it by adding the bounding condition.Now it works!
        *
        * */
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            numOfOpenSites += 1;
        }
        if (row == 0) {
            WQU.union(xyTo1D(row, col), head);
            backwash.union(xyTo1D(row, col), head);
        }
        if (row == N - 1) {
            WQU.union(xyTo1D(row, col), tail);

        }

        if (col != 0 && isOpen(row, col - 1)) {
            WQU.union(xyTo1D(row, col - 1), xyTo1D(row, col));
            backwash.union(xyTo1D(row, col - 1), xyTo1D(row, col));
        }
        if (col != N - 1 && isOpen(row, col + 1)) {
            WQU.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            backwash.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        if (row != 0 && isOpen(row - 1, col)) {
            WQU.union(xyTo1D(row - 1, col), xyTo1D(row, col));
            backwash.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        }
        if (row != N - 1 && isOpen(row + 1, col)) {
            WQU.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            backwash.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
    }



    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }
    public boolean isFull(int row, int col) {
        validate(row, col);
        return backwash.connected(head, xyTo1D(row, col));
    }


    public int numberOfOpenSites() {
        return numOfOpenSites;
    }
    private void validate(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        }
    }
    public boolean percolates() {
        return WQU.connected(head, tail);
    }
    public static void main(String[] args) {
            // use for unit testing (not required, but keep this here for the autograder)
    }
}
