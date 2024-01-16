package basic;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Formatter;

public class Formatters {
    public static void main(String[] args) {
        Formatter fmt = new Formatter();
        LocalDate now = LocalDate.now();
        fmt.format("Floating-point hexadecimal (a or A): %a \n" +
                "Boolean (b or B): %b \n" +
                "Character (c or C): %c \n" +
                "Decimal Integer (d): %d \n" +
                "Hashcode of an argument (h or H): %h \n" +
                "Scientific Notation (e or E): %e \n" +
                "Uses e or f (g or G): %g \n" + // Uses %e or %f, based on the value being formatted and the precision
                "Octal Integer (o): %o \n" +
                "New Line character (n): %n \n" +
                "Time and Date (t or T): %tm/%<td/%<ty \n" +
                "Integer hexadecimal (x or X): %x \n" +
                "Percentage sign : %% \n",
                3.14159, true, 'd', 83, "hashcode", 38.483938,
                5859.383849, 383129, now, 1504);
        System.out.println(fmt.toString());
        // In general, you should close a Formatter when you are done using it.
        // Doing so free and resources that it was using.
        fmt.close();

        fmt.format("Time and Date: %tm/%<td/%<ty", now);
        System.out.println(fmt.toString());

        for(double i=1.23; i < 1.0e+6; i *= 100) {
            fmt.format("%f %e ", i, i);
            System.out.println(fmt);
        }
        fmt.close();

        fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        // Display standard 12-hour time format.
        fmt.format("%tr", cal);
        System.out.println(fmt);
        fmt.close();
        // Display complete time and date information.
        fmt = new Formatter();
        fmt.format("%tc", cal);
        System.out.println(fmt);
        fmt.close();
        // Display just hour and minute.
        fmt = new Formatter();
        fmt.format("%tl:%tM", cal, cal);
        System.out.println(fmt);
        fmt.close();
        // Display month by name and number.
        fmt = new Formatter();
        fmt.format("%tB %tb %tm", cal, cal, cal);
        System.out.println(fmt);
        fmt.close();

        fmt = new Formatter();
        fmt.format("Copying file%nTransfer is %d%% complete", 88);
        System.out.println(fmt);
        fmt.close();

        // Demonstrate a field-width specifier.
        fmt = new Formatter();
        fmt.format("|%f|%n|%12f|%n|%012f|",
                10.12345, 10.12345, 10.12345);
        System.out.println(fmt);
        fmt.close();

        // Create a table of squares and cubes.
        for(int i=1; i <= 10; i++) {
            fmt = new Formatter();
            fmt.format("%4d %4d %4d", i, i*i, i*i*i);
            System.out.println(fmt);
            fmt.close();
        }

        // Demonstrate the precision modifier.
        fmt = new Formatter();
        // Format 4 decimal places.
        fmt.format("%.4f", 123.1234567);
        System.out.println(fmt);
        fmt.close();
        // Format to 2 decimal places in a 16 character field
        fmt = new Formatter();
        fmt.format("%16.2e", 123.1234567);
        System.out.println(fmt);
        fmt.close();
        // Display at most 15 characters in a string.
        fmt = new Formatter();
        fmt.format("%.15s", "Formatting with Java is now easy.");
        System.out.println(fmt);
        fmt.close();

        // Demonstrate left justification.
        fmt = new Formatter();
        // Right justify by default
        fmt.format("|%10.2f|", 123.123);
        System.out.println(fmt);
        fmt.close();
        // Now, left justify.
        fmt = new Formatter();
        fmt.format("|%-10.2f|", 123.123);
        System.out.println(fmt);
        fmt.close();

        // Demonstrate the space format specifiers.
        fmt = new Formatter();
        fmt.format("% d", -100);
        System.out.println(fmt);
        fmt.close();
        fmt = new Formatter();
        fmt.format("% d", 100);
        System.out.println(fmt);
        fmt.close();
        fmt = new Formatter();
        fmt.format("% d", -200);
        System.out.println(fmt);
        fmt.close();
        fmt = new Formatter();
        fmt.format("% d", 200);
        System.out.println(fmt);
        fmt.format("%(d", -100);
        // To show negative numeric output inside parentheses,
        // rather than with a leading –, use the ( flag
        fmt.format("%(d", -100);
        System.out.println(fmt);
        // comma flag
        fmt.format("%,.2f", 4356783497.34);
        System.out.println(fmt);
        // Using an argument index
        fmt.format("%3$d %1$d %2$d", 10, 20, 30);
        System.out.println(fmt);
        fmt.format("%d in hex is %1$x", 255);
        System.out.println(fmt);
        fmt.format("%d in hex is %<x", 255);
        System.out.println(fmt);
        fmt.close();

        // Relative indexes are especially useful
        // when creating custom time and date formats.
        fmt = new Formatter();
        cal = Calendar.getInstance();
        fmt.format("Today is day %te of %<tB, %<tY", cal);
        System.out.println(fmt);
        fmt.close();
    }
}
