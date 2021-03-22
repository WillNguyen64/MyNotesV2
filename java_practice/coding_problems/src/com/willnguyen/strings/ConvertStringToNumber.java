package com.willnguyen.strings;

import java.util.Optional;
import java.util.Scanner;

public class ConvertStringToNumber {

    public static void main(String[] args) {
        // Write a program that converts a string into int, long, float or double

        try (Scanner scanner = new Scanner(System.in).useDelimiter(("\\n"))) {
            System.out.print("Enter string: ");
            final String input = scanner.next();

            Optional<Object> result = Optional.empty();
            try {
                int i = Integer.parseInt(input);
                result = Optional.of(i);
            } catch (NumberFormatException e) {
            }

            if (!result.isPresent()) {
                try {
                    long l = Long.parseLong(input);
                    result = Optional.of(l);
                } catch (NumberFormatException e) {
                }
            }

            if (!result.isPresent()) {
                try {
                    float f = Float.parseFloat(input);
                    result = Optional.of(f);
                } catch (NumberFormatException e) {
                }
            }

            if (!result.isPresent()) {
                try {
                    double d = Double.parseDouble(input);
                    result = Optional.of(d);
                } catch (NumberFormatException e) {
                }
            }

            if (result.isPresent()) {
                final Object val = result.get();
                System.out.printf("Converted to %s with value %s", val.getClass().getSimpleName(), val);
            } else {
                System.out.println("Failed to convert string as it's not a number");
            }
        }
    }
}
