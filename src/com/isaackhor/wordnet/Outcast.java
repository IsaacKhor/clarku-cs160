package com.isaackhor.wordnet;

public class Outcast {
    private WordNet wn;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null)
            throw new IllegalArgumentException();
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int n = nouns.length;
        int[] dists = new int[n];

        for (int i = 0; i < n; i++) {
            // These boundaries because the matrix is symmetric
            // d(i,j) == d(j,i)
            for (int j = 0; j < i; j++) {
                String n1 = nouns[i];
                String n2 = nouns[j];
                int dist = wn.distance(n1, n2);
                dists[i] += dist;
                dists[j] += dist;
            }
        }

        String outcast = nouns[0];
        int minDist = dists[0];
        for (int i = 0; i < n; i++) {
            if (dists[i] > minDist) {
                outcast = nouns[i];
                minDist = dists[i];
            }
        }

        return outcast;
    }
}
