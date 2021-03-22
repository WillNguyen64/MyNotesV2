package com.willnguyen.strings;

import java.util.Scanner;
import java.util.regex.Pattern;

public class RemoveOccurrencesOfChar {
    public static void main(String[] args) {

        // Write a program that removes the occurrences of the given char
        // Input: sesame
        //        s
        // Output: eame

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();
            System.out.print("Enter char to remove from string: ");
            final String charToRemove = scanner.next();

            System.out.println(input.replaceAll(Pattern.quote(charToRemove), ""));
        }

    }

}
