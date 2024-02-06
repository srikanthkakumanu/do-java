package basic;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringJoiner;

public class DateTimeUtils {
    public static void main(String[] args) {
        System.out.println("\n-----Calendar and Gregorian Calendar----\n\n");
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May",
                            "Jun", "Jul", "Aug", "Sep", "Oct",
                            "Nov", "Dec"};
        Calendar c = Calendar.getInstance();
        System.out.printf("Date: %s-%s-%s\n",
                months[c.get(Calendar.MONTH)],
                c.get(Calendar.DATE),
                c.get(Calendar.YEAR));
        System.out.printf("Time: %s:%s:%s\n",
                c.get(Calendar.HOUR),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND));

        c.set(Calendar.HOUR, 10);
        c.set(Calendar.MINUTE, 29);
        c.set(Calendar.SECOND, 22);
        System.out.printf("Time: %s:%s:%s\n",
                c.get(Calendar.HOUR),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND));

        GregorianCalendar gc = new GregorianCalendar();
        System.out.printf("Date: %s-%s-%s\n",
                months[gc.get(Calendar.MONTH)],
                gc.get(Calendar.DATE),
                gc.get(Calendar.YEAR));
        System.out.printf("Time: %s:%s:%s\n",
                gc.get(Calendar.HOUR),
                gc.get(Calendar.MINUTE),
                gc.get(Calendar.SECOND));
        System.out.println(gc.isLeapYear(gc.get(Calendar.YEAR)));

        // Date Time API - Temporal Types
        System.out.println("\n-----Date & Time API - Temporal Types----\n\n");
        // TemporalQuery<Boolean> == Predicate<TemporalAccessor>
        boolean isItTeaTime = LocalDateTime.now()
                .query(temporal -> {
                    var time = LocalTime.from(temporal);
                    return time.getHour() >= 16;
                });
        // The utility class TemporalQueries provides predefined queries,
        // to eliminate the need to create common queries yourself.
        // TemporalQuery<LocalTime> == Function<TemporalAccessor, Localtime>
        LocalTime time = LocalDateTime.now().query(LocalTime::from);

        
    }
}
