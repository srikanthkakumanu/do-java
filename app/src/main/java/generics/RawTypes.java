package generics;

import generics.model.Box;

public class RawTypes {
    public static void main(String[] args) {
        // raw types
        Box<String> sBox = new Box<>();

        // OK, assigning a parameterized type to raw type.
        Box rawBox = sBox; 
        
        // Not OK, will get a warning: unchecked exception, if we assign a raw type 
        // to parameterized type.
        rawBox = new Box();
        Box<Integer> inBox = rawBox; 
        
        // Not OK, will get a warning: unchecked exception, using raw type to invoke 
        // generic methods in generic type
        rawBox = sBox;
        rawBox.set(10);

    }
}
