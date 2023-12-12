package generics;

import java.util.StringJoiner;

public class Generics3 {
    public static void main(String[] args) {

        // simple example with single type parameter
        Pair<String> strings = new Pair<>("Hello", "World");
        Pair<Integer> ints = new  Pair<Integer>(23, 32);

        System.out.println(strings);
        System.out.println(ints);

        Pair<Person> people = new Pair<Generics3.Person>(new Person(("Srikanth")), new Person("God"));
        System.out.println(people);

        // simple example with multi type parameter
        MultiPair<String, String> stringsMultiPair = new MultiPair("Hello", "World");
        MultiPair<Integer, Integer> inMultiPair = new MultiPair<Integer,Integer>(32, 43);

        System.out.println(stringsMultiPair);
        System.out.println(inMultiPair);

        MultiPair<Person, Person> multiPeople = new MultiPair<>(new Person("Srikanth"), new Person("Kakumanu"));
        System.out.println(multiPeople);

        MultiPair<String, Integer> stringIntegerPair = new MultiPair<>("one", 1);
        String first = stringIntegerPair.getFirst();
        Integer second = inMultiPair.getSecond();
        
    }

    private  static class Pair<T> {
        private final T first, second;
        public Pair(T first,  T  second) {
            this.first = first;
            this.second = second;
        }
        public T getFirst() {
            return first;
        }
        public T getSecond() {
            return second;
        }
        @Override
        public String toString() {
            return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                                            .add("First = '" + first + "'")
                                            .add("Second ='" + second + "'")
                                            .toString();
        }
    }

    private static class Person {
        private String name;
        
        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                                            .add("Person ='" + name + "'")
                                            .toString();
        }
    }

    private static class MultiPair<T, S> {
        private final T first;
        private final S second;

        public MultiPair(T first, S second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public S getSecond() {
            return second;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                    .add("First = '" + first + "'")
                    .add("Second ='" + second + "'")
                    .toString();
        }
    }

}
