package com.willnguyen.strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class JoinMultipleStrings {
    public static void main(String[] args) {
        // Write a program that joins multiple strings with a delimiter

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter delimiter: ");
            String delim = scanner.next();

            System.out.print("Enter strings to join: ");
            System.out.println(joinV2(delim, scanner));

        }
    }

    private static String joinV1(String delim, Scanner scanner) {
        final List<String> strings = new ArrayList<>();
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (next.isEmpty()) break;
            strings.add(next);
        }
        return String.join(delim, strings);
    }

    private static String joinV2(String delim, Scanner scanner) {
        final StringJoiner stringJoiner = new StringJoiner(delim);
        while (scanner.hasNext()) {
            String next = scanner.next();
            stringJoiner.add(next);
        }
        return stringJoiner.toString();
    }

}
