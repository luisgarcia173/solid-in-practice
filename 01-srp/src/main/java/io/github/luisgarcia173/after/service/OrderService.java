package io.github.luisgarcia173.after.service;

import io.github.luisgarcia173.after.model.OrderTotals;
import io.github.luisgarcia173.after.policy.DiscountPolicy;
import io.github.luisgarcia173.after.repository.OrderRepository;
import io.github.luisgarcia173.after.notifier.Notifier;
import io.github.luisgarcia173.before.Order;

public class OrderService {
    private final OrderValidator validator;
    private final PriceCalculator priceCalculator;
    private final DiscountPolicy discountPolicy;
    private final OrderRepository repository;
    private final Notifier notifier;

    public OrderService(OrderValidator validator, PriceCalculator priceCalculator, DiscountPolicy discountPolicy, OrderRepository repository, Notifier notifier) {
        this.validator = validator;
        this.priceCalculator = priceCalculator;
        this.discountPolicy = discountPolicy;
        this.repository = repository;
        this.notifier = notifier;
    }

    public OrderTotals process(Order order) {
        validator.validate(order);

        double subtotal = priceCalculator.calculateSubtotal(order);
        double afterDiscount = discountPolicy.apply(order, subtotal);
        double discount = subtotal - afterDiscount;
        double shipping = priceCalculator.calculateShipping(order, afterDiscount);
        double total = Math.round((afterDiscount + shipping) * 100.0) / 100.0;

        OrderTotals totals = new OrderTotals(subtotal, discount, shipping, total);

        repository.save(order, totals);
        notifier.notify(order, totals);

        return totals;
    }
}
