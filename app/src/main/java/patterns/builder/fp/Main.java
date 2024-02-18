package patterns.builder.fp;

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

        // LAZY BUILDER
        var builder = User.builder()
                .name(() -> "James Bond")
                .email("james@bond.com");

        var user = builder.addPermission("create")
                .addPermission("edit")
                .build();

        System.out.println(user);

        // WITH BUILDER - WITHER METHODS
        var user2 = UserWith.builder()
                .with(with -> {
                    with.email = "james@bond.com";
                    with.name = "James Bond";
                })
                .withPermissions(permissions -> {
                    permissions.add("create");
                    permissions.add("view");
                })
                .build();

        System.out.println(user2);
    }
}
