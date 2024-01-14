package basic;

import java.util.Arrays;

/**
 * In Java 14, an improved version of instanceof is introduced i.e. Pattern matching
 * as an experimental feature and became permanent feature from Java 16.
 * <p>
 * Traditional instanceof approach:
 * 1. For each conditional block, we are testing the animal parameter
 *      to determine its type, convert it via a cast and declaring a
 *      local variable. Then we can perform operations specific to
 *      that particular animal.
 * 2. Drawbacks:
 *  - need to test the type and make a cast for every conditional block
 *  - repeat the type name three times for every if block.
 *  - Readability is poor
 *  - problem magnifies each time we add a new animal.
 * <p>
 * Pattern Matching for instanceof:
 * 1. It is a mechanism that determines if a value fits a general form. Pattern
 *      matching is used to test the type of value. A type must be a reference type.
 * <p>
 * 2. This kind of pattern is called a type pattern. If the pattern matches,
 *      a pattern variable will receive a reference to the object matched by
 *      the pattern.
 * <p>
 * 3. If instanceof succeeds, pattern variable will be created and contain a reference
 *      to the object that matches the pattern. If it fails, pattern variable
 *      is never created.
 */
public class PatternMatchInstanceOf {
    public static void main(String[] args) {
        Cat c = new Cat();
        Dog d = new Dog();

        // traditional instanceof approach
        // for each conditional block, we’re testing the animal parameter
        // to determine its type, converting it via a cast and declaring
        // a local variable. Then, we can perform operations specific to
        // that particular animal.
        if(c instanceof Cat) {
            Cat cat = (Cat) c;
            cat.meow();
        }
        if(d instanceof Dog) {
            Dog dog = (Dog) d;
            dog.woof();
        }

        // pattern matching for instanceof
        // Note: cat and dog are not an existing variable,
        // they are pattern variables.
        if(c instanceof Cat cat)
            cat.meow();
        if(d instanceof Dog dog)
            dog.woof();

        // another example
        Number n = Integer.valueOf(9);
        // traditional approach
        if(n instanceof Integer) {
            Integer num = (Integer) n;
            System.out.println(num);
        }
        // pattern matching approach
        if(n instanceof Integer num)
            System.out.println(num);

        // pattern variables can be used in logical AND expressions
        if((n instanceof Integer num) && (num >= 0))
            System.out.println("num is greater than 0");

        // It can also be used in loops
        Object[] obj = {"Alpha", "Beta", "Omega", Integer.valueOf(10)};
        for(int i = 0; ((obj[i] instanceof String s) && (i < obj.length)); i++) {
            System.out.println("Processing " + s);
        }
    }
}

abstract class Animal {}
final class Cat extends Animal {
    void meow() {}
}
final class Dog extends Animal {
    void woof() {}
}
