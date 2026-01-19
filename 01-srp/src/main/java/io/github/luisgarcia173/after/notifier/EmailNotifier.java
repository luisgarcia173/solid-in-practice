package io.github.luisgarcia173.after.notifier;

import io.github.luisgarcia173.before.Order;
import io.github.luisgarcia173.after.model.OrderTotals;

public class EmailNotifier implements Notifier {
    @Override
    public void notify(Order order, OrderTotals totals) {
        System.out.println("[EmailNotifier] sending to=" + order.getCustomerEmail() + " total=" + totals.total());
    }
}
