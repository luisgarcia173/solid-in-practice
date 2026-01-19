package io.github.luisgarcia173.before;

import java.util.Objects;

public record OrderItem(String name, double unitPrice, int quantity) {

    @Override
    public String toString() {
        return "OrderItem{name='" + name + "', unitPrice=" + unitPrice + ", quantity=" + quantity + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Double.compare(orderItem.unitPrice, unitPrice) == 0 && quantity == orderItem.quantity && Objects.equals(name, orderItem.name);
    }

}
