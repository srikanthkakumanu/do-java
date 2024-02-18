package patterns.decorator.fp;

import patterns.decorator.common.AddMilkDecorator;
import patterns.decorator.common.AddSugarDecorator;
import patterns.decorator.common.CoffeeMaker;
import patterns.decorator.common.MilkCarton;

import java.util.function.Function;

public final class Decorations {
    public static Function<CoffeeMaker, CoffeeMaker> addMilk(MilkCarton milkCarton) {
        return coffeeMaker -> new AddMilkDecorator(coffeeMaker, milkCarton);
    }

    public static Function<CoffeeMaker, CoffeeMaker> addSugar() {
        return AddSugarDecorator::new;
    }

    // ...
}
