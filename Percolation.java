package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] grid;
    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF ufTemp;
    private int rowLength;
    private int top;
    private int bottom;
    private int numberOfOpenSites = 0;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        grid = new int[N * N + 2];
        UF = new WeightedQuickUnionUF(N * N + 2);
        ufTemp = new WeightedQuickUnionUF(N * N + 2);
        top = N * N;
        bottom = N * N + 1;
        for (int i = 0; i < N * N; i++) {
            grid[i] = 0;
        }
        rowLength = N;
    }

    private int xyTo1D(int row, int col) {
        if (row >= rowLength || col >= rowLength || row < 0 || col < 0) {
            return (int) Math.pow(10, 10);
        }
        return row * rowLength + col;
    }

    public void open(int row, int col) {
        validate(row, col);
        int current = xyTo1D(row, col);

        if (row == 0) {
            UF.union(current, top);
            ufTemp.union(current, top);
        }
        if (row == rowLength - 1) {
            UF.union(current, bottom);
        }
        if (!isOpen(row, col)) {
            grid[current] = 1;
            numberOfOpenSites += 1;
        }
        union(row, col);
    }

    private void union(int row, int col) {
        int temp = xyTo1D(row, col);
        if ((col - 1) >= 0 && isOpen(row, col - 1)) {
            UF.union(xyTo1D(row, col - 1), temp);
            ufTemp.union(xyTo1D(row, col - 1), temp);
        }
        if ((col + 1 < rowLength) && isOpen(row, col + 1)) {
            UF.union(xyTo1D(row, col + 1), temp);
            ufTemp.union(xyTo1D(row, col + 1), temp);
        }
        if ((row - 1) >= 0 && isOpen(row - 1, col)) {
            UF.union(xyTo1D(row - 1, col), temp);
            ufTemp.union(xyTo1D(row - 1, col), temp);
        }
        if ((row + 1) < rowLength && isOpen(row + 1, col)) {
            UF.union(xyTo1D(row + 1, col), temp);
            ufTemp.union(xyTo1D(row + 1, col), temp);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return (grid[xyTo1D(row, col)] == 1);

    }

    public void validate(int row, int col) {
        if (row >= rowLength || col >= rowLength || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return (ufTemp.connected(top, xyTo1D(row, col)) && isOpen(row, col));
    }


    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return UF.connected(top, bottom);
    }

    public static void main(String[] args) {
    }

}

