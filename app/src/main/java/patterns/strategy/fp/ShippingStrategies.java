package patterns.strategy.fp;

import patterns.strategy.common.ExpeditedShipping;
import patterns.strategy.common.ShippingStrategy;
import patterns.strategy.common.StandardShipping;

public final class ShippingStrategies {
    public static ShippingStrategy expedited(boolean signatureRequired) {
        return new ExpeditedShipping(signatureRequired);
    }

    public static ShippingStrategy standard() {
        return new StandardShipping();
    }
}
