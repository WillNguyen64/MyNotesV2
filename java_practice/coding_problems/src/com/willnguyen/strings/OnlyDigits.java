package com.willnguyen.strings;

import java.util.Scanner;

public class OnlyDigits {

    public static void main(String[] args) {
        // Write a program that checks if a string only contains digits
        // Input: 12345
        // output: true

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();

            boolean onlyDigits = isOnlyDigitsV2(input);

            System.out.println(onlyDigits);
        }

    }

    private static boolean isOnlyDigitsV1(String input) {
        boolean onlyDigits = true;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                onlyDigits = false;
                break;
            }
        }
        return onlyDigits;
    }

    private static boolean isOnlyDigitsV2(String input) {
        // Slower solution
        return !input.chars().anyMatch(n -> !Character.isDigit(n));
    }
}