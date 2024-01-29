package generics;

/**
 A type variable with multiple bounds is a subtype of all the types listed in the bound. 
 If one of the bounds is a class, it must be specified first. 
 Otherwise, we get a compile-time error. As per below, A must be specified first.
 */
 
class A {}
interface B {}
interface C {}

// The below usage will throw a compile-time error
// class D <T extends B & A & C> { /... */ }

public class BoundedTypes3<T extends A & B & C> {
    private T t;
    
    public void test() {
        BoundedTypes3<T> bt = new BoundedTypes3<>();
        System.out.println("Test Done: " + bt.getClass().getName());
    }
    public static void main(String[] args) {
        BoundedTypes3 a = new BoundedTypes3();
        a.test();

    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
