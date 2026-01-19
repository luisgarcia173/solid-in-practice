package io.github.luisgarcia173.after.service;

import io.github.luisgarcia173.before.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceCalculatorTest {

    private final PriceCalculator calculator = new PriceCalculator();

    @Test
    void calculateSubtotal_singleItem() {
        Order order = new Order("o1", "a@b.com", null, false);
        order.addItem("item", 10.0, 2);

        double subtotal = calculator.calculateSubtotal(order);
        assertEquals(20.0, subtotal, 0.0001);
    }

    @Test
    void calculateSubtotal_multipleItems() {
        Order order = new Order("o2", "a@b.com", null, false);
        order.addItem("i1", 5.5, 2);
        order.addItem("i2", 2.25, 3);

        double subtotal = calculator.calculateSubtotal(order);
        assertEquals(17.75, subtotal, 0.0001);
    }

    @Test
    void calculateShipping_noCountry_belowFree() {
        Order order = new Order("o3", "a@b.com", null, false);
        double shipping = calculator.calculateShipping(order, 100.0);
        // default shipping rate is 0.10 -> 10.0
        assertEquals(10.0, shipping, 0.001);
    }

    @Test
    void calculateShipping_atFreeLimit_isZero() {
        Order order = new Order("o4", "a@b.com", null, false);
        double shipping = calculator.calculateShipping(order, 200.0);
        assertEquals(0.0, shipping, 0.0);
    }

    @Test
    void calculateShipping_aboveFreeLimit_isZero() {
        Order order = new Order("o5", "a@b.com", null, false);
        double shipping = calculator.calculateShipping(order, 250.0);
        assertEquals(0.0, shipping, 0.0);
    }

    @Test
    void calculateShipping_brSurcharge() {
        Order order = new Order("o6", "a@b.com", "BR", false);
        double shipping = calculator.calculateShipping(order, 100.0);
        // 100 * 0.10 + 10.0 = 20.0
        assertEquals(20.0, shipping, 0.001);
    }

    @Test
    void calculateShipping_roundingBehavior() {
        Order order = new Order("o7", "a@b.com", null, false);
        double shipping = calculator.calculateShipping(order, 33.333);
        // 33.333 * 0.10 = 3.3333 -> rounded to 3.33
        assertEquals(3.33, shipping, 0.001);
    }
}
