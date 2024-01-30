package records;

/**
 * Alternative to throwing the exceptions, you can data scrub the logic
 * to generate a valid record.
 */
public class RecordScrubLogic {
    public static void main(String[] args) {
        var time = new Time(12, 67);
        System.out.println(time);
    }

    // Data scrubbing: You can scrub the logic to generate a valid record
    record Time (int minutes, int seconds) {
        public Time {
            if (seconds >= 60) {
                int additionalMinutes = seconds / 60;
                minutes += additionalMinutes;
                seconds -= additionalMinutes * 60;
            }
        }
    }
}


