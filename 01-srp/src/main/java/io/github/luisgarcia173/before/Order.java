package io.github.luisgarcia173.before;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private final String id;
    private final List<OrderItem> items = new ArrayList<>();
    private final String customerEmail;
    private final String country;
    private final boolean vip;

    public Order(String id, String customerEmail, String country, boolean vip) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.country = country;
        this.vip = vip;
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCountry() {
        return country;
    }

    public boolean isVip() {
        return vip;
    }

    public void addItem(String name, double unitPrice, int quantity) {
        items.add(new OrderItem(name, unitPrice, quantity));
    }

    @Override
    public String toString() {
        return "Order{id='" + id + "', items=" + items + ", customerEmail='" + customerEmail + "', country='" + country + "', vip=" + vip + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
