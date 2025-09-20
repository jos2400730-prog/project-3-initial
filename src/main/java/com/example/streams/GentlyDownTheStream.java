package com.example.streams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * These coding kata on the Stream API can all be completed with a single return line.
 */
public class GentlyDownTheStream {

    protected List<String> fruits;
    protected List<String> veggies;
    protected List<Integer> integerValues;

    public GentlyDownTheStream() {
        fruits = Arrays.asList("Apple", "Orange", "Banana", "Pear", "Peach", "Tomato");
        veggies = Arrays.asList("Corn", "Potato", "Carrot", "Pea", "Tomato");
        integerValues = new Random().ints(0, 1001)
                                    .boxed()
                                    .limit(1000)
                                    .collect(Collectors.toList());
    }


    public List<String> sortedFruits() {
        return fruits.stream()
                     .sorted()
                     .collect(Collectors.toList());
    }


    public List<String> sortedFruitsException() {
        return fruits.stream()
                .filter(x -> !x.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    // TODO - return a list with the first 2 elements of a sorted list of fruits
    public List<String> sortedFruitsFirstTwo() {
        return fruits.stream()
                .filter(x -> !x.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    // TODO - return a comma separated String of sorted fruits
    public String commaSeparatedListOfFruits() {
        return null;
    }

    // TODO - return a list of veggies that are sorted in reverse (descending) order
    public List<String> reverseSortedVeggies() {
        return null;
    }

    // TODO - return a list of veggies that are sorted in reverse order, and all in upper case
    public List<String> reverseSortedVeggiesInUpperCase() {
        return null;
    }

    // TODO - return a list of the top 10 values in the list of random integers
    public List<Integer> topTen() {
        return null;
    }

    // TODO - return a list of the top 10 unique values in the list of random integers
    public List<Integer> topTenUnique() {
        return null;
    }

    // TODO - return a list of the top 10 unique values in the list of random integers that are odd
    public List<Integer> topTenUniqueOdd() {
        return null;
    }

    // TODO - return a single value that represents the average of all of your random numbers
    public Double average() {
        return null;
    }
}
