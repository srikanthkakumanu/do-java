package patterns.strategy.ood;

import patterns.strategy.common.ExpeditedShipping;
import patterns.strategy.common.Parcel;
import patterns.strategy.common.ShippingService;

/**
 * The strategy pattern is a behavioral pattern. It lets you define a family
 * of algorithms, put each of them into a separate class, and make their
 * objects interchangeable.
 *
 * The strategy pattern suggests that you take a class that does something
 * in a lot of different ways and extract all of these algorithms into
 * separate classes called <B>strategies</B>.
 *
 * The original class is called <B>context</B>, must have a field for storing
 * a reference to one of the strategies. The <B>context delegates the work to
 * a linked strategy object instead of executing it on its own.</B>
 */
public class Main {
    public static void main(String[] args) {
        var service = new ShippingService() {};
        var parcel = new Parcel();
        var strategy = new ExpeditedShipping(true);
        service.ship(parcel, strategy);
    }
}
