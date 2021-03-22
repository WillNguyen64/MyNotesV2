package com.willnguyen.strings;

import java.util.Scanner;

public class CountOccurrenceOfChar {

    public static void main(String[] args) {

        // Write a program that counts number of occurrences of a certain char
        // Input: aabcda
        //        a
        // Output: 3

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();
            System.out.print("Enter char to count number of occurrences of: ");
            final char charToCount = scanner.next().charAt(0);

            int count = 0;

            for (int i = 0; i < input.length(); i++) {
                final char c = input.charAt(i);
                if (c == charToCount) {
                    count++;
                }
            }

            System.out.println(count);
        }

    }
}
