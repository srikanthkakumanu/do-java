package basic;


import java.util.function.Function;

public class Rough {
    public static void main(String[] args) {
        Function<String, String> removeLowerCaseA =
                s -> s.replace("a", "");
        Function<String, String> upperCase = String::toUpperCase;

        var input = "abcd";
        var result = removeLowerCaseA.andThen(upperCase).apply(input);
        System.out.println(result);
        result = upperCase.compose(removeLowerCaseA).apply(input);
        System.out.println(result);
    }
}

