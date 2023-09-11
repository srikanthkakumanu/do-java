package generics;

import java.util.List;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 1. Generics provides compile-time type safety and they are added to ensure that they won't
 *      cause overhead at runtime. Because the compiler applies a process called Eraser on
 *      Generics at compile-time.
 * 2. Why Generics: Generics enable types (classes and interfaces) to be parameters when defining
 *      classes, interfaces and methods. These type parameters provide a way to re-use the same
 *      code with different inputs. The difference is that the input to formal parameters are values;
 *      whereas the input to type parameters are types. 
 * 3. It eliminates the need of casting.
 * 4. Generic methods:
 *      - Generic methods have a type parameter(<type>) before the return type of method declaration.
 *          Ex: public <T> List<T> arrayToList(.....)
 *      - <T> is needed even if method  return type is void when you want to declare a generic method.
 *      - Type parameters can be bounded.
 *      - Generic methods can have different type parameters separated by commas in the method signature.
 *      - Method body for a generic method is just like normal method.
 * 
 */
public class AllAboutGenerics {

    public static void main(String[] args) {
        Integer[] a = {1,2,3,4, 5};    
        List<String> sList = AllAboutGenerics.arrayToList(a, Object::toString);
        System.out.println(sList);
    }

    /**
     * Generic method example with single generic type declaration.
     * 
     * Generic methods have a type parameter(<type>) before the return type of method declaration.
     *  ex: public <T> List<T> arrayToList(...)
     * Type parameters can be bounded.
     * Generic methods can have different type parameters separatedby commas in the method signature.
     * 
     * @param <T> Generic Type declaration
     * @param a 
     * @return
     */
    public <T> List<T> arrayToList(T[] a) {
        return Arrays.asList(a);
    }

    /**
     * Generic methods can have different type parameters separated by commas in the method signature.
     */
    public static <T, G> List<G> arrayToList(T[] a, Function<T, G> mapper) {
        return Arrays.stream(a)
                    .map(mapper)
                    .collect(Collectors.toList());
    }

    
}