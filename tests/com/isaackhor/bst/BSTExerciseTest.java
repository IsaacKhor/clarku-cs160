package com.isaackhor.bst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BSTExerciseTest {

    private BSTExercise<Integer, Object> b1 = new BSTExercise<>();
    private BSTExercise<Integer, Object> b2 = new BSTExercise<>();
    private BSTExercise<Integer, Object> b3 = new BSTExercise<>();
    private BSTExercise<Integer, Object> bempty = new BSTExercise<>();
    private Integer[] t1 = {0, 1, 2};
    private Integer[] t2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private Integer[] t3 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    @BeforeEach
    void setUp() {
        b1.balancedBuild(t1);
        b2.balancedBuild(t2);
        b3.balancedBuild(t3);
    }

    @Test
    void depth() {
        assertEquals(1, b1.depth(0));
        assertEquals(0, b1.depth(1));
        assertEquals(1, b1.depth(2));

        assertThrows(IllegalArgumentException.class, () -> b1.depth(null));
        assertEquals(-1, b1.depth(100));
        assertDoesNotThrow(() -> bempty.get(100));
    }

    @Test
    void leaves() {
        ArrayList<Integer> b1l = (ArrayList<Integer>) b1.leaves();
        assertArrayEquals(t1, b1l.toArray());

        ArrayList<Integer> b2l = (ArrayList<Integer>) b2.leaves();
        assertArrayEquals(t2, b2l.toArray());

        ArrayList<Integer> b3l = (ArrayList<Integer>) b3.leaves();
        assertArrayEquals(t3, b3l.toArray());

        assertNull(bempty.leaves());
    }

    @Test
    void testToString() {
        String t1str = "1\n" +
                "  0\n" +
                "  2\n";
        assertEquals(t1str, b1.toString());

        String t2str = "4\n" +
                "  1\n" +
                "    0\n" +
                "    2\n" +
                "      -\n" +
                "      3\n" +
                "  7\n" +
                "    5\n" +
                "      -\n" +
                "      6\n" +
                "    8\n" +
                "      -\n" +
                "      9\n";
        assertEquals(t2str, b2.toString());

        String t3str = "7\n" +
                "  3\n" +
                "    1\n" +
                "      0\n" +
                "      2\n" +
                "    5\n" +
                "      4\n" +
                "      6\n" +
                "  11\n" +
                "    9\n" +
                "      8\n" +
                "      10\n" +
                "    13\n" +
                "      12\n" +
                "      14\n";
        assertEquals(t3str, b3.toString());

        assertEquals("-\n", bempty.toString());
    }

    @Test
    void balancedBuild() {
        assertThrows(IllegalArgumentException.class,
                () -> b1.balancedBuild(null));
        assertDoesNotThrow(() -> bempty.balancedBuild(new Integer[]{}));

        assertEquals(3, b1.root.size);
        assertEquals(1, b1.root.left.size);
        assertEquals(1, b1.root.right.size);
        assertEquals(0, b1.root.left.key);

        assertEquals(15, b3.root.size);
        assertEquals(7, b3.root.left.size);
        assertEquals(3, b3.root.left.left.size);
        assertEquals(3, b3.root.left.right.size);
        assertEquals(7, b3.root.right.size);
        assertEquals(3, b3.root.right.left.size);
        assertEquals(3, b3.root.right.right.size);
    }
}
