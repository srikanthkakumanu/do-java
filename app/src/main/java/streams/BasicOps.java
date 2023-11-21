package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

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