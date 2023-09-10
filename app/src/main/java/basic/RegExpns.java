package basic;

/**
 * Regular expressions
 * -------------------
 *
 * pattern matching or string matching
 * -----------------------------------
 * [abc] = a,b or c
 * [^abc] = any character except a,b,c
 * [a-z] = any character between a to z
 * [A-Z] = any character between A to Z
 * [a-z,A-Z] = a to z, A to Z
 * [0-9] = 0 to 9
 *
 * quantifiers
 * -----------
 *
 *
 */
public class RegExpns {
    public static void main(String[] args) {
        test("a", ".");
        test("abc", "...");
        test("Hello", "^Hello");
        test("Hello World Hello Universe", "^Hello");
        test("Hello World Hello Universe", "Universe$");
    }

    private static void test(String text, String regex) {
        System.out.printf(text.matches(regex) + ":- [text=%s, regex=%s]\n", text, regex);
    }
}
