package records;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.Comparator;

/**
 * Dynamic tuples (Local Records) — This feature is missing in Java.
 * Other programming languages usually use those as dynamic data aggregators
 * without requiring an explicitly defined type.
 * Java Records are simple data aggregators and can be considered nominal tuples.
 * But we can use Java records as localized on-the-fly data aggregators.
 * Contextually localized Records simplify and formalize data processing and
 * bundle functionality.
 */
public class LocalRecords {
    public static void main(String[] args) {
        Map<Integer, List<String>> albums =
                Map.of(1990, List.of("Bossanova", " Listen Without Prejudice"),
                        1991, List.of("Nevermind", "Ten", "Blue lines"),
                        1992, List.of("The Chronic", "Rage Against the Machine"),
                        1993, List.of("Enter the Wu-Tang (36 Chambers)"),
                        1999, List.of("The Slim Shady LP", "Californication", "Play"));

        filterAlbums(albums, 1991).forEach(System.out::println);
    }

    public static List<String> filterAlbums (Map<Integer,
                                        List<String>> albums,
                                        int minimumYear) {

        /**
         * Local record as dynamic tuple
         */
        record AlbumsPerYear(int year, List<String> titles) {

            public AlbumsPerYear(Map.Entry<Integer, List<String>> entry) {
                this(entry.getKey(), entry.getValue());
            }

            public static Predicate<AlbumsPerYear> minimumYear(int year) {
                return albumsPerYear -> albumsPerYear.year() >= year;
            }

            public static Comparator<AlbumsPerYear> sortByYear() {
                return Comparator.comparing(AlbumsPerYear::year);
            }
        }

        return albums.entrySet()
                .stream()
                .map(AlbumsPerYear::new)
                .filter(AlbumsPerYear.minimumYear(minimumYear))
                .sorted(AlbumsPerYear.sortByYear())
                .map(AlbumsPerYear::titles)
                .flatMap(List::stream)
                .toList();
    }

}


