package records;

import java.util.Objects;

/**
 Record:
 Record purpose is to carry just the data and no boilerplate code is required.
 It is commonly referred to as an aggregate type.
 Records are immutable data classes that require only the type and field names.
 No setXXX() are generated because all records are immutable. However, if a record holds a reference
 to some object, you can make a change to that object, but you cannot change to what object the
 reference in the record refers. Thus, in Java terms, records are said to be shallowly immutable.
 <p>
 Once Record is declared - equals(), toString(), hashCode() methods, private and 
 final fields, public constructor (with fields defined at record declaration level)
 are generated by compiler.
 <p>
 1. By default, All record declarations implicitly inherits from Record. 
      So, no more extends are allowed as Java does not support multiple inheritance.
 <p>
 2. By default, all records create a canonical constructor with the arguments 
      that are defined at declaration level. But we can override that canonical constructor.
 <p>
 3. We can override the default constructor with Compact constructor as shown below.
      This is special to records only and useful to override constructors in more compact way.
 <p>
 4. We can define multiple constructors with different combinations of arguments.
 <p>
 5. It can implement interfaces.
 <p>
 6. By default, All records are final hence it cannot be extended further.
 <p>
 7. Once declared, all the record fields are immutable hence cannot be changed. 
      Hence, they are thread-safe and thereby no synchronization is required.
 <p>
 8. It allows only static constants to be declared. no instance level fields (non-static) are allowed. 
      Any fields are needed, they should be declared as arguments at record declaration level.
 <p>
 9. A record can be generic i.e. it supports generic just like any other types.
      However, A constructor cannot be generic, and it cannot include a throws
      clause either at constructor or getter method level.
 */
final record Person(String id, String name, Integer age) {

    public static final String DEFAULT_ID = "000-000";
    // But we cannot define any instance fields here. They should be declared at definition level only.

    // 1. all records create a default canonical constructor with parameters that are defined at declaration level.
    // We can override it if needed.
    // public Person(String id, String name, Integer age) {
    //     System.out.println("Person record constructor override.");
    //
    //     Objects.requireNotNull(id);
    //     Objects.requireNotNull(name);
    //
    //     if(age < 0)
    //         throw new IllegalArgumentException("Person Age cannot be negative!");
        
    //     this.id = id; this.name = name; this.age = age;
    // }

    // 2. Compact canonical constructor: - as defined below. This is only special feature for records.
    // This is useful instead of overriding the constructor as shown above.
    // public Person {
    //     System.out.println("Person record constructor overrided with \"Compact Constructor\".");
    //     
    //     Objects.requireNotNull(id);
    //     Objects.requireNotNull(name);
    //
    //     if(age < 0)
    //         throw new IllegalArgumentException("Person Age cannot be negative!");
    //     // With Compact constructor, the below statement is no longer is required.
    //     // this.id = id; this.name = name; this.age = age;
    // }

    // 3. We can define different types of custom constructors for record.
    public Person(String name, Integer age) {
        this(DEFAULT_ID, name, age);
        System.out.println("Person record constructor with variation of arguments");
        Objects.requireNonNull(name);
        
        if(age < 0)
            throw new IllegalArgumentException("Person Age cannot be negative!");
    }

    public String nameInUpperCase() { return name.toUpperCase(); }

    // We can define static fields and methods
    public static String greetings() { return "Hello, "; }
}

public class Records {
    public static void main(String[] args) {
        Person p = new Person("Thomas Neumann", 23);
        System.out.println(p.toString());
        System.out.println(p.nameInUpperCase());
        System.out.println(Person.greetings());

        System.out.println("Records support Generics");
        Something<Integer> i = new Something<>(1, 32);
        Something<Double>  d = new Something<>(2, 32.43d);
        Something<String> s = new Something<>(3, "Record with Generics");
        System.out.println(i.value());
        System.out.println(d.value());
        System.out.println(s.value());
    }
}

record Something<T>(Integer id, T value){}