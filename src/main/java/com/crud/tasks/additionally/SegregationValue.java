package com.crud.tasks.additionally;

import java.util.*;

public class SegregationValue {
    public static void main(String[] args) {
        int[] a = {3, 1, 1, 5, 6, 4};
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

        Integer[] b = {3, 1, 1, 5, 6, 4};
        Arrays.sort(b);
        Arrays.sort(b, Comparator.reverseOrder());
        System.out.println(Arrays.toString(b));
        Arrays.sort(b, Comparator.naturalOrder());
        System.out.println(Arrays.toString(b));

        List<Integer> c = Arrays.asList(3, 1, 1, 5, 6, 4);
        Collections.sort(c);
        System.out.println(c);
    }
}
