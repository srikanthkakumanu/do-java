package functional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Immutability {
    public static void main(String[] args) {

        // 1. Strings are immutable in Java
        // string interning means two objects refer to the same string from string constant pool
        String s1 = "TikTok";
        String s2 = "TikTok";
        System.out.println(s1 == s2);

        // Using new, always create a new object in heap even though the value is same.
        // Thus, no reference to same string from string pool
        String s3 = new String("TikTok");
        System.out.println(s1 == s3);
        // manual string interning
        String s4 = s3.intern();
        System.out.println(s1 == s4);

        // 2. Immutable collections
        // 2.1 Unmodifiable collections
        List<String> modifiable = new ArrayList<>();
        modifiable.add("blue"); modifiable.add("red");
        // It only creates an unmodifiable view but underlying collection still can be modified
        List<String> unmodifiable = Collections.unmodifiableList(modifiable);
        try { unmodifiable.clear(); } // It throws UnsupportedOperationException
        catch(UnsupportedOperationException uoe) {
            System.out.println(uoe.toString());
        }

        // It only creates an unmodifiable view but underlying collection still can be modified
        List<String> original = new ArrayList<>();
        modifiable.add("blue"); modifiable.add("red");
        unmodifiable = Collections.unmodifiableList(original);
        original.add("green");
        System.out.println(unmodifiable.size());

        // 2.2 Immutable Collection factory methods aka of(...) methods (Java 9+)
        List<String> immutable = List.of("blue", "red");

        try { immutable.add("green"); } // cannot modify immutable collection
        catch (UnsupportedOperationException uoe) {
            System.out.println(uoe.toString());
        }
        System.out.println(immutable.size());

        // 2.3 Immutable copies (Java 10+) by using copyOf(...) methods
        // They provide a deeper level of immutability
        List<String> copied = List.copyOf(original);
        original.add("yellow");
        System.out.println(original);
        System.out.println(copied);

        // 3. Immutable Math
        var answer = new BigDecimal(42);
        var result = answer.add(BigDecimal.ONE);
        System.out.println(result);
        System.out.println(answer);

        
    }
}
