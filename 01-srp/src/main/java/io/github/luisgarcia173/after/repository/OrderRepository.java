package io.github.luisgarcia173.after.repository;

import io.github.luisgarcia173.before.Order;
import io.github.luisgarcia173.after.model.OrderTotals;

public interface OrderRepository {
    void save(Order order, OrderTotals totals);
}
