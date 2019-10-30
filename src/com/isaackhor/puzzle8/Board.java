package com.isaackhor.puzzle8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int[][] tiles;
    private int size;
    private int hammingDist;
    private int manhattanDist;
    private int emptyRow;
    private int emptyCol;
    private boolean isSolveable;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException();

        this.tiles = tiles;
        this.size = tiles.length;

        int[] linearisedCopy = new int[size * size - 1];
        int linIdx = 0;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int tile = tiles[r][c];
                if (tile == 0) {
                    emptyRow = r;
                    emptyCol = c;
                    // Skip over distances because we're not counting the blank
                    continue;
                }
                // Put this here because we don't want to include the blank
                // when we count inversions
                linearisedCopy[linIdx++] = tile;

                int correctRow = (tile - 1) / size;
                int correctCol = (tile - 1) % size;
                int man = manDist(r, c, correctRow, correctCol);
                manhattanDist += man;
                // If manhattan is 0, then square is in the right place
                // so don't add to hamming
                hammingDist += man != 0 ? 1 : 0;
            }
        }

        int inversions = countInversions(linearisedCopy);
        if (size % 2 == 1)
            // size is odd, solveable iff inversions is even
            isSolveable = inversions % 2 == 0;
        else
            // size is even, so solveable iff (inversions + emptyRow) is odd
            isSolveable = (inversions + emptyRow) % 2 == 1;
    }

    private int manDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    // string representation of this board
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(size);
        sb.append('\n');
        for (int[] row : tiles) {
            for (int tile : row) {
                sb.append(String.format("%2d ", tile));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size)
            throw new IllegalArgumentException();
        return tiles[row][col];
    }

    // board size n
    public int size() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hammingDist == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Board))
            return false;
        return Arrays.deepEquals(tiles, ((Board) other).tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> ret = new ArrayList<>(4);

        // Can move empty slot up, slide tile down
        if (emptyRow != 0) {
            int[][] newTiles = Arrays.copyOf(tiles, size);
            // To move empty slot up, we need to clone and modify the row with
            // the empty slot and the row above; all others can use the same
            // arrays without cloning
            newTiles[emptyRow - 1] = Arrays.copyOf(tiles[emptyRow - 1], size);
            newTiles[emptyRow] = Arrays.copyOf(tiles[emptyRow], size);

            // Actually move the value
            int movedVal = newTiles[emptyRow - 1][emptyCol];
            newTiles[emptyRow][emptyCol] = movedVal;
            newTiles[emptyRow - 1][emptyCol] = 0;

            ret.add(new Board(newTiles));
        }

        // Can move empty slot down, slide tile up
        if (emptyRow < size - 1) {
            int[][] newTiles = Arrays.copyOf(tiles, size);
            newTiles[emptyRow] = Arrays.copyOf(tiles[emptyRow], size);
            newTiles[emptyRow + 1] = Arrays.copyOf(tiles[emptyRow + 1], size);

            // Actually move the value
            int movedVal = newTiles[emptyRow + 1][emptyCol];
            newTiles[emptyRow][emptyCol] = movedVal;
            newTiles[emptyRow + 1][emptyCol] = 0;

            ret.add(new Board(newTiles));
        }

        // Can move empty slot left, slide tile right
        if (emptyCol != 0) {
            int[][] newTiles = Arrays.copyOf(tiles, size);
            newTiles[emptyRow] = Arrays.copyOf(tiles[emptyRow], size);

            // Actually move the value
            int movedVal = newTiles[emptyRow][emptyCol - 1];
            newTiles[emptyRow][emptyCol] = movedVal;
            newTiles[emptyRow][emptyCol - 1] = 0;

            ret.add(new Board(newTiles));
        }

        // Can move empty slot right, slide tile left
        if (emptyCol < size - 1) {
            int[][] newTiles = Arrays.copyOf(tiles, size);
            newTiles[emptyRow] = Arrays.copyOf(tiles[emptyRow], size);

            // Actually move the value
            int movedVal = newTiles[emptyRow][emptyCol + 1];
            newTiles[emptyRow][emptyCol] = movedVal;
            newTiles[emptyRow][emptyCol + 1] = 0;

            ret.add(new Board(newTiles));
        }

        return ret;
    }

    // is this board solvable?
    public boolean isSolvable() {
        return isSolveable;
    }

    // Count number of inversions in arr
    // Will modify it, so please pass in a copy
    private static int countInversions(int[] arr) {
        // Modified mergesort
        // Every time we merge two arrays together, we eliminate inversions,
        // so we just count them as they are sorted out
        if (arr.length <= 1)
            return 0;

        int mid = (arr.length + 1) / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        return countInversions(left)
                + countInversions(right)
                + countAndMerge(arr, left, right);
    }

    // This will modify the dest array
    // Requires no duplicates
    // returns number of inversions merged out
    private static int countAndMerge(int[] dest, int[] left, int[] right) {
        int i = 0, j = 0, ret = 0;
        while (i < left.length || j < right.length) {
            if (i >= left.length)
                dest[i + j] = right[j++];
            else if (j >= right.length)
                dest[i + j] = left[i++];
            else if (left[i] < right[j])
                dest[i + j] = left[i++];
            else {
                // There are len-i elements greater than right[j] in left that
                // are in the wrong position
                ret += left.length - i;
                dest[i + j] = right[j++];
            }
        }

        return ret;
    }

    // Required for API compliance
    public static void main(String[] args) {
    }
}
