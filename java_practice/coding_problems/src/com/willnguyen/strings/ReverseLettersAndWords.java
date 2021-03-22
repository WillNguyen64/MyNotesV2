package com.willnguyen.strings;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReverseLettersAndWords {
    // Write a program that reverses the letters of each word
    // Input: sesame street
    // Output: emases teerts

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();

            final String wordsLettersReversed = reverseV2(input);
            System.out.println(String.join(" ", wordsLettersReversed));
        }

    }

    private static String reverseV1(String input) {
        final String[] words = input.split("\\s+");
        final StringBuilder sb = new StringBuilder();

        for (String word : words) {
            // Reverse the letters in the word
            for (int j = word.length() - 1; j >= 0; j--) {
                sb.append(word.charAt(j));
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    private static final Pattern PATTERN = Pattern.compile(" +");

    private static String reverseV2(String input) {
        return PATTERN.splitAsStream(input)
                .map(w -> new StringBuilder(w).reverse())
                .collect(Collectors.joining(" "));
    }
}
