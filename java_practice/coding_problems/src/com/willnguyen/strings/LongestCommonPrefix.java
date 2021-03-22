package com.willnguyen.strings;

import java.util.Arrays;

public class LongestCommonPrefix {

    public static void main(String[] args) {
        // Write a program that finds the longest common prefix between a set of strings
        // Input:
        //    String[] texts = {"abc", "abcd", "abcde", "ab", "abcd", "abcdef"};
        // Output:
        //    ab

        String[] texts = {"abc", "abcd", "abcde", "ab", "abcd", "abcdef"};

        // Approach
        // 1. Find the shortest string
        // 2. Iterate through that string by char, checking to see if the char
        //    exists in the same position in ALL of the other strings. Stop when you
        //    hit a char where this is not true.
        // Other Approaches
        // - Binary search
        // - Tries

        StringBuilder commonPrefix = new StringBuilder();

        if (texts.length > 0) {
            Arrays.sort(texts);
            String shortest = texts[0];
            for (int i = 0; i < shortest.length(); i++) {
                boolean matchAll = true;
                for (int j = 0; j < texts.length; j++) {
                    if (texts[j].charAt(i) != shortest.charAt(i)) {
                        matchAll = false;
                        break;
                    }
                }
                if (!matchAll) {
                    break;
                }
                commonPrefix.append(shortest.charAt(i));
            }
        }

        System.out.println(commonPrefix);

    }

}
