package com.example.streams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Enhanced coding kata on the Stream API with exception handling, generics, and advanced concepts.
 * All methods include proper validation and can be completed with a single return statement plus validation.
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

    /**
     * Example method showing proper exception handling and validation
     * Returns a sorted list of fruits with comprehensive error checking
     */
    public List<String> sortedFruits() throws InvalidDataException {
        try {
            validateCollection(fruits, "Fruits collection");

            return fruits.stream()
                    .filter(Objects::nonNull) // Handle potential null elements
                    .sorted()
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException | EmptyCollectionException e) {
            // ✅ Rethrow validation-related exceptions directly (tests expect these exact types)
            throw e;
        } catch (Exception e) {
            // ✅ Only wrap *unexpected* runtime issues
            throw new InvalidDataException("Failed to sort fruits: " + e.getMessage());
        }
    }

    /**
     * Enhanced version with custom predicate and exception handling
     */
    public List<String> sortedFruitsException() throws InvalidDataException {
        return sortedFruitsWithFilter(fruit -> !fruit.startsWith("A"));
    }

    // - return a list with the first 2 elements of a sorted list of fruits
    // Add proper validation and exception handling
    public List<String> sortedFruitsFirstTwo() throws InvalidDataException {
        try {
            validateCollection(fruits, "Fruits collection");

            return fruits.stream()
                    .filter(Objects::nonNull)
                    .sorted()
                    .limit(2)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException | EmptyCollectionException e) {
            // Re-throw validation exceptions directly
            throw e;
        } catch (Exception e) {
            // Wrap unexpected issues
            throw new InvalidDataException("Failed to process sorted fruits (first two): " + e.getMessage());
        }
    }


    //  - return a comma separated String of sorted fruits
    // Handle null values and empty results gracefully
    public String commaSeparatedListOfFruits() throws InvalidDataException {
        try {
            validateCollection(fruits, "Fruits collection");

            return fruits.stream()
                    .filter(Objects::nonNull)
                    .sorted()
                    .collect(Collectors.joining(", "));
        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException("Failed to create comma-separated fruit list: " + e.getMessage());
        }
    }

    //  - return a list of veggies sorted in reverse (descending) order
    // Use Comparator.reverseOrder() and handle edge cases
    public List<String> reverseSortedVeggies() throws InvalidDataException {
        try {
            validateCollection(veggies, "Veggies collection");

            return veggies.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException("Failed to reverse sort veggies: " + e.getMessage());
        }
    }

    //  - return a list of veggies sorted in reverse order, all in upper case
    // Chain multiple stream operations with proper exception handling
    public List<String> reverseSortedVeggiesInUpperCase() throws InvalidDataException {
        try {
            validateCollection(veggies, "Veggies collection");

            return veggies.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .map(v -> v.toUpperCase(Locale.ROOT))
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException("Failed to reverse sort veggies in uppercase: " + e.getMessage());
        }
    }

    //  - return a list of the top 10 values in the list of random integers
    // Handle cases where list has fewer than 10 elements
    public List<Integer> topTen() throws InvalidDataException {
        try {
            validateCollection(integerValues, "Integer values collection");

            return integerValues.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .limit(10)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException("Failed to retrieve top 10 integer values: " + e.getMessage());
        }
    }

    //  - return a list of the top 10 unique values in the list of random integers
    // Use distinct() operation and handle empty results
    public List<Integer> topTenUnique() throws InvalidDataException {
        try {
            validateCollection(integerValues, "Integer values collection");

            return integerValues.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .distinct()
                    .limit(10)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException("Failed to retrieve top 10 unique integer values: " + e.getMessage());
        }
    }

    //  - return a list of the top 10 unique values that are odd
    // Combine filtering, distinct, and limiting operations
    public List<Integer> topTenUniqueOdd() throws InvalidDataException {
        try {
            validateCollection(integerValues, "Integer values collection");

            return integerValues.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .distinct()
                    .filter(x -> x % 2 != 0)
                    .limit(10)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException("Failed to retrieve top 10 unique odd integers: " + e.getMessage());
        }
    }

    //  - return the average of all random numbers
    // Handle potential OptionalDouble and division by zero scenarios
    public Double average() throws InvalidDataException {
        try {
            validateCollection(integerValues, "Integer values collection");

            return safeAverage(integerValues)
                    .orElseThrow(() -> new InvalidDataException("No values available for average"));
        } catch (IllegalArgumentException e) {
            throw e; // keep null collection test passing
        } catch (EmptyCollectionException e) {
            // ✅ this test expects InvalidDataException
            throw new InvalidDataException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidDataException("Failed to calculate average: " + e.getMessage());
        }
    }

    // Generic method for safe collection operations
    private <T> void validateCollection(Collection<T> collection, String collectionName) throws EmptyCollectionException {
        if (collection == null) {
            throw new IllegalArgumentException(collectionName + " cannot be null");
        }
        if (collection.isEmpty()) {
            throw new EmptyCollectionException(collectionName + " cannot be empty");
        }
    }

    // Helper method demonstrating advanced generics and functional programming
    private <T> List<T> sortedWithFilter(Collection<T> collection,
                                         Predicate<T> filter,
                                         Comparator<T> comparator) throws InvalidDataException {
        try {
            validateCollection(collection, "Input collection");

            return collection.stream()
                    .filter(Objects::nonNull)
                    .filter(filter)
                    .sorted(comparator)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InvalidDataException("Failed to sort and filter collection: " + e.getMessage());
        }
    }

    // Specialized method using the generic helper
    private List<String> sortedFruitsWithFilter(Predicate<String> filter) throws InvalidDataException {
        return sortedWithFilter(fruits, filter, String::compareTo);
    }

    // Utility method for safe integer operations
    private OptionalDouble safeAverage(Collection<Integer> numbers) {
        return numbers.stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average();
    }
}