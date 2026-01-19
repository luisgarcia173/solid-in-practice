package io.github.luisgarcia173.before;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderProcessorDiscountTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void vipGets10Percent() {
        Order order = new Order("o-vip", "vip@example.com", "US", true);
        order.addItem("i", 200.0, 1);
        double subtotal = processor.calculateSubtotal(order);
        double after = processor.applyDiscount(order, subtotal);
        double discountAmount = Math.round((subtotal - after) * 100.0) / 100.0;
        assertThat(discountAmount).isEqualTo(20.00);
    }

    @Test
    void nonVip_over500_gets7Percent() {
        Order order = new Order("o-7", "n@example.com", "US", false);
        order.addItem("i", 600.0, 1);
        double subtotal = processor.calculateSubtotal(order);
        double after = processor.applyDiscount(order, subtotal);
        double discountAmount = Math.round((subtotal - after) * 100.0) / 100.0;
        assertThat(discountAmount).isEqualTo(42.00);
    }

    @Test
    void nonVip_500_orLess_gets5Percent() {
        Order order = new Order("o-5", "n@example.com", "US", false);
        order.addItem("i", 500.0, 1);
        double subtotal = processor.calculateSubtotal(order);
        double after = processor.applyDiscount(order, subtotal);
        double discountAmount = Math.round((subtotal - after) * 100.0) / 100.0;
        assertThat(discountAmount).isEqualTo(25.00);
    }

    @Test
    void brAddsFlat5() {
        Order order = new Order("o-br", "c@example.com", "BR", false);
        order.addItem("i", 100.0, 1);
        double subtotal = processor.calculateSubtotal(order);
        double after = processor.applyDiscount(order, subtotal);
        double discountAmount = Math.round((subtotal - after) * 100.0) / 100.0;
        assertThat(discountAmount).isEqualTo(10.00); // 5% of 100 = 5 + 5 = 10
    }

    @Test
    void borderSubtotal500_uses5Percent_not7() {
        Order order = new Order("o-border", "c@example.com", "US", false);
        order.addItem("i", 500.0, 1);
        double subtotal = processor.calculateSubtotal(order);
        double after = processor.applyDiscount(order, subtotal);
        double discountAmount = Math.round((subtotal - after) * 100.0) / 100.0;
        assertThat(discountAmount).isEqualTo(25.00);
    }

    @Test
    void roundingToTwoDecimalsVerified() {
        Order order = new Order("o-round", "c@example.com", "US", false);
        order.addItem("i", 333.333, 1);
        double subtotal = processor.calculateSubtotal(order);
        double after = processor.applyDiscount(order, subtotal);
        double discountAmount = Math.round((subtotal - after) * 100.0) / 100.0;
        assertThat(discountAmount).isEqualTo(16.67);
    }
}
