package com.willnguyen.strings;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountSubstringOccurrences {

    public static void main(String[] args) {
        // Write a program that counts the number of occurrences of a substring in another given string
        // Input: racecarcar
        //        car
        // Output: 2

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String string = scanner.next();

            System.out.print("Enter substring: ");
            final String substring = scanner.next();

            final Pattern pattern = Pattern.compile(Pattern.quote(substring));
            final Matcher matcher = pattern.matcher(string);
            int count = 0;
            while (matcher.find()) count++;
            System.out.println(count);
        }
    }
}
