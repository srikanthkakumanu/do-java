package patterns.builder.ood;

/**
 * The builder pattern is another creational pattern for creating more complex data structures
 * by separating the construction from the representation itself.
 *
 * It solves various object creation problems, like multistep creation and validation,
 * and improves non-mandatory argument handling.
 *
 * Therefore, it’s a good companion for Records, which can only be created in a single swoop.
 */
public class Main {
    public static void main(String[] args) {
        var builder = User.builder()
                .email("hello@hello.com")
                .name("James Crowley");

        var user = builder.addPermission("create")
                .addPermission("edit").build();

        System.out.println(user);
    }
}
