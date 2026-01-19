package io.github.luisgarcia173.before;

import io.github.luisgarcia173.before.infra.EmailClient;
import io.github.luisgarcia173.before.infra.JdbcConnection;

import java.time.LocalDateTime;

public class OrderProcessor {
    // Hardcoded configuration (shows tight coupling to config)
    private static final double SHIPPING_RATE = 0.10; // 10% of subtotal
    private static final double VIP_DISCOUNT = 0.10; // 10%
    private static final double NORMAL_DISCOUNT = 0.05; // 5%
    private static final double FREE_SHIPPING_LIMIT = 200.0;

    // Implicit dependencies instantiated inside the class
    private final EmailClient emailClient = new EmailClient();
    private final JdbcConnection jdbc = new JdbcConnection("jdbc:fake://localhost/orders");

    public static class Totals {
        public double subtotal;
        public double discount;
        public double shipping;
        public double total;
    }

    public void process(Order order) {
        log("Starting processing order " + order.getId());

        try {
            validate(order);

            double subtotal = calculateSubtotal(order);

            // BUG: apply discount before shipping (intentional) and apply discount twice
            double discounted = applyDiscount(order, subtotal);

            double shipping = calculateShipping(order);

            // Another bug: applyDiscount again (duplicate)
            double discountedAgain = applyDiscount(order, discounted);

            // incorrect rounding: truncates to two decimals by multiplying and casting to int (buggy)
            double total = Math.floor((discountedAgain + shipping) * 100) / 100.0;

            Totals totals = new Totals();
            totals.subtotal = subtotal;
            totals.discount = subtotal - discountedAgain; // confusing/incorrect metric
            totals.shipping = shipping;
            totals.total = total;

            persist(order, totals);
            sendConfirmation(order, totals);

            log("Finished processing order " + order.getId() + " at " + LocalDateTime.now());
            log("Totals: subtotal=" + totals.subtotal + " discount=" + totals.discount + " shipping=" + totals.shipping + " total=" + totals.total);
        } catch (RuntimeException ex) {
            log("Failed to process order " + order.getId() + " reason=" + ex.getMessage());
            throw ex;
        }
    }

    public void validate(Order order) {
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        // incomplete validation: doesn't check for negative prices or quantities
        for (OrderItem item : order.getItems()) {
            if (item.quantity() <= 0) {
                throw new IllegalArgumentException("Item quantity must be > 0");
            }
        }

        if (order.getCustomerEmail() == null || !order.getCustomerEmail().contains("@")) {
            // validation incomplete: accepts many invalid emails
            throw new IllegalArgumentException("Invalid customer email");
        }
    }

    public double calculateSubtotal(Order order) {
        double subtotal = 0.0;
        for (OrderItem item : order.getItems()) {
            subtotal += item.unitPrice() * item.quantity();
        }
        return subtotal;
    }

    public double applyDiscount(Order order, double subtotal) {
        double discount = 0.0;

        if (order.isVip()) {
            discount = subtotal * VIP_DISCOUNT;
        } else if (subtotal > 500) {
            // business rule mixed here
            discount = subtotal * 0.07; // 7% for large orders
        } else {
            discount = subtotal * NORMAL_DISCOUNT;
        }

        // conditional on country (mixed rules)
        if ("BR".equalsIgnoreCase(order.getCountry())) {
            discount += 5.0; // flat BR discount
        }

        double result = subtotal - discount;

        // Intentional rounding bug: uses Math.round which returns long when single arg
        result = Math.round(result * 100.0) / 100.0; // okay-ish but combined with floor earlier causes issues

        log("Applied discount: original=" + subtotal + " after=" + result);
        return result;
    }

    public double calculateShipping(Order order) {
        double subtotal = calculateSubtotal(order);

        if (subtotal >= FREE_SHIPPING_LIMIT) {
            return 0.0;
        }

        // shipping depends on country and weight (not modeled) - simplified
        double ship = subtotal * SHIPPING_RATE;

        if ("BR".equalsIgnoreCase(order.getCountry())) {
            // country surcharge
            ship += 10.0;
        }

        return ship;
    }

    public void persist(Order order, Totals totals) {
        // writes SQL directly, side-effect in the middle of processing
        String sql = String.format("INSERT INTO orders(id, subtotal, discount, shipping, total) VALUES('%s', %.2f, %.2f, %.2f, %.2f)",
                order.getId(), totals.subtotal, totals.discount, totals.shipping, totals.total);
        jdbc.execute(sql);
    }

    public void sendConfirmation(Order order, Totals totals) {
        String body = "Thank you for your order " + order.getId() + "\nTotal: " + totals.total;
        emailClient.send(order.getCustomerEmail(), "Order confirmation", body);
    }

    public void log(String msg) {
        System.out.println("[OrderProcessor] " + msg);
    }

}
