package com.isaackhor.autocomplete;

import com.isaackhor.autocomplete.Autocomplete;
import com.isaackhor.autocomplete.Term;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AutocompleteTest {
    private Autocomplete wiktionary;
    private Autocomplete allSimilar;
    private Term[] allSimilarTerms;

    @BeforeEach
    void setUp() {
        In in = new In("wiktionary.txt");
        int n = in.readInt();
        Term[] terms = new Term[n];
        for(int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }
        wiktionary = new Autocomplete(terms);

        Term testingTerm = new Term("this is a query", 10);
        allSimilarTerms = new Term[10];
        Arrays.fill(allSimilarTerms, testingTerm);
        allSimilar = new Autocomplete(allSimilarTerms);
    }

    @Test
    void allMatches() {
        Term[] wik = wiktionary.allMatches("helm");
        Term[] wikRes = {
                new Term("helm", 754109),
                new Term("helmet", 736546)
        };
        assertArrayEquals(wikRes, wik);

        Term[] sim = allSimilar.allMatches("this");
        assertArrayEquals(allSimilarTerms, sim);

        Term[] sim2 = allSimilar.allMatches("");
        assertArrayEquals(allSimilarTerms, sim2);
    }

    @Test
    void numberOfMatches() {
        Term[] wik = wiktionary.allMatches("helm");
        assertEquals(2, wik.length);

        Term[] sim = allSimilar.allMatches("this");
        assertEquals(10, sim.length);

        Term[] sim2 = allSimilar.allMatches("");
        assertEquals(10, sim2.length);
    }
}