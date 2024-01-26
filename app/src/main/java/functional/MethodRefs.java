package functional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Method references (:: operator) are special type of lambda expressions. They are often used to 
 * create a simple lambda expressions by referencing existing methods.
 * There are four types of method references.
 * 1. static methods
 * 2. Bounded non-static method references:
 *      If you want to refer to a non-static method of an already existing object,
 *      you need bounded non-static method reference. Bounded non-static method
 *      references are a great way to use already existing methods on variables,
 *      the current instance (this::) or superclass (super::). The lambda arguments are passed
 *      as the method arguments to the reference method of that specific object.
 *      You don't need an intermediate variable, you can combine the return value
 *      of another method call or field access directly with the :: operator.
 * 3. Unbounded non-static method references:
 *      Unbound non-static method reference not bound to a specific object, but they
 *      refer to an instance method of a type.
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
        Comparator<BiCycle> compareByFrames = (a, b) -> a.frameSize().compareTo(b.frameSize());

        List<BiCycle> bicycles = List.of(new BiCycle("Hero", 32), new BiCycle("Rally", 32), 
                                        new BiCycle("Honda", 24), new BiCycle("Honda", 14));
        bicycles.stream()
                .sorted(compareByFrames::compare) // 2. Reference to an instance method of an object
                .forEachOrdered(System.out::println); // 1. static method reference

        // 3. Instance methods of an arbitrary object of a particular type
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

        // another example
        List<Customer> customers = List.of(
                new Customer(1, "First", true),
                new Customer(2, "Second", false),
                new Customer(3, "Third", true),
                new Customer(4, "Fourth", false));

        // Using Lambdas
        customers.stream()
                .filter(customer -> customer.active())
                .map(customer -> customer.name())
                .map((name -> name.toUpperCase()))
                .peek(name -> System.out.println(name))
                .toArray(count -> new String[count]);

        // Using Method References
        customers.stream()
                .filter(Customer::active)
                .map(Customer::name)
                .map(String::toUpperCase)
                .peek(System.out::println)
                .toArray(String[]::new);

        // 1. Static Method Reference
        // Using Lambda
        Function<Integer, String> asLambda = i -> Integer.toHexString(i);
        // Using static method reference
        Function<Integer, String> asRef = Integer::toHexString;

        // 2. Bounded non-static method reference
        // Below is with an intermediate object "now"
        var now = LocalDate.now();
        // Using Lambda based on an existing object
        // now is the existing object in the below example
        //Predicate<LocalDate> isAfterNowAsLambda = date -> date.isAfter(now);
        Predicate<LocalDate> isAfterNowAsLambda = $ -> $.isAfter(now);
        // Using bounded non-static method reference
        Predicate<LocalDate> isAfterNowAsRef = now::isAfter;

        // without intermediate object "now"
        // Bind Return value
        isAfterNowAsRef = LocalDate.now()::isAfter;
        // Bind static field
        Function<Object, String> castToString = String.class::cast;
        // We can also reference methods from the current instance this::
        // or the super implementation with super::
        // check superAndThis() method for more details
        new SubClass().superAndThis("Hello, World!");

        // 3. Unbounded non-static method reference
        // Using Lambda
        Function<String, String> toLowerCaseLambda = str -> str.toLowerCase();
        // Using method reference
        Function<String, String> toLowerCaseRef = String::toLowerCase;


    }

    private static String toHexString(Integer i) { return Integer.toHexString(i); }
 }

record BiCycle(String brand, Integer frameSize) {
    public BiCycle(String brand) {
        this(brand, 0);
    }
}

record Customer(Integer id, String name, Boolean active) {}

class SuperClass {
     public String doWork(String input) { return "super: " + input; }
}
class SubClass extends SuperClass {
    @Override
    public String doWork(String input) { return "this: " + input; }

    public void superAndThis(String input) {
        Function<String, String> thisWorker = this::doWork;
        var thisResult = thisWorker.apply(input);
        System.out.println(thisResult);

        Function<String, String> superWorker = SubClass.super::doWork;
        var superResult = superWorker.apply(input);
        System.out.println(superResult);
    }
}
