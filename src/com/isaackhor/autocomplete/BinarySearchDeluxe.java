package com.isaackhor.autocomplete;

import java.util.Comparator;

public class BinarySearchDeluxe {
    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key,
                                         Comparator<Key> comparator) {
        if(a == null || key == null || comparator == null)
            throw new IllegalArgumentException();

        int low = 0;
        int high = a.length - 1;

        while(low <= high) {
            int mid = (low + high) >>> 1;
            Key midval = a[mid];
            if(midval == null)
                throw new IllegalArgumentException();
            int comp = comparator.compare(key, midval);

            if(comp < 0) // key < midval, so chop off top half
                high = mid - 1;
            else if(comp > 0) // key > midval, so chop off bottom half
                low = mid + 1;
            else if(low != mid) // found key, chop off top half
                high = (int) Math.ceil((low + high) / 2.0);
            else
                return mid;
        }

        return -1;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key,
                                        Comparator<Key> comparator) {
        if(a == null || key == null || comparator == null)
            throw new IllegalArgumentException();

        int low = 0;
        int high = a.length - 1;

        while(low <= high) {
            int mid = (int) Math.ceil((low + high) / 2.0);
            Key midval = a[mid];
            if(midval == null)
                throw new IllegalArgumentException();
            int comp = comparator.compare(key, midval);

            if(comp < 0) // key < midval, so chop off top half
                high = mid - 1;
            else if(comp > 0) // key > midval, so chop off bottom half
                low = mid + 1;
            else if(high != mid) // found it, chop off bottom half
                low = (low + high) >>> 1;
            else
                return mid;
        }

        return -1;
    }
}
