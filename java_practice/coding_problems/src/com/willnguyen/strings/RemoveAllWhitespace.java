package com.willnguyen.strings;

import java.util.Scanner;

public class RemoveAllWhitespace {

    public static void main(String[] args) {
        // Write a program that removes all whitespace from a string

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();
            final String trimmedInput = input.replaceAll("\\s", "");
            System.out.println(trimmedInput);
        }
    }
}
