package com.willnguyen.strings;

import java.util.Scanner;

public class IsPalindrome {

    public static void main(String[] args) {
        // Write a program that determines whether the given string is a palindrome or not
        // Input: racecar
        // Output: true

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();

            // If original string and the reverse are the same, they are palindromes.
            final StringBuilder sb = new StringBuilder();

            for (int i = input.length() - 1; i >= 0; i--) {
                sb.append(input.charAt(i));
            }

            final String reverse = sb.toString();
            System.out.println((input.equals(reverse)));
        }
    }

}
