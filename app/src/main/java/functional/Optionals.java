package functional;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 Optional is highly recommended to be used as return types only.
 Optional has many variations such as OptionalLong, OptionalInt, OptionalDouble.
 */
public class Optionals {
    public static void main(String[] args) {
        // 1st example
        // Without Optional, but the risk of return value is null
        Dog dog = findByName("Fred");
        System.out.println(dog.age());

        // With Optional
        Optional<Dog> optionalDog = findByNameWithOptional("Fred");
        optionalDog.ifPresent(value -> System.out.println(value.age()));

        optionalDog.ifPresent(v -> System.out.println(v.age()));
        optionalDog.ifPresentOrElse(v -> System.out.println(v.age()), () -> System.out.println("No Dog!"));

        Dog resultDog = optionalDog.orElse(new Dog("Unknown", 0));
        System.out.println(resultDog.age());

        resultDog = optionalDog.orElseGet(() -> new Dog("Unknown", 1));
        System.out.println(resultDog.age());

        System.out.println(optionalDog.map(Dog::age).orElse(0));

        // 2nd example
        predictions().map(m -> "Looks " + m).ifPresent(System.out::println);

        // 3rd example
        Optional<String> noVal = Optional.empty();
        Optional<String> hasVal = Optional.of("ABCDEFG");

//        if(noVal.isPresent())
//            System.out.println("This value won't be displayed");
//        else
//            System.out.println("noVal has no value");
//
//        if(hasVal.isPresent())
//            System.out.println("The string in hasVal is: " + hasVal.get());

        System.out.println(noVal.orElse("Default String"));

        noVal.ifPresentOrElse(v -> System.out.println("This won't be displayed"),
                () -> System.out.println("noVal has no value"));
        hasVal.ifPresentOrElse(v -> System.out.println("The string in hasVal is: " + v),
                () -> System.out.println("noVal has no value"));

        // 3rd example
        Optional<Content> getIt = get("ABC");
        System.out.println(getIt.isPresent());

        String hasValue = "Optionals are awesome!";
        String hasNull = null;
        // Optional.ofNullable(T value) if the value might be null
        // Optional.of(T value) if the value must be non-null
        // Optional.empty() if there’s no value
        Optional<String> mayBeOptional = Optional.ofNullable(hasValue); //
        Optional<String> emptyOptional = Optional.ofNullable(hasNull);
        Optional<String> mustHaveValue = Optional.of(hasValue);
        emptyOptional = Optional.of(hasNull); // throws NPE
        Optional<String> noValue = Optional.empty();

        mayBeOptional.ifPresentOrElse(System.out::println,
                () -> System.out.println("No value found!"));

        // 4th example
        record User(boolean isActive) {}
        record Group(Optional<User> admin) {}
        record Permissions(List<String> permissions, Group group) {
            public boolean isEmpty() { return permissions.isEmpty(); }
        }

        User admin = new User(true);
        Group group = new Group(Optional.of(admin));
        Permissions permissions = new Permissions(List.of("A", "B", "C"), group);
        // Intermediate operations to find an active admin
        boolean isActiveAdmin = Optional.ofNullable(permissions)
                .filter(Predicate.not(Permissions::isEmpty))
                .map(Permissions::group)
                .flatMap(Group::admin)
                .map(User::isActive)
                .orElse(Boolean.FALSE);

        // optionals as stream elements
        List<Permissions> listOfPermissions = List.of(new Permissions(List.of("A", "B", "C"),
                group));
        List<User> activeUsers = listOfPermissions.stream()
                .filter(Predicate.not(Permissions::isEmpty))
                .map(Permissions::group)
                .map(Group::admin)
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .filter(User::isActive)
                .toList();

        List<User> activeUsersFlatMap =
                listOfPermissions.stream()
                        .filter(Predicate.not(Permissions::isEmpty))
                        .map(Permissions::group)
                        .map(Group::admin)
                        // A singular flatMap call replaces the
                        // previous filter and map operations.
                        .flatMap(Optional::stream)
                        .filter(User::isActive)
                        .toList();

        // Optional best practices
        // DON'T DO THIS - Don't use optional for simple null checks
        String fallbackValue = "fallback value";
        String maybeNull = null;
        String value = Optional.ofNullable(maybeNull).orElse(fallbackValue);
        // DON'T DO THIS
        if (Optional.ofNullable(maybeNull).isPresent()) { }
        // DO THIS INSTEAD
        value = maybeNull != null ? maybeNull : fallbackValue;
        // DO THIS INSTEAD
        if (maybeNull != null) { }

    }

    static Optional<Content> loadFromDB(String identifier) {
        return Optional.of(new Content(false));
    }

    // Usage of Optional in a functional call chain
    static Optional<Content> get(String contentId) {
        Map<String, Content> cache = new HashMap<>();

        return Optional.ofNullable(contentId)
                .filter(Predicate.not(String::isBlank))
                .map(String::toLowerCase)
                .map(cache::get)
                .or(() -> loadFromDB(contentId))
                .filter(Content::isPublished);
    }

    private static Optional<String> predictions() {     
        //return Optional.of("Great!"); 
        return Optional.empty();
    }
    /**
     Mimiking the valid output coming from DB
     @param name
     @return
     */
    private static Dog findByName(String name) {
        Dog dog = new Dog(name, 3);
        return dog;
    }

    /**
     Mimiking that no valid result found coming from DB
     @param name
     @return
     */
    private static Optional<Dog> findByNameWithOptional(String name) {
        Dog dog = null;
        //dog = new Dog(name, 5);
        return Optional.ofNullable(dog);
    }
}

record Dog(String name, int age) {}
record Content(boolean isPublished) {}