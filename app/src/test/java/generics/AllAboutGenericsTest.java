package generics;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class AllAboutGenericsTest {
    @Test
    public void givenArrayOfIntegers_thanStringReturnedOK() {
        Integer[] a = {1,2,3,4,5};
        List<String> sList = Generics2.arrayToList(a, Object::toString);
        assertTrue(sList.contains("1"));
    }
}
