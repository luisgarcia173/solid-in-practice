package io.github.luisgarcia173.after.notifier;

import io.github.luisgarcia173.before.Order;
import io.github.luisgarcia173.after.model.OrderTotals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailNotifierTest {

    @Test
    void notify_printsExpectedMessage() {
        EmailNotifier notifier = new EmailNotifier();
        Order order = new Order("o1", "user@example.com", "US", false);
        OrderTotals totals = new OrderTotals(10.0, 1.0, 2.0, 11.0);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            notifier.notify(order, totals);
        } finally {
            System.setOut(originalOut);
        }

        String printed = out.toString();
        assertTrue(printed.contains("sending to=user@example.com"));
        assertTrue(printed.contains("total=11.0"));
    }
}
