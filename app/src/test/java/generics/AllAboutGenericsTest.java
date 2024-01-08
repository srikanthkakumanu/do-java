package generics;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;


public class AllAboutGenericsTest {
    @Test
    public void givenArrayOfIntegers_thanStringReturnedOK() {
        Integer[] a = {1,2,3,4,5};
        List<String> sList = Generics2.arrayToList(a, Object::toString);
        assertTrue(sList.contains("1"));
    }
}
