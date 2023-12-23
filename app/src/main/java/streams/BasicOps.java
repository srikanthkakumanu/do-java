package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import java.util.List;

public class BasicOps {
    public static void main(String[] args) {
        String[] ar = new String[] {"a", "b", "c", "d", "e", "a", "b", "c", "d", "e"};
        // Basic stream creation
        Stream<String> stream = Arrays.stream(ar);
        System.out.println(stream.count());

        stream = Stream.of(ar);
        // stream = Stream.of("a", "b", "c");
        System.out.println(stream.count());

        // Multithreaded i.e. parallel streams
        Stream.of(ar).parallel().forEach(e -> System.out.println(e));
        Stream.of(ar).parallel().forEachOrdered(e -> System.out.println(e));

        // Basic stream ops
        stream = Stream.of(ar);
        System.out.println(stream.distinct().count());

        // parallel streams
        Collection<Book> books = new ArrayList<>();
        books.add(new Book("Thousand Lights in the Sea", "Ten Tsu", 1923));
        books.add(new Book("Art of War", "Ten Tsu", 1932));
        books.add(new Book("A Autobiograph of a Monk", "Swami Yogananda", 1915));
        books.add(new Book("The Berlin Downfall", "Antony Beevor", 1965));
        books.add(new Book("Hampi - a Forgotten Empire", "Fernio Nunez", 1970));

        AtomicLong countOfBooks = new AtomicLong();
        books.parallelStream().forEach(
            book -> {
            if(book.getYearPublished() == 1915)
                countOfBooks.incrementAndGet();
        });

        System.out.printf("Total Count of Books for 1915: %d \n", countOfBooks.get());


        List<Integer> nums = List.of(32, 53, 32, 34, 32, 21, 43, 53);
        long distinctCount = nums
                                .stream() // source of data
                                .distinct() // intermediate operation - which returns Stream<T>
                                .count(); // terminal operation - which gives a definite result
        System.out.println(distinctCount);

        boolean isExist = nums
                            .stream() // source of data
                            .anyMatch(e -> e == 32); // takes a Predicate and returns definite result
        System.out.println(isExist);

        ArrayList<String> list = new ArrayList<>();
        list.add("One");
        list.add("OneAndOnly");
        list.add("Derek");
        list.add("Change");
        list.add("factory");
        list.add("justBefore");
        list.add("Italy");
        list.add("Italy");
        list.add("Thursday");
        list.add("");
        list.add("");

        Stream<String> sm = list.stream().filter(v -> v.contains("d"));
        sm.forEachOrdered(System.out::println);
    }
}


class Book { 
    private String name, author;
    private int yearPublished;
    
    
    public Book(String name, String author, int yearPublished) {
        this.name = name;
        this.author = author;
        this.yearPublished = yearPublished;
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public int getYearPublished() {
        return yearPublished;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }  
}