package patterns.strategy.common;

public interface ShippingService {
    default void ship(Parcel parcel, ShippingStrategy strategy) {
        strategy.ship(parcel);
    }
}
