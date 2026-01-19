package io.github.luisgarcia173.after.repository;

import io.github.luisgarcia173.before.Order;
import io.github.luisgarcia173.after.model.OrderTotals;

import java.util.HashMap;
import java.util.Map;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, OrderTotals> store = new HashMap<>();

    @Override
    public void save(Order order, OrderTotals totals) {
        store.put(order.getId(), totals);
        System.out.println("[InMemoryOrderRepository] saved order=" + order.getId());
    }

    public OrderTotals find(String orderId) {
        return store.get(orderId);
    }
}
