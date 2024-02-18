package patterns.decorator.common;

import java.util.List;

public interface CoffeeMaker {
    List<String> getIngredients();
    Coffee prepare();
}
