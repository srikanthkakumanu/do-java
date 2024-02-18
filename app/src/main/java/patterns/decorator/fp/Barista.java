package patterns.decorator.fp;

import patterns.decorator.common.CoffeeMaker;

import java.util.Arrays;
import java.util.function.Function;

public final class Barista {

    private Barista() {
        // Suppress default constructor.
        // Ensures non-instantiability and non-extensibility
    }

    public static CoffeeMaker decorate(CoffeeMaker coffeeMaker,
                                       Function<CoffeeMaker, CoffeeMaker> decorator) {
        return decorator.apply(coffeeMaker);
    }

    public static CoffeeMaker decorate(CoffeeMaker coffeeMaker,
                                       Function<CoffeeMaker, CoffeeMaker>... decorators) {
        Function<CoffeeMaker, CoffeeMaker> reducedDecorations =
                Arrays.stream(decorators)
                        .reduce(Function.identity(), Function::andThen);

        return reducedDecorations.apply(coffeeMaker);
    }
}
