package com.willnguyen.strings;

import java.util.Arrays;
import java.util.Scanner;

public class IsAnagram {

    public static void main(String[] args) {
        // Write a program that checks whether two strings are anagrams
        // Input: gainly
        //        laying
        // Output: true

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string1: ");
            final String string1 = scanner.next();

            System.out.print("Enter string2: ");
            final String string2 = scanner.next();

            // If the sorted versions of both strings are equal, they are anagrams
            char[] sorted1 = string1.toCharArray();
            Arrays.sort(sorted1);
            char[] sorted2 = string2.toCharArray();
            Arrays.sort(sorted2);

            System.out.println((Arrays.equals(sorted1, sorted2)));
        }
    }

}
