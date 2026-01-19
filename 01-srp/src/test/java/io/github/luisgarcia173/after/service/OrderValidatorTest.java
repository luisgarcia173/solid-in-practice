package io.github.luisgarcia173.after.service;

import io.github.luisgarcia173.before.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderValidatorTest {

    private final OrderValidator validator = new OrderValidator();

    @Test
    void validate_happyPath_noException() {
        Order order = new Order("o1", "a@b.com", "US", false);
        order.addItem("i1", 10.0, 1);

        assertDoesNotThrow(() -> validator.validate(order));
    }

    @Test
    void validate_emptyItems_throws() {
        Order order = new Order("o2", "a@b.com", "US", false);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validator.validate(order));
        // message should mention at least one item
        assert(ex.getMessage().contains("at least one item"));
    }

    @Test
    void validate_zeroQuantity_throws() {
        Order order = new Order("o3", "a@b.com", "US", false);
        order.addItem("i1", 10.0, 0);
        assertThrows(IllegalArgumentException.class, () -> validator.validate(order));
    }

    @Test
    void validate_negativeUnitPrice_throws() {
        Order order = new Order("o4", "a@b.com", "US", false);
        order.addItem("i1", -0.01, 1);
        assertThrows(IllegalArgumentException.class, () -> validator.validate(order));
    }

    @Test
    void validate_invalidEmail_throws() {
        Order order = new Order("o5", "no-at-sign", "US", false);
        order.addItem("i1", 1.0, 1);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validator.validate(order));
        assert(ex.getMessage().contains("Invalid email"));
    }
}
