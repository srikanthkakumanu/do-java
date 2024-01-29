package basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 Regular expressions
 -------------------
 A regular expression comprises normal characters, character classes (sets of characters),
 wildcard characters, and quantifiers.
 *
 pattern matching or string matching
 -----------------------------------
 [abc] = a,b or c
 [^abc] = inverted - any character except a,b,c
 [a-z] = A set of characters - any character between a to z
 [A-Z] = A set of characters - any character between A to Z
 [a-z,A-Z] = a to z, A to Z
 [0-9] = 0 to 9
 *
 quantifiers
 -----------
 A quantifier determine how many times an expression is matched.
 + = Match one or more.
 = Match zero or more.
 ? = Match zero or one.
 */
public class RegExpns {
    public static void main(String[] args) {
        test("a", ".");
        test("abc", "...");
        test("Hello", "^Hello");
        test("Hello World Hello Universe", "^Hello");
        test("Hello World Hello Universe", "Universe$");

        // Using Regex API
        Pattern p;
        Matcher m;
        boolean found;

        p = Pattern.compile("Java");
        m = p.matcher("Java");
        found = m.matches();
        System.out.println(found ? "Matched" : "Not Matched");
        // Use find() to find subsequence
        p = Pattern.compile("Java");
        m = p.matcher("Java SE");
        System.out.println(m.find() ? "Java is found in Java SE" : "Java is not found");
        // Use find() to find multiple subsequences
        p = Pattern.compile("test");
        m = p.matcher("test 1 2 3 test");
        while(m.find())
            System.out.println("test is found at index : " + m.start());
        // Use a quantifier
        p = Pattern.compile("W+");
        m = p.matcher("W WW WWW");
        while(m.find())
            System.out.println("Match: " + m.group());
        // Use wildcard and quantifier
        p = Pattern.compile("e.+d");
        m = p.matcher("extend cup end table");
        while(m.find())
            System.out.println("Match: " + m.group());

        // replaceAll
        String str = "Jon Jonathan Frank Ken Todd";
        p = Pattern.compile("Jon.*? ");
        m = p.matcher(str);
        System.out.println("Original sequence: " + str);
        str = m.replaceAll("Eric ");
        System.out.println("Modified sequence: " + str);

    }

    private static void test(String text, String regex) {
        System.out.printf(text.matches(regex) + ":- [text=%s, regex=%s]\n", text, regex);
    }
}
