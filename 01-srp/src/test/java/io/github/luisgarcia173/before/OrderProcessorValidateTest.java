package io.github.luisgarcia173.before;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderProcessorValidateTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void emptyItems_throwsIllegalArgumentException() {
        Order order = new Order("o-empty", "customer@example.com", "US", false);

        assertThatThrownBy(() -> processor.validate(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Order must contain at least one item");
    }

    @Test
    void itemQuantityLessOrEqualZero_throwsIllegalArgumentException() {
        Order order = new Order("o-zero", "customer@example.com", "US", false);
        order.addItem("sku-1", 10.0, 0);

        assertThatThrownBy(() -> processor.validate(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Item quantity must be > 0");
    }

    @Test
    void nullEmail_throwsIllegalArgumentException() {
        Order order = new Order("o-null", null, "US", false);
        order.addItem("sku-1", 10.0, 1);

        assertThatThrownBy(() -> processor.validate(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid customer email");
    }

    @Test
    void missingAtInEmail_throwsIllegalArgumentException() {
        Order order = new Order("o-bad-email", "no-at-symbol", "US", false);
        order.addItem("sku-1", 10.0, 1);

        assertThatThrownBy(() -> processor.validate(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid customer email");
    }

    @Test
    void validOrder_doesNotThrow() {
        Order order = new Order("o-valid", "customer@example.com", "US", false);
        order.addItem("sku-1", 15.0, 2);

        // Should not throw
        processor.validate(order);
    }
}
