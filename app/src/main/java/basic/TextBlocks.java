package basic;

/**
 * Text blocks are introduced in Java 15.
 * A text block is a sequence of characters that occupy more than one line.
 */
public class TextBlocks {
    public static void main(String[] args) {
        String text = """
                Notice something else about this example. Because the last line ends with a newline, that\s
                newline will also be in the resulting string. If you don’t want a trailing newline, then put the\s
                closing delimiter at the end of the last line, like this:""";
        System.out.println(text);
        text = """
                Text blocks support strings that
                span two or more lines and preserve
                    indentation. They reduce the
                        tedium associated with the
                     entry of long or complicated
                strings into a program.
                """;
        System.out.println(text);
    }
}
