package com.isaackhor.autocomplete;

import com.isaackhor.autocomplete.Term;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class TermTest {

    private Term test1 = new Term("test1", 1);
    private Term test2 = new Term("test2", 1);
    private Term test3 = new Term("test3", 5);

    @Test
    void byReverseWeightOrder() {
        Comparator<Term> cmp = Term.byReverseWeightOrder();
        assertEquals(cmp.compare(test1, test2), 0);
        assertEquals(cmp.compare(test1, test3), 4);
    }

    @Test
    void byPrefixOrder() {
        Comparator<Term> cmp = Term.byPrefixOrder(4);
        assertEquals(cmp.compare(test1, test2), 0);
        Comparator<Term> cmp2 = Term.byPrefixOrder(5);
        assertEquals(cmp2.compare(test1, test2), -1);
    }

    @Test
    void compareTo() {
        assertEquals(test1.compareTo(test2), -1);
    }

    @Test
    void testToString() {
        assertEquals(test1.toString(), "1\ttest1");
    }
}