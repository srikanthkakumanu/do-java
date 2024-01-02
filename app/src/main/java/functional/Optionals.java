package functional;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Optional;

/**
 * Optional is highly recommended to be used as return types only.
 */
public class Optionals {
    public static void main(String[] args) {
        // 1st example
        // Without Optional, but risk of return value is null
        Dog dog = findByName("Fred");
        System.out.println(dog.getAge());

        // With Optional
        Optional<Dog> optionalDog = findByNameWithOptional("Fred");
        if(optionalDog.isPresent())
            System.out.println(optionalDog.get().getAge());

        optionalDog.ifPresent(v -> System.out.println(v.getAge()));
        optionalDog.ifPresentOrElse(v -> System.out.println(v.getAge()), () -> System.out.println("No Dog!"));

        Dog resultDog = optionalDog.orElse(new Dog("Unknown", 0));
        System.out.println(resultDog.getAge());

        resultDog = optionalDog.orElseGet(() -> new Dog("Unknown", 1));
        System.out.println(resultDog.getAge());

        System.out.println(optionalDog.map(Dog::getAge).orElse(0));

        // 2nd example
        predictions().map(m -> "Looks " + m).ifPresent(System.out::println);
    }

    private static Optional<String> predictions() {     
        //return Optional.of("Great!"); 
        return Optional.empty();
    }
    /**
     * Mimiking the valid output coming from DB
     * @param name
     * @return
     */
    private static Dog findByName(String name) {
        Dog dog = new Dog(name, 3);
        return dog;
    }

    /**
     * Mimiking that no valid result found coming from DB
     * @param name
     * @return
     */
    private static Optional<Dog> findByNameWithOptional(String name) {
        Dog dog = null;
        //dog = new Dog(name, 5);
        return Optional.ofNullable(dog);
    }
}

@Data
@AllArgsConstructor
class Dog {
    private String name;
    private int age;
}
