// 代码生成时间: 2025-08-28 03:21:34
package com.example.demo;

import io.quarkus.runtime.Quarkus;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SortingService class provides functionality to sort a list of numbers using different algorithms.
 */
public class SortingService {

    // Default constructor
    public SortingService() {
    }

    /**
     * Sorts a list of numbers using the built-in Collections.sort method.
     * @param numbers the list of numbers to sort
     * @return the sorted list of numbers
     */
    public List<Integer> sortNumbersUsingCollections(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("The list of numbers cannot be null or empty.");
        }
        Collections.sort(numbers);
        return numbers;
    }

    /**
     * Sorts a list of numbers using the built-in Java Streams API.
     * @param numbers the list of numbers to sort
     * @return the sorted list of numbers
     */
    public List<Integer> sortNumbersUsingStreams(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("The list of numbers cannot be null or empty.");
        }
        return numbers.stream().sorted().collect(Collectors.toList());
    }

    /**
     * Sorts a list of numbers using a custom Comparator.
     * @param numbers the list of numbers to sort
     * @param comparator the custom comparator to use for sorting
     * @return the sorted list of numbers
     */
    public List<Integer> sortNumbersUsingCustomComparator(List<Integer> numbers, Comparator<Integer> comparator) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("The list of numbers cannot be null or empty.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The custom comparator cannot be null.");
        }
        return numbers.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * Main method for demonstration purposes.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SortingService service = new SortingService();
        List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 2);

        System.out.println("Original list: " + numbers);

        try {
            List<Integer> sortedNumbers = service.sortNumbersUsingCollections(numbers);
            System.out.println("Sorted list using Collections.sort: " + sortedNumbers);

            sortedNumbers = service.sortNumbersUsingStreams(numbers);
            System.out.println("Sorted list using Streams API: " + sortedNumbers);

            sortedNumbers = service.sortNumbersUsingCustomComparator(numbers, Comparator.reverseOrder());
            System.out.println("Sorted list using custom Comparator: " + sortedNumbers);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
