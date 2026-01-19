package io.github.luisgarcia173.after.policy;

import io.github.luisgarcia173.before.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeasonalDiscountTest {

    private final SeasonalDiscount discount = new SeasonalDiscount();

    @Test
    void apply_vip_applies12Percent_and_brSurcharge() {
        Order order = new Order("o1", "a@b.com", "BR", true);
        double after = discount.apply(order, 100.0);
        // vip -> 12% = 12.0, br surcharge +5 => discount total 17.0 -> after = 83.0
        assertEquals(83.0, after, 0.001);
    }

    @Test
    void apply_highSubtotal_8percent() {
        Order order = new Order("o2", "a@b.com", "US", false);
        double after = discount.apply(order, 600.0);
        // 8% of 600 = 48 -> after = 552
        assertEquals(552.0, after, 0.001);
    }

    @Test
    void apply_lowSubtotal_3percent() {
        Order order = new Order("o3", "a@b.com", "US", false);
        double after = discount.apply(order, 100.0);
        assertEquals(97.0, after, 0.001);
    }

    @Test
    void apply_zeroSubtotal_returnsZero() {
        Order order = new Order("o4", "a@b.com", "US", false);
        double after = discount.apply(order, 0.0);
        assertEquals(0.0, after, 0.0);
    }

    @Test
    void apply_countryCaseInsensitive() {
        Order order = new Order("o5", "a@b.com", "br", false);
        double after = discount.apply(order, 100.0);
        // br surcharge applied even in lower-case
        assertEquals(92.0, after, 0.001); // 3% of 100 = 3 + 5 => discount 8 -> after 92
    }
}
