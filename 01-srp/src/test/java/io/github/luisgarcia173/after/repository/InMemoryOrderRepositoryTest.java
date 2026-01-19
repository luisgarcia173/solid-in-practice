package io.github.luisgarcia173.after.repository;

import io.github.luisgarcia173.before.Order;
import io.github.luisgarcia173.after.model.OrderTotals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InMemoryOrderRepositoryTest {

    @Test
    void saveAndFind_returnsSavedTotals() {
        InMemoryOrderRepository repo = new InMemoryOrderRepository();
        Order order = new Order("o1", "a@b.com", "US", false);
        OrderTotals totals = new OrderTotals(10.0, 1.0, 2.0, 11.0);

        assertNull(repo.find(order.getId()));
        repo.save(order, totals);
        OrderTotals found = repo.find(order.getId());
        assertEquals(totals, found);
    }

    @Test
    void save_overwritesPrevious() {
        InMemoryOrderRepository repo = new InMemoryOrderRepository();
        Order order = new Order("o2", "a@b.com", "US", false);
        OrderTotals t1 = new OrderTotals(10.0, 1.0, 2.0, 11.0);
        OrderTotals t2 = new OrderTotals(20.0, 2.0, 3.0, 21.0);

        repo.save(order, t1);
        repo.save(order, t2);

        assertEquals(t2, repo.find(order.getId()));
    }
}
