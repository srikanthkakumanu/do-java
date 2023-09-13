package generics;


public class BoundedTypes2<T extends Number> {
    private T t;

    public BoundedTypes2(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }
    // ** with bounded types, we can use the extended type methods in our code like below.
    public boolean isEven() { return t.intValue() % 2 == 0; }

    public <U extends Number> void inspect(U u) {
        System.out.println("T: " + t.getClass().getName());
        System.out.println("U: " + u.getClass().getName());
    }
    public static void main(String[] args) {
        BoundedTypes2<Integer> bt = new BoundedTypes2<>(123);
        bt.set(29);
        // compiler throws error for below line as only number are allowed.
        // bt.inspect("some text");
    }
}
