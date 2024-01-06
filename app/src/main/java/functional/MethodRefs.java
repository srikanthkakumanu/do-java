package functional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Method references (:: operator) are special type of lambda expressions. They are often used to 
 * create a simple lamdba expressions by referencing existing methods.
 * There are four types of method references.
 * 1. static methods
 * 2. Instance methods of particular objects
 * 3. Instance methods of an orbitrary object of a particular type
 * 4. Constructor
 */

 public class MethodRefs {
    public static void main(String[] args) {
        List<String> messages = Arrays.asList("hello", "my", "friends");

        // 1. static method reference i.e. println
        // capitalize each word
        messages.stream()
                .map(m -> (m.substring(0, 1).toUpperCase())
                           .concat(m.substring(1)))
                .collect(Collectors.toList())
                .forEach(System.out::println); // 1. static method reference
        
        // 2. Instance methods of particular objects / Reference to an instance method of an object
        Comparator<BiCycle> compareByFrames = (a, b) -> a.getFrameSize().compareTo(b.getFrameSize());

        List<BiCycle> bicycles = List.of(new BiCycle("Hero", 32), new BiCycle("Rally", 32), 
                                        new BiCycle("Honda", 24), new BiCycle("Honda", 14));
        bicycles.stream()
                .sorted(compareByFrames::compare) // 2. Reference to an instance method of an object
                .forEachOrdered(System.out::println); // 1. static method reference

        // 3. Instance methods of an orbitrary object of a particular type
        List<Integer> nums = Arrays.asList(5, 3, 50, 24, 40, 2, 9, 18);
        // If we use the classic lambda expression as shown below,
        // we need to specify both a, b explicitly.
        List<Integer> sorted = nums.stream()
                                    .sorted((a, b) -> a.compareTo(b))
                                    .collect(Collectors.toList()); 
        // using method reference, we do not need to specify explicitly like below
        sorted = nums.stream()
                    .sorted(Integer::compareTo) // method reference
                    .collect(Collectors.toList());
        sorted.forEach(System.out::println);

        // 4. Constructor reference
        // We can reference the constructor just like to same way we reference static methods
        List<String> bikeBrands = Arrays.asList("Giant", "Scott", "Trek", "GT");
        // added single-arg based (brand) to BiCycle class below.
        // The below statement returns BiCycle array from original string list.
        BiCycle[] bicyclesWithNames = bikeBrands.stream()
                    .map(BiCycle::new) // constructor reference
                    .toArray(BiCycle[]::new); // array constructor reference
        Arrays.asList(bicyclesWithNames).forEach(System.out::println);
    }
 }

@Data
@AllArgsConstructor
@NoArgsConstructor
class BiCycle {
    private String brand;
    private Integer frameSize;

    public BiCycle(String brand) {
        this.brand = brand;
        this.frameSize = 0;
    }
}
