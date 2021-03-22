package com.willnguyen.strings;

import java.util.*;

public class SortArrayByLength {

    public static void main(String[] args) {

        // Write a program that sorts the strings in array by length

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter strings to join: ");
            final List<String> strings = new ArrayList<>();
            while (scanner.hasNext()) {
                String next = scanner.next();
                if (next.isEmpty()) break;
                strings.add(next);
            }

            Collections.sort(strings, Comparator.comparingInt(String::length));
            // To return a new sorted array, use Arrays.stream(strs).sorted(Comparator.comparingInt(String::length))
            System.out.println(strings);
        }
    }

}
