package com.isaackhor.puzzle8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    private Solver b1 = new Solver(new Board(new int[][]{
            {1, 0, 3},
            {4, 2, 5},
            {7, 8, 6}
    }));
    private Solver b2 = new Solver(new Board(new int[][]{
            {1, 2, 3, 4},
            {5, 6, 0, 8},
            {9, 10, 7, 11},
            {13, 14, 15, 12}
    }));
    private Board unsolveable = new Board(new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
    });

    @Test
    void constructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Solver(unsolveable);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Solver(null);
        });
    }

    @Test
    void moves() {
        assertEquals(3, b1.moves());
        assertEquals(3, b2.moves());
    }

    @Test
    void solution() {
    }
}