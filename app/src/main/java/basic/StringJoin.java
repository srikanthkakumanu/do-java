package basic;

import java.util.StringJoiner;

/**
 * StringBuilder is NOT synchronized and StringBuffer is synchronized.
 * But StringBuilder offers better performance compared to StringBuffer.
 */
public class StringJoin {
    public static void main(String[] args) {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add("One").add("Two").add("Three");
        System.out.println(sj);

        sj = new StringJoiner(",");
        sj.setEmptyValue("Empty"); // sets Empty as default value if no string value is passed/set
        System.out.println(sj);

        sj = new StringJoiner(",", "[", "]");
        sj.setEmptyValue("Empty"); // sets Empty as default value if no string value is passed/set
        System.out.println(sj); // The default (Empty) value is returned only when string joiner is empty

        // merge two joiners
        StringJoiner rgbJoiner = new StringJoiner(
                ",", "[", "]");
        StringJoiner cmybJoiner = new StringJoiner(
                "-", "[", "]");

        rgbJoiner.add("Red")
                .add("Green")
                .add("Blue");
        cmybJoiner.add("Cyan")
                .add("Magenta")
                .add("Yellow")
                .add("Black");
        System.out.println(rgbJoiner.merge(cmybJoiner));
    }
}
