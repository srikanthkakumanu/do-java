package generics;

class GenClass<T extends Number> {
    public void display() {
        System.out.println("This is a bounded type generics class");
    }
}

public class BoundedTypes1 {
    public static void main(String[] args) {
        GenClass<Integer> gc = new GenClass<>();
        gc.display();
        // For below statment, compiler throws error if any types used other than Number
        // GenClass<String> gc = new GenClass<>();
    }
}
