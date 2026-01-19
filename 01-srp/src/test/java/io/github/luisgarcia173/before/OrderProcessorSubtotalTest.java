package io.github.luisgarcia173.before;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderProcessorSubtotalTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void singleItem_subtotalCorrect() {
        Order order = new Order("o-s1", "c@example.com", "US", false);
        order.addItem("sku1", 10.0, 3); // 30.0

        double subtotal = processor.calculateSubtotal(order);
        assertThat(subtotal).isCloseTo(30.0, within(0.0001));
    }

    @Test
    void multipleItems_subtotalCorrect() {
        Order order = new Order("o-s2", "c@example.com", "US", false);
        order.addItem("A", 100.0, 1);
        order.addItem("B", 10.0, 3); // 100 + 30 = 130

        double subtotal = processor.calculateSubtotal(order);
        assertThat(subtotal).isCloseTo(130.0, within(0.0001));
    }
}
