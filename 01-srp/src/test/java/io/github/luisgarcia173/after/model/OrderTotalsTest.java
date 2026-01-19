package io.github.luisgarcia173.after.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTotalsTest {

    @Test
    void toString_containsFields() {
        OrderTotals t = new OrderTotals(10.0, 1.0, 2.0, 11.0);
        assertEquals("OrderTotals{subtotal=10.0, discount=1.0, shipping=2.0, total=11.0}", t.toString());
    }
}
