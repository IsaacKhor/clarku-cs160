package com.isaackhor.autocomplete;

import java.util.*;
import edu.princeton.cs.algs4.*;

public class Autocomplete {
    private Term[] terms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if(terms == null || Arrays.asList(terms).contains(null))
            throw new IllegalArgumentException();
        this.terms = terms;
        Arrays.parallelSort(this.terms, Term::compareTo);
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        int prelen = prefix.length();
        Term dummy = new Term(prefix, 0);
        Comparator<Term> cmp = Term.byPrefixOrder(prelen);

        int firstIdx = BinarySearchDeluxe.firstIndexOf(terms, dummy, cmp);
        int lastIdx = BinarySearchDeluxe.lastIndexOf(terms, dummy, cmp);

        if(firstIdx == -1 || lastIdx == -1)
            return new Term[0];

        Term[] match = Arrays.copyOfRange(terms, firstIdx, lastIdx + 1);
        Arrays.sort(match, Term.byReverseWeightOrder());
        return match;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        int prelen = prefix.length();
        Term dummy = new Term(prefix, 0);
        Comparator<Term> cmp = Term.byPrefixOrder(prelen);

        int firstIdx = BinarySearchDeluxe.firstIndexOf(terms, dummy, cmp);
        int lastIdx = BinarySearchDeluxe.lastIndexOf(terms, dummy, cmp);

        if(firstIdx == -1 || lastIdx == -1)
            return 0;

        return lastIdx - firstIdx + 1;
    }

    public static void main(String[] args) {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);

        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("prompt> ");
            String prefix = sc.nextLine();
            Term[] results = autocomplete.allMatches(prefix);
            int numMatches = autocomplete.numberOfMatches(prefix);

            StdOut.printf("Found %d matches:\n", numMatches);
            for (int i = 0; i < Math.min(k, results.length); i++)
                System.out.println(results[i]);
            StdOut.println();
        }
    }
}
