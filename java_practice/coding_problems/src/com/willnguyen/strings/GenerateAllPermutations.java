package com.willnguyen.strings;

import java.util.*;

public class GenerateAllPermutations {

    public static void main(String[] args) {
        // Write a program that generates all of the permutations of the given string
        // The total number of permutations is equal to N! where N is the string length.
        // Input: abc
        // Output: abc, acb, bac, bca, cab, cba

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();
            System.out.println(permutations(input));
        }
    }

    public static Set<String> permutations(String input) {

        final Set<String> result = new HashSet<>();

        // Base case: if input size is 1, return the string.
        if (input.length() == 1) {
            result.add(input);
        } else {
            // Recursive step: if input size is N > 1:
            //    - Generate permutations of size N by taking each letter and appending it
            //      to the permutations of length N-1 generated from the other letters.

            for (int i = 0; i < input.length(); i++) {
                final char c = input.charAt(i);
                Set<String> permutationsWithoutChar;
                if (i == 0) {
                    permutationsWithoutChar = permutations(input.substring(1));
                } else if (i == input.length() - 1) {
                    permutationsWithoutChar = permutations(input.substring(0, input.length() - 1));
                } else {
                    permutationsWithoutChar = permutations(input.substring(0, i) + input.substring(i + 1));
                }

                for (String permutationWithoutChar : permutationsWithoutChar) {
                    result.add(c + permutationWithoutChar);
                }
            }
        }
        return result;
    }
}
