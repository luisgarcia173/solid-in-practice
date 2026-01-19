package io.github.luisgarcia173.after.policy;

import io.github.luisgarcia173.before.Order;

public interface DiscountPolicy {
    double apply(Order order, double subtotal);
}
