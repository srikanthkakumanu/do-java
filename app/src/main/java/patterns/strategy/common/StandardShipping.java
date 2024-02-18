package patterns.strategy.common;

public class StandardShipping implements ShippingStrategy {
    @Override
    public void ship(Parcel parcel) {
        System.out.println("Shipping Parcel with '" + getClass().getSimpleName() + "'");
    }
}
