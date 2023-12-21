package collections;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * There are 3 ways to iterate over a collection.
 * 1. via enhanced for-each construct
 * 2. via Iterator
 * 3. via Iterable
 */
public class Iterables {
    public static void main(String[] args) {
        
        // Different ways to iterate a collection
        // also by using standard iterator
        iterate();

        // Iterate using a ListIterator
        listIterate();

        // Iterate multiple collections in parallel
        iterateMultipleCollections();
    }

    /**
     * Demonstrates different ways to iterate the elements by using standard iterator
     * from a collection.
     */
    private static void iterate() {
        System.out.println("Iterating the elements in different ways..");
        
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // 1st:- below for-each exhibits poor performance as it creates a new iterator 
        //       for each iteration.
        for (var num : nums)
            System.out.println(num);

        // 2nd:- Using standard Iterator
        Iterator<?> i = nums.iterator();
        while (i.hasNext())
            System.out.println(i.next());

        // 3rd:- Using forEachRemaining from Iterable<E>
        i.forEachRemaining(System.out::println);

        // 4th:- Using standard forEach of a collection
        nums.forEach(System.out::println);


    }

    /** 
     * Demonstrate iteration using ListIterator
     * 
     */
    private static void listIterate() {
        System.out.println("Iterating a list using ListIterator...");
        List<Integer> nums = new ArrayList<>(List.of(2, 5, 6, 1, 39, 321));
        
    }

    /**
     * Note: use standard Iterator when we need to:
     * 1. Iterate multiple collections in parallel.
     * 2. Remove the current element - because standard for or for-each is
     *      not usable for filtering.
     * Below example iterates multiple collections in parallel.
     */
    private static void iterateMultipleCollections() {
        System.out.println("Iterating over multiple collections in parallel");
        // Iterate two arraylist simultaneously
        var countries = List.of("India", "United States", "Ireland", "Switzerland", "Malaysia");
        var codes = List.of("+91", "+1", "+353", "+41", "+60");

        Iterator<String> countryIterator = countries.iterator();
        Iterator<String> codeIterator = codes.iterator();

        while(countryIterator.hasNext() && codeIterator.hasNext()) {
            String merged = String.format("%s=%s", countryIterator.next(), codeIterator.next());
            System.out.println(merged);
        }
    }
}
