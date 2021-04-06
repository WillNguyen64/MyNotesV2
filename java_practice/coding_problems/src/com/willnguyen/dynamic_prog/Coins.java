package com.willnguyen.dynamic_prog;

import java.util.ArrayList;
import java.util.List;

public class Coins {
    /*
    Consider an amount of n cents. Count the ways you can change this amount using any number of quarters (25 cents),
    dimes (10 cents), nickels (5 cents), and pennies (1 cent).
     */

    /*
      Example:
         46 cents
         Combo  | 25 cents  |  10 cents  |  5 cents  |  1 cent
           1         1           2             0          1
           2         1           1             2          1
           3         1           1             1          6
           4         1           1             0         11
           ...
     */

    /*
      Solution:
        Basic idea:
          - Start with 4 denominations (i.e,. n), picking the highest
          - Figure out the possible quantities you can change with the highest denomination
          - For each quantity, we calculate how many ways you can change for the leftover amount we can't change using
            the 3 other denominations (i.e., n-1)
          - Then we sum up all of the ways we can change for each quantity.
     */

    public static void main(String[] args) {
        int[] denominations = {25, 10, 5, 1};
        // 5 cents
        //  1)  1x5, 0x1
        //  2)  0x5, 5x1
        List<String> waysToMakeChange = new ArrayList<>();
        makeChange(denominations, 0, 5, waysToMakeChange);
        System.out.println(waysToMakeChange);

        // 11 cents
        //  1) 1x10, 0x5, 1x1
        //  2) 0x10, 2x5, 1x1
        //  3) 0x10, 1x5, 6x1
        //  4) 0x10, 0x5, 11x1
        waysToMakeChange = new ArrayList<>();
        makeChange(denominations, 0, 11, waysToMakeChange);
        System.out.println(waysToMakeChange);
    }


    private static int makeChange(int[] denominations, int currIdx, int leftover, List<String> result) {

        if (currIdx == denominations.length - 1) {
            // Only one way to make change with 1 cent
            result.add(leftover + "x1");
            return 1;
        }

        int currDenomination = denominations[currIdx];
        int numWaysToChange = 0;

        for (int i = 0; i <= leftover / currDenomination; i++) {
            int newLeftover = leftover - (i * currDenomination);
            List<String> subResults = new ArrayList<>();
            numWaysToChange += makeChange(denominations, currIdx + 1, newLeftover, subResults);
            for (int j = 0; j < subResults.size(); j++) {
                result.add(i + "x" + currDenomination + "|" + subResults.get(j));
            }
        }

        return numWaysToChange;
    }
}
