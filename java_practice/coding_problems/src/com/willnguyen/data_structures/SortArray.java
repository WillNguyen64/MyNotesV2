package com.willnguyen.data_structures;

import java.util.Arrays;

public class SortArray {

    public static void main(String[] args) {

        Integer[] arr = new Integer[] {4, 3, 2, 10, 12, 1, 5, 6};

        System.out.println("Unsorted: " + Arrays.asList((arr)));

        builtInSort(Arrays.copyOf(arr, arr.length));
        bubbleSort(Arrays.copyOf(arr, arr.length));
        selectionSort(Arrays.copyOf(arr, arr.length));
        insertionSort(Arrays.copyOf(arr, arr.length));
        mergeSort(Arrays.copyOf(arr, arr.length));
        quickSort(Arrays.copyOf(arr, arr.length));
    }

    private static void builtInSort(Integer[] arr) {
        Arrays.sort(arr);
        System.out.println("Built-in Sort: " + Arrays.asList(arr));
    }

    private static void bubbleSort(Integer[] arr) {
        // Repeatedly swap adjacent elements if they are out of order until the
        // array is sorted

        // Time Complexity: O(n^2)

        long t1 = System.nanoTime();
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i+1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = tmp;
                    swapped = true;
                }
            }
        } while (swapped);
        System.out.println("Bubble Sort: " + Arrays.asList(arr));

    }

    private static void selectionSort(Integer[] arr) {
        // Sort the array by splitting it into a sorted and unsorted part
        // Select the min value from the unsorted part and put it at the
        // beginning of the unsorted part

        // Time Complexity: O(n^2) - due to two nested loops
        // Space: O(1)
        long t1 = System.nanoTime();

        for (int i = 0; i < arr.length - 1; i++) {
            // Find the min in arr[i .. max]
            int jMin = i;
            int j = i + 1;
            while (j < arr.length) {
                if (arr[j] < arr[jMin]) {
                    jMin = j;
                }
                j++;
            }
            // Swap the value in current position with the min
            int tmp = arr[i];
            arr[i] = arr[jMin];
            arr[jMin] = tmp;
        }

        System.out.println("Selection Sort: " + Arrays.asList(arr));
    }

    private static void insertionSort(Integer[] arr) {
        // Sort the array by splitting it into a sorted and unsorted part
        // Values from unsorted part are picked and placed in the correct
        // position in the sorted part

        // Algorithm - sort array of size n in ascending order
        // 1. Iterate from arr[1] to arr[n] over array
        // 2. Compare current element (key) to predecessor
        // 3. If key < predecessor, compare to elements before. Move
        // greater elements one position up to make space for swapped element.

        // Time Complexity: O(n^2) - worst time if array is sorted in reversed order
        // Space: O(1)

        long t1 = System.nanoTime();

        for (int i = 0; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >=0 && arr[j] > key) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }
        System.out.println("Insertion Sort: " + Arrays.asList(arr));
    }

    private static void mergeSort(Integer[] arr) {
        // Use divide-and-conquer approach to sort an array
        // Divide the array into two parts, run mergeSort on each half, then merge the sorted halves.
        //
        // MergeSort(arr[], l,  r)
        //   1. Find the middle: m = l + [(r - 1) / 2]
        //   2. mergeSort(arr[], l, m)
        //   3. mergeSort(arr[], m+1 r)
        //   4. merge(arr[], l, m, r)

        // Time Complexity: O(n*logN) - splitting array takes logN time, and for each split, we look at average of N elements
        // Space Complexity: O(n) - must create a temp array to merge two arrays
        long t1 = System.nanoTime();
        mergeSort(arr, 0, arr.length);
        System.out.println("Merge Sort: " + Arrays.asList(arr) + ", time = " + (System.nanoTime() - t1));
    }

    private static void mergeSort(Integer[] arr, int l, int r) {

        int len = r - l;
        if (len == 1) {
            return;
        } else {
            // Find the middle
            int m = l + ((r - l) / 2);
            // mergeSort 1st half
            mergeSort(arr, l, m);
            // mergeSort 2nd half
            mergeSort(arr, m, r);
            // merge the two halves
            merge(arr, l, m, r);
        }
    }

    private static void merge(Integer[] arr, int l, int m, int r) {

        int[] mergedArr = new int[r - l];

        // Iterate thru both sorted arrays in parallel. On each iteration,
        // we look at the current element from both lists and select the
        // smaller one to put into the merged array. Stop iterating when
        // we run out of elements in one of the lists.
        int i = l, j = m, k = 0;
        while (i < m && j < r) {
            if (arr[i] <= arr[j]) mergedArr[k++] = arr[i++];
            if (arr[j] < arr[i]) mergedArr[k++] = arr[j++];
        }

        // If one of the lists has leftover elements, add them to the end of the merged array.
        while (i < m) {
            mergedArr[k++] = arr[i++];
        }

        while (j < r) {
            mergedArr[k++] = arr[j++];
        }

        // Copy elements from merged array back to the original array
        i = l; k = 0;
        while (k < mergedArr.length) {
            arr[i++] = mergedArr[k++];
        }
    }

    private static void quickSort(Integer[] arr) {
        // Complexity
        long t1 = System.nanoTime();

        quickSort(arr, 0, arr.length - 1);
        System.out.println("Quick Sort: " + Arrays.asList(arr));
    }

    private static void quickSort(Integer[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
    }

    private static int partition(Integer[] arr, int low, int high) {

        int pivot = arr[high];
        int pi = low - 1;

        for (int j = low; j <= high - 1; j++) {

            if (arr[j] < pivot) {
                pi++;
                swap(arr, pi, j);
            }
        }

        swap(arr, pi + 1, high);
        return pi + 1;
    }

    private static void swap(Integer[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

}
