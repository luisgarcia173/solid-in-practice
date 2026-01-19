package io.github.luisgarcia173;

import io.github.luisgarcia173.before.Order;
import io.github.luisgarcia173.before.OrderProcessor;
import io.github.luisgarcia173.after.service.OrderService;
import io.github.luisgarcia173.after.service.OrderValidator;
import io.github.luisgarcia173.after.service.PriceCalculator;
import io.github.luisgarcia173.after.policy.DiscountPolicy;
import io.github.luisgarcia173.after.policy.SeasonalDiscount;
import io.github.luisgarcia173.after.repository.InMemoryOrderRepository;
import io.github.luisgarcia173.after.notifier.EmailNotifier;
import io.github.luisgarcia173.after.model.OrderTotals;

public class Main {
    public static void main(String[] args) {
        System.out.println("== SRP Demo: BEFORE (God class) ==");

        Order order1 = new Order("order-1", "customer@example.com", "BR", false);
        order1.addItem("Widget", 120.0, 1);
        order1.addItem("Gadget", 80.0, 2);

        OrderProcessor processor = new OrderProcessor();
        processor.process(order1);

        System.out.println("\n== SRP Demo: AFTER (refactored) ==");

        Order order2 = new Order("order-2", "customer@example.com", "BR", false);
        order2.addItem("Widget", 120.0, 1);
        order2.addItem("Gadget", 80.0, 2);

        OrderValidator validator = new OrderValidator();
        PriceCalculator priceCalculator = new PriceCalculator();
        DiscountPolicy discountPolicy = new SeasonalDiscount();
        InMemoryOrderRepository repository = new InMemoryOrderRepository();
        EmailNotifier notifier = new EmailNotifier();

        OrderService service = new OrderService(validator, priceCalculator, discountPolicy, repository, notifier);
        OrderTotals totals = service.process(order2);

        System.out.println("After totals: " + totals);

        System.out.println("== End demo ==");
    }
}