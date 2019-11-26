package com.isaackhor.wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private Digraph net;
    private Map<Integer, String> idToSet = new HashMap<>();
    private Map<String, List<Integer>> wordToIds;
    private ShortestCommonAncestor sca;

    public WordNet(String synsetsPath, String hypernymsPath) {
        if (synsetsPath == null || hypernymsPath == null)
            throw new IllegalArgumentException();

        In synsetsFile = new In(synsetsPath);
        In hypernymsFile = new In(hypernymsPath);

        Map<String, List<Integer>> wordToIdTmp = new HashMap<>();
        while (synsetsFile.hasNextLine()) {
            String[] synset = synsetsFile.readLine().split(",");

            int id = Integer.parseInt(synset[0]);
            String set = synset[1];

            idToSet.put(id, set);
            for (String s : set.split(" ")) {
                List<Integer> ids = wordToIdTmp.computeIfAbsent(s, (unused) ->
                        new ArrayList<>());
                ids.add(id);
                wordToIdTmp.put(s, ids);
            }
        }
        wordToIds = wordToIdTmp;
        net = new Digraph(idToSet.size());

        while (hypernymsFile.hasNextLine()) {
            String[] ids = hypernymsFile.readLine().split(",");

            int from = Integer.parseInt(ids[0]);
            for (int i = 1; i < ids.length; i++) {
                int to = Integer.parseInt(ids[i]);
                net.addEdge(from, to);
            }
        }

        sca = new ShortestCommonAncestor(net);
    }


    // all WordNet nouns
    public Iterable<String> nouns() {
        return wordToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return wordToIds.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null || !isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException();

        List<Integer> id1 = wordToIds.get(noun1);
        List<Integer> id2 = wordToIds.get(noun2);
        int scaId = sca.ancestorSubset(id1, id2);
        return idToSet.get(scaId);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null || !isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException();

        List<Integer> id1 = wordToIds.get(noun1);
        List<Integer> id2 = wordToIds.get(noun2);
        return sca.lengthSubset(id1, id2);
    }
}
