package com.willnguyen.strings;

import java.util.Scanner;

public class HasSubstring {

    public static void main(String[] args) {
        // Write a program that checks whether the given string contains the given substring
        // Input: racecar
        //        car
        // Output: true

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String string = scanner.next();

            System.out.print("Enter substring: ");
            final String substring = scanner.next();

            System.out.println(string.contains(substring));
        }
    }

}
