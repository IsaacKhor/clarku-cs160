package com.isaackhor.bst;

import java.util.ArrayList;
import java.util.List;

public class BSTExercise<Key extends Comparable<Key>, Value>
        extends BST<Key, Value> {

    public int depth(Key key) {
        if (key == null)
            throw new IllegalArgumentException();

        Node cur = super.root;
        int depth = 0;
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp < 0) {
                cur = cur.left;
                depth++;
            } else if (cmp > 0) {
                cur = cur.right;
                depth++;
            } else {
                break;
            }
        }

        if (cur == null)
            return -1;
        return depth;
    }

    /**
     * Return all keys in sorted order. Completes in O(N)
     *
     * @return Iterable of keys in sorted order. null if root is null
     */
    public Iterable<Key> leaves() {
        if (super.root == null)
            return null;
        List<Key> ret = new ArrayList<Key>(super.size());
        retrieveInOrder(super.root, ret);
        return ret;
    }

    private void retrieveInOrder(Node n, List<Key> lst) {
        if (n == null)
            return;

        if (n.left != null)
            retrieveInOrder(n.left, lst);
        lst.add(n.key);
        if (n.right != null)
            retrieveInOrder(n.right, lst);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return nodeToStr(super.root, "", sb).toString();
    }

    private StringBuilder nodeToStr(Node n, String prefix, StringBuilder sb) {
        sb.append(prefix);
        if (n == null) {
            sb.append("-\n");
            return sb;
        } else if (n.left == null && n.right == null) {
            sb.append(n.key);
            sb.append('\n');
            return sb;
        } else {
            sb.append(n.key);
            sb.append('\n');
        }

        String newPrefix = prefix + "  ";
        sb = nodeToStr(n.left, newPrefix, sb);
        sb = nodeToStr(n.right, newPrefix, sb);
        return sb;
    }

    public void balancedBuild(Key[] a) {
        if (a == null)
            throw new IllegalArgumentException();

        if (a.length == 0) {
            super.root = null;
            return;
        }

        super.root = buildBalSubtree(a, 0, a.length - 1);
    }

    private Node buildBalSubtree(Key[] a, int startidx, int endidx) {
        if (startidx == endidx)
            return new Node(a[startidx], null, 1);
        else if (startidx > endidx)
            return null;

        int mid = (startidx + endidx) / 2;
        Node left = buildBalSubtree(a, startidx, mid - 1);
        Node right = buildBalSubtree(a, mid + 1, endidx);

        Node parent = new Node(a[mid], null, endidx - startidx + 1);
        parent.left = left;
        parent.right = right;

        return parent;
    }
}
