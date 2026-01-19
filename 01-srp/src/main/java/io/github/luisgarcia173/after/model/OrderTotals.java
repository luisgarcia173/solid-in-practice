package io.github.luisgarcia173.after.model;

public record OrderTotals(double subtotal, double discount, double shipping, double total) {

    @Override
    public String toString() {
        return "OrderTotals{subtotal=" + subtotal + ", discount=" + discount + ", shipping=" + shipping + ", total=" + total + '}';
    }
}
