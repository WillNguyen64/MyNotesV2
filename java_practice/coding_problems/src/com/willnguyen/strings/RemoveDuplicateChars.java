package com.willnguyen.strings;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RemoveDuplicateChars {

    public static void main(String[] args) {
        // Write a program that removes the duplicate characters from the given string
        // Input: sesame
        // Output: seam

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();

            char[] chArray = input.toCharArray();
            StringBuilder sb = new StringBuilder();
            Set<Character> chHashSet = new HashSet<>();
            for (char c: chArray) {
                if (chHashSet.add(c)) {
                    sb.append(c);
                }
            }
            System.out.println(sb.toString());
        }
    }

}
