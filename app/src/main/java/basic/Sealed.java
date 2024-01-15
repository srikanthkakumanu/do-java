package basic;

/**
 * 1. The sealed classes and interfaces were added officially in Java 17.
 *      - It is a preview feature in Java 15, 16.
 *      - They enable more fine-grained inheritance control in Java and
 *          do these checks at compile-time.
 *      - Sealing allows classes and interfaces to define their permitted subtypes.
 * <p>
 * 2. Any normal/abstract class or interface can be declared as sealed.
 * <p>
 * 3. If a sealed class/interface is declared:
 *      - A sealed class must have a subclass.
 *      - A sealed interface must be implemented by a subclass.
 * <p>
 * 4. A Sealed class/interface can restrict (permission to extend/implement)
 *      the inheritance/implementation by using permits keyword at
 *      declaration level.
 * <p>
 * 5. The subclass/implementation class of a sealed class/interface must
 *      be either of final/sealed/non-sealed.
 *      - final means no further inheritance of that subclass/interface.
 *      - sealed means further it must have a subclass/implementation class
 *          and permits rule also can be applied.
 *      - Non-sealed means it is open for further inheritance or implementation.
 *          - Any normal class can extend non-sealed class.
 * <p>
 * 6. We can also perform switch expression pattern matching
 *      with sealed classes/interfaces.
 * 7. All permitted subclass/implementation classes must belong to the
 *      same module as the sealed class/interface.
 * 8. Records also can implement sealed interfaces and note that records
 *      are implicitly final by default.
 * 9. sealed, non-sealed, permits are context-sensitive keywords.
 */
public class Sealed {
    public static void main(String[] args) {
        // Pattern matching with sealed class/interfaces
        SealedInterface si = new D();
        String result = switch(si) {
            case A a -> "This is A (final class) and an implementation class of SealedInterface.";
            case Blue blue -> "This is a normal class, a subclass of B (A non-sealed implementation class of SealedInterface).";
            case B b -> "This is B (non-sealed class) and an implementation class of SealedInterface.";
            case D d -> "This is D (final class), a subclass of C (A sealed implementation class of SealedInterface).";
            case C c -> "This is C (sealed class) and an implementation class of SealedInterface.";
        };
        System.out.println(result);
    }
}

/**
 * An implementation class for a sealed interface must either be
 * a final/sealed/non-sealed.
 */
sealed interface SealedInterface permits A, B, C {
    int sides();
}
final class A implements SealedInterface {
    public int sides() { return 0; }
}

/**
 * A normal subclass can extend a non-sealed implementation class.
 * Ex: Blue class
 */
non-sealed class B implements SealedInterface {
    public int sides() { return 4; }
}
/**
 * Blue can extend a non-sealed implementation class.
 * It is like any other normal class.
 */
class Blue extends B {
    public int sides() { return -1; }
}

// sealed class C implements SealedInterface { // permits also can be applied
sealed class C implements SealedInterface permits D {
    public int sides() { return 9; }
}

/**
 * A subclass of a sealed class must be either sealed/final/non-sealed.
 */
 final class D extends C {
    public int sides() { return 10; }
}


