package com.isaackhor.wordnet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OutcastTest {
    private Outcast oc = new Outcast(new WordNet(
            "input/synsets.txt", "input/hypernyms.txt"));

    private String[] o1 = {"horse", "zebra", "cat", "bear", "table"};
    private String[] o2 = {"water", "soda", "bed", "orange_juice", "milk",
            "apple_juice", "tea", "coffee"};
    private String[] o3 = {"apple", "pear", "peach", "banana", "lime", "lemon",
            "blueberry", "strawberry", "mango", "watermelon", "potato"};

    @Test
    void outcast() {
        assertEquals("table", oc.outcast(o1));
        assertEquals("bed", oc.outcast(o2));
        assertEquals("potato", oc.outcast(o3));
    }
}