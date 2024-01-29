package functional;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Optional;

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
