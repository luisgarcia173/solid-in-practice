package io.github.luisgarcia173.after.service;

import io.github.luisgarcia173.after.model.OrderTotals;
import io.github.luisgarcia173.after.notifier.Notifier;
import io.github.luisgarcia173.after.policy.SeasonalDiscount;
import io.github.luisgarcia173.after.repository.OrderRepository;
import io.github.luisgarcia173.before.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    static class RecordingRepository implements OrderRepository {
        Order savedOrder;
        OrderTotals savedTotals;

        @Override
        public void save(Order order, OrderTotals totals) {
            this.savedOrder = order;
            this.savedTotals = totals;
        }
    }

    static class RecordingNotifier implements Notifier {
        Order notifiedOrder;
        OrderTotals notifiedTotals;

        @Override
        public void notify(Order order, OrderTotals totals) {
            this.notifiedOrder = order;
            this.notifiedTotals = totals;
        }
    }

    @Test
    void process_happyPath_savesAndNotifies_andReturnsTotals() {
        OrderValidator validator = new OrderValidator();
        PriceCalculator calculator = new PriceCalculator();
        SeasonalDiscount discount = new SeasonalDiscount();
        RecordingRepository repo = new RecordingRepository();
        RecordingNotifier notifier = new RecordingNotifier();

        OrderService service = new OrderService(validator, calculator, discount, repo, notifier);

        Order order = new Order("o1", "user@example.com", "BR", true);
        order.addItem("i1", 50.0, 2); // subtotal 100

        OrderTotals totals = service.process(order);

        assertNotNull(totals);
        assertEquals(100.0, totals.subtotal(), 0.001);
        // seasonal discount vip 12% -> 12 + 5 => 17 -> after = 83
        assertEquals(17.0, totals.discount(), 0.001);
        // shipping on afterDiscount=83 -> 83*0.1 + 10 = 8.3 + 10 = 18.3 -> rounded 18.3
        assertEquals(18.3, totals.shipping(), 0.001);
        assertEquals(101.3, totals.total(), 0.001);

        // repository saved
        assertEquals(order, repo.savedOrder);
        assertEquals(totals, repo.savedTotals);

        // notifier called
        assertEquals(order, notifier.notifiedOrder);
        assertEquals(totals, notifier.notifiedTotals);
    }

    @Test
    void process_validationFails_noSaveNoNotify_andThrows() {
        OrderValidator validator = new OrderValidator();
        PriceCalculator calculator = new PriceCalculator();
        SeasonalDiscount discount = new SeasonalDiscount();
        RecordingRepository repo = new RecordingRepository();
        RecordingNotifier notifier = new RecordingNotifier();

        OrderService service = new OrderService(validator, calculator, discount, repo, notifier);

        Order order = new Order("o2", "invalid-email", "US", false);
        // no items -> validation should fail

        assertThrows(IllegalArgumentException.class, () -> service.process(order));

        assertNull(repo.savedOrder);
        assertNull(notifier.notifiedOrder);
    }
}
