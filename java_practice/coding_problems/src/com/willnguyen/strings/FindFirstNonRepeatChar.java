package com.willnguyen.strings;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindFirstNonRepeatChar {
    public static void main(String[] args) {
        // Write a program that returns the first non-repeated character from a given string
        // Assumptions:
        //   - string is case sensitive, so that 'A' and 'a' are considered different chars
        //   - string can contain spaces, which counts as a character

        // Input: "sesame street"
        // Output: a

        FindFirstNonRepeatCharV2();
    }

    public static void FindFirstNonRepeatCharV1() {
        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();

            final Map<Character, Integer> charCounts = new LinkedHashMap<>();
            for (int i = 0; i < input.length(); i++) {
                final char c = input.charAt(i);
                charCounts.compute(c, (k, v) -> (v == null) ? 1 : ++v);
            }

            Optional<Character> firstNonRepeatChar = Optional.empty();
            for (char c : charCounts.keySet()) {
                if (charCounts.get(c) == 1) {
                    firstNonRepeatChar = Optional.of(c);
                    break;
                }
            }

            if (firstNonRepeatChar.isPresent()) {
                System.out.println(firstNonRepeatChar);
            } else {
                System.out.println("No non-repeating char found");
            }
        }
    }

    public static void FindFirstNonRepeatCharV2() {
        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();

            Optional<Character> firstNonRepeatChar = input.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() == 1L)
                    .findFirst()
                    .map(entry -> entry.getKey());

            if (firstNonRepeatChar.isPresent()) {
                System.out.println(firstNonRepeatChar);
            } else {
                System.out.println("No non-repeating char found");
            }
        }
    }
}
