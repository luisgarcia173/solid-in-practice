package io.github.luisgarcia173.after.service;

import io.github.luisgarcia173.before.Order;
import io.github.luisgarcia173.before.OrderItem;

public class OrderValidator {
    public void validate(Order order) {
        if (order.getItems().isEmpty()) throw new IllegalArgumentException("Order must contain at least one item");
        for (OrderItem item : order.getItems()) {
            if (item.quantity() <= 0) throw new IllegalArgumentException("Item quantity must be > 0");
            if (item.unitPrice() < 0) throw new IllegalArgumentException("Unit price must be >= 0");
        }
        if (order.getCustomerEmail() == null || !order.getCustomerEmail().contains("@")) throw new IllegalArgumentException("Invalid email");
    }
}
