package com.willnguyen.dynamic_prog;

import java.util.*;

public class FiveTowers {

    /*
    Consider a 5x5 grid with five defensive towers spread across the grid. To provide an optimal
    defense for the grid, we have to build a tower on each row of the grid. Find all the solutions for
    building these towers so that none of them share the same column and diagonal.
     */

    /*
    Example:
      Here's one possible solution of tower placements:
          0   1   2   3   4
      0       x
      1               x
      2   x
      3           x
      4                   x
     */

    /*
    Solution:
        Find solutions for a 5x5 grid based on solutions for 4x5 grid
          - Find solutions for a 4x5 grid based on solutions for 3x5 grid
          - Find solutions for a 3x5 grid based on solutions for 2x5 grid
          - Find solutions for a 2x5 grid based on solutions for 1x5 grid
        etc..
     */

    public static void main(String[] args) {
        List<Solution> solutions = findSolutions(5, 5);
        solutions.forEach(
                s -> System.out.println(s)
        );
    }


    public static List<Solution> findSolutions(int numRows, int numCols) {

        final List<Solution> solutions = new ArrayList<>();

        if (numRows == 0) {
            solutions.add(new Solution(Collections.EMPTY_LIST));
        } else {
            final List<Solution> partialSolutions = findSolutions(numRows - 1, numCols);
            for (Solution partialSolution : partialSolutions) {
                // Add point from this row to partial solutions for n-1 rows to form new solution for n rows
                for (int col = 0; col < numCols; col++) {
                    Point newPoint = new Point(numRows - 1, col);
                    boolean foundPointForSolution = true;
                    for (Point otherPoint : partialSolution.points) {
                        // Make sure new point is not in same column as other points
                        if (sameColumn(newPoint, otherPoint) || sameDiagonal(newPoint, otherPoint)) {
                            foundPointForSolution = false;
                            break;
                        }
                    }
                    if (foundPointForSolution) {
                        solutions.add(new Solution(partialSolution.points, newPoint));
                    }
                }
            }
        }

        return solutions;
    }

    public static boolean sameColumn(Point p1, Point p2) {
        return p1.col == p2.col;
    }

    public static boolean sameDiagonal(Point p1, Point p2) {
        return Math.abs(p1.row - p2.row) == Math.abs(p1.col - p2.col);
    }

    public static class Solution {
        final List<Point> points = new ArrayList<>();

        public Solution(List<Point> points) {
            for (Point point : points) {
                this.points.add(point);
            }
        }

        public Solution(List<Point> points, Point point) {
            this(points);
            this.points.add(point);
        }

        public void addPoint(Point point) {
            this.points.add(point);
        }

        @Override
        public String toString() {
            return points.toString();
        }
    }

    public static class Point {
        int row;
        int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row &&
                    col == point.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public String toString() {
            return "{" + row + ", " + col + '}';
        }
    }

}
