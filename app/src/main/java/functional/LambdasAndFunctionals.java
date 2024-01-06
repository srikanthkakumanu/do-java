package functional;

interface Something { String doIt(String v); }

public class LambdasAndFunctionals {
    public static void main(String... args) {
        String input = "Lambda expression";
        // passing lambda expression as input into a method
        String output = op(str -> str.toUpperCase(), input);
        System.out.println(output);

        output = op(str -> {
            String result = "";
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) != ' ')
                    result += str.charAt(i) + "-";
            }
            return result;
        }, input);
        System.out.println(output);
    }

    /**
     * Passing lambda expression as a method argument
     * @param st
     * @param s
     * @return
     */
    static String op(Something st, String s) {
        return st.doIt(s);
    }

    
}
