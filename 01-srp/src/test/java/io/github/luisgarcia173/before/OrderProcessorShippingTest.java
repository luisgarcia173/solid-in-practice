package io.github.luisgarcia173.before;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderProcessorShippingTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void subtotalAtLeast200_freeShipping() {
        Order order = new Order("o-free", "c@example.com", "US", false);
        order.addItem("big", 200.0, 1);
        double ship = processor.calculateShipping(order);
        assertThat(ship).isCloseTo(0.0, within(0.0001));
    }

    @Test
    void subtotalUnder200_10Percent() {
        Order order = new Order("o-ship", "c@example.com", "US", false);
        order.addItem("x", 50.0, 2); // subtotal 100
        double ship = processor.calculateShipping(order);
        assertThat(ship).isEqualTo(10.0);
    }

    @Test
    void brUnder200_adds10Surcharge() {
        Order order = new Order("o-br-ship", "c@example.com", "BR", false);
        order.addItem("x", 50.0, 2); // subtotal 100
        double ship = processor.calculateShipping(order);
        assertThat(ship).isEqualTo(20.0);
    }

    @Test
    void subtotalExactly200_freeShippingBorder() {
        Order order = new Order("o-border-ship", "c@example.com", "US", false);
        order.addItem("x", 100.0, 2); // subtotal 200
        double ship = processor.calculateShipping(order);
        assertThat(ship).isCloseTo(0.0, within(0.0001));
    }
}
