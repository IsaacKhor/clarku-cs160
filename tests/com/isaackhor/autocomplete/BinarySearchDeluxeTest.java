package com.isaackhor.autocomplete;

import com.isaackhor.autocomplete.BinarySearchDeluxe;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchDeluxeTest {
    private Integer[] testdata = {0,1,2,3,4,5,5,6,7,8,9};
    private Integer[] allSame = {5,5,5,5,5,5,5,5,5,5};
    private Integer[] withNull = {0,1,2,3,4,null,5,6,7,8,9};
    private Comparator<Integer> nat = Comparator.naturalOrder();

    @Test
    void firstIndexOf() {
        assertEquals(
                5,
                BinarySearchDeluxe.firstIndexOf(testdata, 5, nat)
        );
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            BinarySearchDeluxe.firstIndexOf(withNull, 5, nat);
        });
        assertEquals(0, BinarySearchDeluxe.firstIndexOf(allSame, 5, nat));
    }

    @Test
    void lastIndexOf() {
        assertEquals(
                6,
                BinarySearchDeluxe.lastIndexOf(testdata, 5, nat)
        );
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            BinarySearchDeluxe.lastIndexOf(withNull, 5, nat);
        });
        assertEquals(9, BinarySearchDeluxe.lastIndexOf(allSame, 5, nat));
    }
}