package collections;

import java.util.Iterator;
import java.util.List;

/**
 * There are 3 ways to iterate over a collection.
 * 1. via enhanced for-each construct
 * 2. via Iterator
 * 3. via Iterable
 */
public class Iterables {
    public static void main(String[] args) {
        // 1st
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // below exhibit slow performance as it creates a new iterator for each iteration.
        for (var num : nums)
            System.out.println(num);

        // 2nd
        Iterator<?> i = nums.iterator();
        while (i.hasNext())
            System.out.println(i.next());

        // 3rd
        nums.forEach(System.out::println);

        // 4th

        // Iterate multiple collections in parallel
        iterateMultipleCollections();
    }

    /**
     * Note: use standard Iterator when we need to:
     * 1. Iterate multiple collections in parallel.
     * 2. Remove the current element - because standard for or for-each is
     *      not usable for filtering.
     * Below example iterates multiple collections in parallel.
     */
    private static void iterateMultipleCollections() {

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
