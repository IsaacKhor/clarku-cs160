package com.isaackhor.wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShortestCommonAncestorTest {
    private Digraph d25;
    private Digraph withCycle;
    private Digraph noCycle;
    private ShortestCommonAncestor sca;
    private List<Integer> l1 = Arrays.asList(13, 23, 24);
    private List<Integer> l2 = Arrays.asList(6, 16, 17);
    private List<Integer> containsNull = Arrays.asList(6, 16, 17, null);

    @BeforeAll
    void setUp() {
        d25 = new Digraph(new In("input/digraph25.txt"));
        withCycle = new Digraph(new In("input/hasCycle.txt"));
        noCycle = new Digraph(new In("input/noCycle.txt"));
        sca = new ShortestCommonAncestor(d25);
    }

    @Test
    void constructor() {
        assertThrows(IllegalArgumentException.class, () ->
                new ShortestCommonAncestor(null));
        assertThrows(IllegalArgumentException.class, () ->
                new ShortestCommonAncestor(withCycle));
        assertDoesNotThrow(() -> new ShortestCommonAncestor(noCycle));
        assertDoesNotThrow(() -> new ShortestCommonAncestor(d25));
    }

    @Test
    void length() {
        assertThrows(IllegalArgumentException.class, () ->
                sca.length(-1, 1));

        assertEquals(2, sca.length(13, 14));
        assertEquals(4, sca.length(13, 16));
        assertEquals(9, sca.length(13, 24));

        // One node is the ancestor of another
        assertEquals(1, sca.length(13, 7));
        assertEquals(1, sca.length(7, 13));
        assertEquals(0, sca.length(7, 7));
    }

    @Test
    void ancestor() {
        assertThrows(IllegalArgumentException.class, () ->
                sca.length(-1, 1));

        assertEquals(7, sca.ancestor(13, 14));
        assertEquals(3, sca.ancestor(13, 16));
        assertEquals(0, sca.ancestor(13, 24));

        // One node is the ancestor of another
        assertEquals(7, sca.ancestor(13, 7));
        assertEquals(7, sca.ancestor(7, 13));
        assertEquals(7, sca.ancestor(7, 7));
    }

    @Test
    void lengthSubset() {
        assertThrows(IllegalArgumentException.class, () ->
                sca.lengthSubset(Collections.EMPTY_LIST, l1));
        assertThrows(IllegalArgumentException.class, () ->
                sca.lengthSubset(containsNull, l1));

        assertEquals(4, sca.lengthSubset(l1, l2));
    }

    @Test
    void ancestorSubset() {
        assertThrows(IllegalArgumentException.class, () ->
                sca.ancestorSubset(Collections.EMPTY_LIST, l1));
        assertThrows(IllegalArgumentException.class, () ->
                sca.ancestorSubset(containsNull, l1));

        assertEquals(3, sca.ancestorSubset(l1, l2));
    }
}