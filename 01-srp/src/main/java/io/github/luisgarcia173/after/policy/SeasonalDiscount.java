package io.github.luisgarcia173.after.policy;

import io.github.luisgarcia173.before.Order;

public class SeasonalDiscount implements DiscountPolicy {
    @Override
    public double apply(Order order, double subtotal) {
        double discount;
        if (order.isVip()) {
            discount = subtotal * 0.12; // better VIP discount
        } else if (subtotal > 500) {
            discount = subtotal * 0.08;
        } else {
            discount = subtotal * 0.03;
        }

        if ("BR".equalsIgnoreCase(order.getCountry())) {
            discount += 5.0;
        }

        return Math.round((subtotal - discount) * 100.0) / 100.0;
    }
}
