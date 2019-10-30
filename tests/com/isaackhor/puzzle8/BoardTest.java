package com.isaackhor.puzzle8;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board b1 = new Board(new int[][]{
            {1, 0, 3},
            {4, 2, 5},
            {7, 8, 6}
    });
    private Board b1_left = new Board(new int[][]{
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
    });
    private Board b1_right = new Board(new int[][]{
            {1, 3, 0},
            {4, 2, 5},
            {7, 8, 6}
    });
    private Board b1_down = new Board(new int[][]{
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
    });

    private Board b2 = new Board(new int[][]{
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
    });
    private Board b2_left = new Board(new int[][]{
            {8, 1, 3},
            {0, 4, 2},
            {7, 6, 5}
    });
    private Board b2_right = new Board(new int[][]{
            {8, 1, 3},
            {4, 2, 0},
            {7, 6, 5}
    });
    private Board b2_up = new Board(new int[][]{
            {8, 0, 3},
            {4, 1, 2},
            {7, 6, 5}
    });
    private Board b2_down = new Board(new int[][]{
            {8, 1, 3},
            {4, 6, 2},
            {7, 0, 5}
    });

    private Board solved = new Board(new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    });

    private Board unsolveable = new Board(new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
    });

    @Test
    void constructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Board(null);
        });
    }

    @Test
    void testToString() {
        assertEquals("3\n 1  0  3 \n 4  2  5 \n 7  8  6 \n", b1.toString());
        assertEquals("3\n 8  1  3 \n 4  0  2 \n 7  6  5 \n", b2.toString());
    }

    @Test
    void tileAt() {
        assertEquals(1, b1.tileAt(0, 0));
        assertEquals(0, b1.tileAt(0, 1));
        assertEquals(3, b1.tileAt(0, 2));
        assertEquals(4, b1.tileAt(1, 0));
        assertEquals(2, b1.tileAt(1, 1));
        assertEquals(5, b1.tileAt(1, 2));
        assertEquals(7, b1.tileAt(2, 0));
        assertEquals(8, b1.tileAt(2, 1));
        assertEquals(6, b1.tileAt(2, 2));

        assertThrows(IllegalArgumentException.class, () -> {
            b1.tileAt(-1, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            b1.tileAt(1, 3);
        });
    }

    @Test
    void size() {
        assertEquals(3, b1.size());
        assertEquals(3, b2.size());
    }

    @Test
    void hamming() {
        assertEquals(3, b1.hamming());
        assertEquals(5, b2.hamming());
    }

    @Test
    void manhattan() {
        assertEquals(3, b1.manhattan());
        assertEquals(10, b2.manhattan());
    }

    @Test
    void isGoal() {
        assertFalse(b1.isGoal());
        assertFalse(b1_down.isGoal());
        assertFalse(b1_left.isGoal());
        assertFalse(b1_right.isGoal());

        assertFalse(b2.isGoal());
        assertFalse(b2_up.isGoal());
        assertFalse(b2_down.isGoal());
        assertFalse(b2_left.isGoal());
        assertFalse(b2_right.isGoal());

        assertTrue(solved.isGoal());
    }

    @Test
    void neighbors() {
        List<Board> b1n = (List<Board>) b1.neighbors();
        assertEquals(b1_down, b1n.get(0));
        assertEquals(b1_left, b1n.get(1));
        assertEquals(b1_right, b1n.get(2));

        List<Board> b2n = (List<Board>) b2.neighbors();
        assertEquals(b2_up, b2n.get(0));
        assertEquals(b2_down, b2n.get(1));
        assertEquals(b2_left, b2n.get(2));
        assertEquals(b2_right, b2n.get(3));
    }

    @Test
    void isSolvable() {
        assertTrue(b1.isSolvable());
        assertTrue(b2.isSolvable());
        assertFalse(unsolveable.isSolvable());
    }
}