package collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Objects;
import java.util.Collections;
import java.util.HashSet;

/**
 * Basic example of all Collection<E> methods usage by using a ArrayList concrete implementation.
 */
public class TheCollection {
    public static void main(String[] args) {

        bulk();
        // aggregate();

        
    }

    /**
     * Examples of all standard/old methods of collection which
     * modify the original collection when used.
     * Note: Aggregate methods does not change the original collection.
     */
    private static void bulk() {
        Collection<Integer> c = new ArrayList<>(List.of(2, 23, 43, 22, 43));
        Collection<Integer> some = new ArrayList<>(List.of(23, 43));

        System.out.println(c.contains(2));
        System.out.println(c.containsAll(some));
        System.out.println(c.add(15));
        System.out.println(c.addAll(List.of(494, 38389)));
        System.out.println(c.add(null));
        System.out.println(c.add(null));
        c.forEach(System.out::println);
        // Removing Nulls from a collection
        System.out.println("Removed Nulls: " + c.removeAll(Collections.singleton(null)));
        c.forEach(System.out::println);
        
        PhoneTask mikePhone = new PhoneTask("Mike", "987 6543");
        PhoneTask paulPhone = new PhoneTask("Paul", "123 4567");
        CodingTask databaseCode = new CodingTask("db");
        CodingTask guiCode = new CodingTask("gui");
        CodingTask logicCode = new CodingTask("logic");

        // This task will be useful when we construct range views of sorted sets
        // Collection<PhoneTask> phoneTasks = new HashSet<>();
        // Collection<CodingTask> codingTasks = new HashSet<>();
        // Collection<Task> mondayTasks = new HashSet<>();
        // Collection<Task> tuesdayTasks = new HashSet<>();
        // Collections.addAll(phoneTasks, mikePhone, paulPhone);
        // Collections.addAll(codingTasks, databaseCode, guiCode, logicCode);
        // Collections.addAll(mondayTasks, logicCode, mikePhone);
        // Collections.addAll(tuesdayTasks, databaseCode, guiCode, paulPhone);

        // above commented code can be condensed like below.
        var phoneTasks = Stream.of(mikePhone, paulPhone).collect(Collectors.toSet());
        var codingTasks = Stream.of(databaseCode, guiCode, logicCode).collect(Collectors.toSet());
        var mondayTasks = Stream.of(logicCode, mikePhone).collect(Collectors.toSet());
        var tuesdayTasks = Stream.of(databaseCode, guiCode, paulPhone).collect(Collectors.toSet());
        
        PhoneTask ruthPhone = new PhoneTask("Ruth", "567 1234");
        mondayTasks.add(ruthPhone);

        var allTasks = Stream.of(mondayTasks,tuesdayTasks)
                                .flatMap(Collection::stream)
                                .collect(Collectors.toSet());

        var tuesdayNonPhoneTasks = tuesdayTasks.stream()
                                .filter(t -> ! phoneTasks.contains(t));
        
        var phoneTuesdayTasks = tuesdayTasks.stream()
                                .filter(phoneTasks::contains).collect(Collectors.toSet());
        
        var tuesdayPhoneTasks = phoneTasks.stream()
                                .filter(tuesdayTasks::contains).collect(Collectors.toSet());

        var tuesdayCodeTasks = tuesdayTasks.stream()
                                .filter(t -> !(t instanceof PhoneTask))
                                .collect(Collectors.toSet());
        
        
    }
    /**
     * Examples of all standard aggregate methods that collection provides.
     */
    private static void aggregate() {

        Collection<Integer> c = new ArrayList<>(List.of(2, 23, 43, 22, 43));
        Collection<Integer> some = new ArrayList<>(List.of(23, 43));

        // filter and for-each
        c.parallelStream()
                .filter(e -> e != 2)
                .forEach(System.out::println);

        // map and collect
        String joiner = c.parallelStream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        System.out.println(joiner);

        // only reduce
        System.out.println(c.parallelStream().reduce(0, Integer::sum));


    }
}


interface Task extends Comparable<Task> {
    @Override
    default int compareTo(Task o) {
        return toString().compareTo(o.toString());
    }  
}

record CodingTask(String spec) implements Task {}
record PhoneTask(String name, String number) implements Task {}
record EmptyTask() implements Task {
    public String toString() {  return "";   }
}
