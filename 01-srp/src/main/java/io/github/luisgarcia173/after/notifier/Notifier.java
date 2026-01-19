package io.github.luisgarcia173.after.notifier;

import io.github.luisgarcia173.before.Order;
import io.github.luisgarcia173.after.model.OrderTotals;

public interface Notifier {
    void notify(Order order, OrderTotals totals);
}
