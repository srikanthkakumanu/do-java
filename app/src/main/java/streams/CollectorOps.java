package streams;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;


public class CollectorOps {

    record User (UUID id,
                 String group,
                 LocalDateTime lastLogin,
                 List<String> logEntries) {}

    record UserStats (long total, long neverLoggedIn) {}

    static List<User> users = List.of(
            new User(UUID.randomUUID(), "admin", LocalDateTime.now().minusDays(23L), List.of("1", "2")),
            new User(UUID.randomUUID(), "user", LocalDate.now().atStartOfDay(), List.of("Z", "Y")),
            new User(UUID.randomUUID(), "user", LocalDateTime.now().minusDays(42L), List.of("A", "B"))
    );
    public static void main(String[] args) {
        System.out.println("\n-----Stream operations using Collector----\n");
        System.out.println("\n-----Collectors.groupingBy----\n\n");
        Map<String, List<User>> lookup = users.stream()
                                                .collect(Collectors
                                                        .groupingBy(User::group));
        lookup.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

//        Collector<UUID, ?, Set<UUID>> collectToSet = Collectors.toSet(); // Collect elements to set
//        Collector<User, ?, Set<UUID>> mapToId = Collectors.mapping(User::id, collectToSet); // map from user to UUID
//        Collector<User, ?, Map<String, Set<UUID>>> groupingBy = Collectors.groupingBy(User::group, mapToId); // grouping by group
        // The Above three statements can be written like below
        Map<String, Set<UUID>> onlyIdLookup =
                users.stream().collect(
                        groupingBy(User::group, mapping(User::id, toSet())));
        onlyIdLookup.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        // Method Reference
        var collectIdsToSet = mapping(User::id, Collectors.toSet());
        // Lambda alternative
        var collectIdsToSetLambda = mapping((User user) -> user.id, Collectors.toSet());
        onlyIdLookup = users.stream().collect(groupingBy(User::group, collectIdsToSet));
        onlyIdLookup.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        // reducing
        System.out.println("\n-----Collectors.reducing----\n\n");
        // detailed
        var summingUp = reducing(0, Integer::sum);
        var downstream = mapping((User user) -> user.logEntries().size(), summingUp);
        Map<UUID, Integer> logCountPerUserId = users.stream()
                .collect(groupingBy(User::id, downstream));
        logCountPerUserId.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        // short
        downstream = reducing(0, (User user) -> user.logEntries.size(), Integer::sum);
        logCountPerUserId = users.stream()
                .collect(groupingBy(User::id, downstream));
        logCountPerUserId.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        // short 2
        System.out.println("\n-----Collectors.summingInt----\n\n");
        downstream = summingInt((User user) -> user.logEntries().size());
        logCountPerUserId = users.stream().collect(groupingBy(User::id, downstream));
        logCountPerUserId.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        System.out.println("\n-----Collectors.flatMapping----\n\n");
        var logEntryDownstream = flatMapping((User user) ->
                                                            user.logEntries().stream(),
                                                            toList());
        Map<String, List<String>> result = users.stream().collect(
                                                            groupingBy(User::group,
                                                                    logEntryDownstream));
        result.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        System.out.println("\n-----Collectors.filtering----\n\n");
        var startOfDay = LocalDate.now().atStartOfDay();
        Predicate<User> loggedInToday =
                Predicate.not(user -> user.lastLogin().isBefore(startOfDay));

        // With Intermediate filter
        Map<String, Set<UUID>> todaysLoginsByGroupWithFilterOp =
                users.stream()
                        .filter(loggedInToday)
                        .collect(groupingBy(User::group, mapping(User::id, toSet())));

        // With Collect filter
        Map<String, Set<UUID>> todaysLoginsByGroupWithCollector =
                users.stream()
                        .collect(groupingBy(User::group,
                                filtering(loggedInToday, mapping(User::id, toSet()))));

        // Teeing - Composite collectors
        /**
         * Imagine you want to know how many users you have and how many of
         * them never logged in. Without the teeing operation, you would
         * have to traverse the elements twice: once for the overall count
         * and another time for counting the never-logged-in users.
         */
        UserStats stats = users.stream() // A local Record type UserStats is used as the result type because Java lacks dynamic tuples.
                .collect(teeing(counting(), // The first downstream Collector counts all elements.
                                    filtering(user -> user.lastLogin() == null,
                                                counting()), // The second downstream Collector filters first and uses an additional downstream Collector to count the remaining elements.
                        UserStats::new)); // A method reference to the UserStats constructor serves as the merge function of the two downstream Collector results.
        System.out.println(stats.total() + " " + stats.neverLoggedIn());
    }

}
