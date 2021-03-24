package com.willnguyen.data_structures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShuffleArray {

    public static void main(String[] args) {
        // Shuffle an array

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Collections.shuffle(integers);
        System.out.println(integers);

    }
}
