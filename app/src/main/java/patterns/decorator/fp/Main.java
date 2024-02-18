package patterns.decorator.fp;

import patterns.decorator.common.AddMilkDecorator;
import patterns.decorator.common.AddSugarDecorator;
import patterns.decorator.common.BlackCoffeeMaker;
import patterns.decorator.common.CoffeeMaker;
import patterns.decorator.common.MilkCarton;

/**
 * The Decorator design pattern is a structural pattern that allows for modifying
 * object behavior at runtime. Instead of subclassing, an object is wrapped
 * inside a "Decorator" that contains the desired behavior.
 */
public class Main {

    public static void main(String[] args) {

        // PARAMETERIZED DECORATION
        CoffeeMaker decoratedCoffeeMaker = Barista.decorate(new BlackCoffeeMaker(),
                coffeeMaker -> new AddMilkDecorator(coffeeMaker,
                        new MilkCarton()));

        System.out.println("[Parameterized] Ingredients for Café con Leche: " + decoratedCoffeeMaker.getIngredients());

        CoffeeMaker finalCoffeeMaker = Barista.decorate(decoratedCoffeeMaker,
                AddSugarDecorator::new);


        // Add Sugar
        CoffeeMaker lastDecoratedCoffeeMaker = new AddSugarDecorator(decoratedCoffeeMaker);
        System.out.println("[Parameterized] Ingredients after adding sugar: " + lastDecoratedCoffeeMaker.getIngredients());


        // MULTI-DECORATION
        CoffeeMaker multiDecoratedCoffeeMaker = Barista.decorate(new BlackCoffeeMaker(),
                coffeeMaker -> new AddMilkDecorator(coffeeMaker,
                        new MilkCarton()),
                AddSugarDecorator::new);

        System.out.println("[Multi] Ingredients: " + lastDecoratedCoffeeMaker.getIngredients());

        // DECORATIONS-HELPER

        var milkCarton = new MilkCarton();
        CoffeeMaker maker = Barista.decorate(new BlackCoffeeMaker(),
                Decorations.addMilk(milkCarton),
                Decorations.addSugar());

        System.out.println("[Helper] Ingredients: " + lastDecoratedCoffeeMaker.getIngredients());
    }
}
