package generics;

// Generic class
class GenericClass<T> {
    private T data;

    public GenericClass(T data) { this.data = data; }

    public T getData() { return this.data; }
}

public class SimpleGenerics {
    public static void main(String[] args) {
        // Initialize the generic class with Integer data
        GenericClass<Integer> igc = new GenericClass<>(5);
        System.out.println(igc.getData());

        // Initialize the generic class with String data
        GenericClass<String> sgc = new GenericClass<>("Hello Generics!");
        System.out.println(sgc.getData());

        // ? stands for unknown type or any type
        GenericClass<?> any = new GenericClass<>("Funny Generics!");
        System.out.println(any.getData());
        any = new GenericClass<>(480.383);
        System.out.println(any.getData());
    }
}