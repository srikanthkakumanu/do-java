package basic;

import java.util.Currency;
import java.util.Locale;

public class Currencies {
    public static void main(String[] args) {
        Currency c = Currency.getInstance(Locale.US);
        System.out.printf("Symbol: %s, Fractional Digits: %d\n", c.getSymbol(), c.getDefaultFractionDigits());
    }
}
