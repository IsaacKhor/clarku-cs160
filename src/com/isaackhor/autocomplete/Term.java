package com.isaackhor.autocomplete;

import java.util.Comparator;

public class Term implements Comparable<Term> {
    private String query;
    private long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if(query == null || weight < 0)
            throw new IllegalArgumentException();

        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return (Term a, Term b) -> (int) (b.weight - a.weight);
    }

    private String upTo(int end) {
        int trueEnd = Math.min(query.length(), end);
        return query.substring(0, trueEnd);
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if(r<0) throw new IllegalArgumentException();
        return (Term a, Term b) -> {
            String sa = a.upTo(r);
            String sb = b.upTo(r);
            return sa.compareTo(sb);
        };
    }

    // Compares the two terms in lexicographic order by query.
    @Override
    public int compareTo(Term that) { return query.compareTo(that.query); }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    @Override
    public String toString() {
        return String.format("%d\t%s", weight, query);
    }

    @Override
    public boolean equals(Object other) {
        if(other == null || !Term.class.isAssignableFrom(other.getClass()))
            return false;
        Term b = (Term) other;
        return query.equals(b.query) && (weight == b.weight);
    }

    // unit testing (required)
    public static void main(String[] args) {}
}
