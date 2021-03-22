package com.willnguyen.strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Map.Entry.comparingByValue;

public class FindCharWithMostOccurrences {
    public static void main(String[] args) {

        // Write a program that finds the char with the most occurrences
        // Input: aabcda
        // Output: a

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();

            final Map<Character, Integer> charCounts = new HashMap<>();
            for (int i = 0; i < input.length(); i++) {
                final char c = input.charAt(i);
                charCounts.compute(c, (k, v) -> (v == null) ? 1 : ++v);
            }

            charCounts.entrySet().stream()
                      //.max(Comparator.comparing(Map.Entry::getValue))
                      .max(comparingByValue())
                      .ifPresent(
                              e -> System.out.println(e.getKey())
                      );
        }
    }
}
