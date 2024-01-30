package records;


import java.time.LocalDateTime;
import java.util.Objects;

/**
 * step-by-step creation
 * ---------------------
 * One of the advantages of immutable data structures is the lack of
 * “half-initialized” objects.
 *
 * Still, not every data structure is initializable all at once.
 *
 * Instead of using a mutable data structure in such a case, you can use
 * the builder pattern to get a mutable intermediate variable that’s used
 * to create an eventually immutable final result.
 */
public class UserRecord {
    public static void main(String[] args) {
        User u = new User("Bob", true);
        System.out.println(User.hello(User.MR) + u.name());

        Container<String> text = new Container<>("Hello, ", "this is a string container");
        String content = text.content();

        // Creating a record in step-by-step fashion
        // by using Builder design pattern
        var builder = new UserBuilder("Ben")
                .active(true)
                .lastLogin(LocalDateTime.now());
        var user = builder.build();
    }
}

record User(String name, boolean active, LocalDateTime lastLogin) {

    // We can define static constants
    public static final String MR = "Mr. ";
    public static final String MS = "Ms. ";

    /**
     * An Important Note:
     * A constructor cannot be generic.
     * It cannot include a "throws" clause either at constructor or getter method level.
     */
    /**
     * A Canonical constructor - that is automatically generated, but
     * we can override it to include additional code.
     */
//        public User(String name, boolean active, LocalDateTime lastLogin) {
//            Objects.requireNonNull(name);
//            Objects.requireNonNull(lastLogin);
//            this.name = name;
//            this.active = active;
//            this.lastLogin = lastLogin;
//        }
    /**
     * A compact canonical constructor - to avoid boiler plate code
     */
    public User {
        Objects.requireNonNull(name);
        Objects.requireNonNull(lastLogin);
        name = name.toLowerCase();
    }
    /**
     * A custom constructor but it must call default
     * canonical constructor as a first statement.
     */
    public User(String name, boolean active) {
        // It must call default canonical constructor as a first statement.
        this(name, active, LocalDateTime.now());
    }
    /**
     * We can define additional getter methods without compromising immutability
     */
    public String nameInUpperCase() { return name.toUpperCase(); }
    /**
     * We can define static getter methods without compromising immutability
     */
    public static String hello() {
        return "Hello, ";
    }

    public static String hello(String connotation) {
        return "Hello, " + connotation;
    }
}

/**
 * An Important Note:
 * A constructor cannot be generic.
 * It cannot include a "throws" clause either at constructor or getter method level.
 */
record Container<T> (T content, String identifier) {}

/**
 *
 * Builder design pattern aims to provide a flexible solution for constructing
 * complex data structures by separating the build process from the final
 * representation of the data structure.
 *
 * The main advantage of a Builder pattern is the ability to create complex
 * data structures step-by-step, allowing you to defer steps until the required
 * data is available.
 *
 * It also fits into the single responsibility principle of object-oriented design.
 */
final class UserBuilder {
    private final String name;
    private boolean active;
    private LocalDateTime lastLogin;

    public UserBuilder (String name) { this.name = name; this.active = true; }

    public UserBuilder active (boolean isActive) {
        if (!this.active)
            throw new IllegalArgumentException("User is not Active!");
        this.active = isActive;
        return this;
    }

    public UserBuilder lastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public User build() {
        return new User(this.name, this.active, this.lastLogin);
    }
}

