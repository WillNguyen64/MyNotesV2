package com.willnguyen.strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CountDuplicateChars {
    public static void main(String[] args) {
        // Write a program that counts duplicate chars from a given string
        // Assumptions:
        //   - string is case sensitive, so that 'A' and 'a' are considered different chars
        //   - string can contain spaces, which counts as a character

        // Input: "sesame street"
        // Output: 3

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();
            final Map<Character, Long> charCounts = countDuplicatesV2(input);

            int duplicateCharCount = 0;
            for (Long count : charCounts.values()) {
                if (count > 1) duplicateCharCount++;
            }
            System.out.println(duplicateCharCount);
        }
    }

    public static  Map<Character, Long> countDuplicatesV1(String input) {
        final Map<Character, Long> charCounts = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            // For Unicode, use codePointAt() instead of charAt()
            // See pp 13 of Java Coding Problems book
            final char c = input.charAt(i);
            charCounts.compute(c, (k, v) -> (v == null) ? 1 : ++v);
        }
        return charCounts;
    }

    public static Map<Character, Long> countDuplicatesV2(String input) {
        // For Unicode, use codePoints() instead of chars()
        // See pp 13 of Java Coding Problems book
        final Map<Character, Long> charCounts = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return charCounts;
    }
}
