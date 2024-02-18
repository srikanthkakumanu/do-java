package patterns.decorator.ood;

import patterns.decorator.common.*;

/**
 * The Decorator design pattern is a structural pattern that allows for modifying
 * object behavior at runtime. Instead of subclassing, an object is wrapped
 * inside a "Decorator" that contains the desired behavior.
 */
public class Main {
    public static void main(String[] args) {

        // Café con Leche
        CoffeeMaker coffeeMaker = new BlackCoffeeMaker();
        CoffeeMaker decoratedCoffeeMaker = new AddMilkDecorator(coffeeMaker, new MilkCarton());
        System.out.println("Ingredients for Café con Leche: " + decoratedCoffeeMaker.getIngredients());

        // Add Sugar
        CoffeeMaker lastDecoratedCoffeeMaker = new AddSugarDecorator(decoratedCoffeeMaker);
        System.out.println("Ingredients after adding sugar: " + lastDecoratedCoffeeMaker.getIngredients());

        Coffee sweetCafeConLeche = lastDecoratedCoffeeMaker.prepare();
        System.out.println(sweetCafeConLeche);
    }
}
