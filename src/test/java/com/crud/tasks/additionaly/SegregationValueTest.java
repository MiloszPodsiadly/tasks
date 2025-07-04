package com.crud.tasks.additionaly;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SegregationValueTest {

    @Test
    void shouldSortIntArrayInAscendingOrder() {
        // given
        int[] array = {3, 1, 1, 5, 6, 4};

        // when
        Arrays.sort(array);

        // then
        assertArrayEquals(new int[]{1, 1, 3, 4, 5, 6}, array);
    }

    @Test
    void shouldSortIntegerArrayInDescendingOrder() {
        // given
        Integer[] array = {3, 1, 1, 5, 6, 4};

        // when
        Arrays.sort(array, Comparator.reverseOrder());

        // then
        assertArrayEquals(new Integer[]{6, 5, 4, 3, 1, 1}, array);
    }

    @Test
    void shouldSortIntegerListInAscendingOrder() {
        // given
        List<Integer> list = Arrays.asList(3, 1, 1, 5, 6, 4);

        // when
        Collections.sort(list);

        // then
        assertEquals(Arrays.asList(1, 1, 3, 4, 5, 6), list);
    }

    @Test
    void shouldSortIntegerListInDescendingOrder() {
        // given
        List<Integer> list = Arrays.asList(3, 1, 1, 5, 6, 4);

        // when
        list.sort(Comparator.reverseOrder());

        // then
        assertEquals(Arrays.asList(6, 5, 4, 3, 1, 1), list);
    }
}