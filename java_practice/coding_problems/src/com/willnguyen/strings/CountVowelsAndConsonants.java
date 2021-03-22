package com.willnguyen.strings;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class CountVowelsAndConsonants {

    private static final Set<Character> VOWELS =
            Arrays.asList('a', 'e', 'i', 'o', 'u').stream().collect(Collectors.toSet());

    public static void main(String[] args) {
        // Write a program that counts the number of vowels and consonants in a given string
        // Input: abcde
        // Output: numVowels: 2, numConsonants: 3

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();

            countVowelsV2(input);
        }

    }

    private static void countVowelsV1(String input) {
        String inputLC = input.toLowerCase();
        int numVowels = 0;
        int numConsonants = 0;

        for (int i = 0; i < inputLC.length(); i++) {
            final char c = inputLC.charAt(i);
            if (Character.isLetter(c)) {
                if (VOWELS.contains(c)) {
                    numVowels++;
                } else if ((c >= 'a' && c <= 'z')){
                    numConsonants++;
                }
            }
        }

        System.out.printf("numVowels: %s, numConsonants: %s", numVowels, numConsonants);
    }

    private static void countVowelsV2(String input) {
        String inputLC = input.toLowerCase();

        long numVowels = inputLC.chars()
                .filter(c -> VOWELS.contains((char) c))
                .count();

        long numConsonants = inputLC.chars()
                .filter(c -> !VOWELS.contains((char) c))
                .filter(c -> c >= 'a' && c <= 'z')
                .count();

        System.out.printf("numVowels: %s, numConsonants: %s", numVowels, numConsonants);

    }

    private static void countVowelsV3(String input) {
        String inputLC = input.toLowerCase();

        inputLC.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> !VOWELS.contains((char) c))
                .filter(c -> c >= 'a' && c <= 'z')
                .collect(Collectors.partitioningBy(c -> VOWELS.contains(c), Collectors.counting()));
    }
}