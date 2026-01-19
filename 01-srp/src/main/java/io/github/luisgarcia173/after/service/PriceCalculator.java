package io.github.luisgarcia173.after.service;

import io.github.luisgarcia173.before.Order;
import io.github.luisgarcia173.before.OrderItem;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PriceCalculator {
    private static final double SHIPPING_RATE;
    private static final double FREE_SHIPPING_LIMIT;
    private static final String CONFIG_FILE = "/srp.properties";

    // country surcharges for shipping (e.g. country.surcharge.BR=10.0)
    private static final Map<String, Double> COUNTRY_SURCHARGES = new HashMap<>();

    static {
        double shipRate = 0.10;
        double freeLimit = 200.0;
        Properties props = new Properties();
        try (InputStream in = PriceCalculator.class.getResourceAsStream(CONFIG_FILE)) {
            if (in != null) {
                props.load(in);
                String sr = props.getProperty("shipping.rate");
                if (sr != null && !sr.isBlank()) shipRate = Double.parseDouble(sr);
                String fl = props.getProperty("free.shipping.limit");
                if (fl != null && !fl.isBlank()) freeLimit = Double.parseDouble(fl);

                // load country.surcharge.<COUNTRY> entries
                for (String name : props.stringPropertyNames()) {
                    if (name.startsWith("country.surcharge.")) {
                        String country = name.substring("country.surcharge.".length()).toUpperCase();
                        String v = props.getProperty(name);
                        try {
                            COUNTRY_SURCHARGES.put(country, Double.parseDouble(v));
                        } catch (NumberFormatException e) {
                            System.err.println("[PriceCalculator] invalid number for " + name + ": " + v);
                        }
                    }
                }

            } else {
                System.err.println("[PriceCalculator] Config " + CONFIG_FILE + " not found on classpath, using defaults");
            }
        } catch (Exception e) {
            System.err.println("[PriceCalculator] Failed loading config " + CONFIG_FILE + ": " + e.getMessage());
        }
        SHIPPING_RATE = shipRate;
        FREE_SHIPPING_LIMIT = freeLimit;

        // ensure BR default exists if not configured
        COUNTRY_SURCHARGES.putIfAbsent("BR", 10.0);
    }

    public double calculateSubtotal(Order order) {
        double subtotal = 0.0;
        for (OrderItem item : order.getItems()) {
            subtotal += item.unitPrice() * item.quantity();
        }
        return subtotal;
    }

    public double calculateShipping(Order order, double subtotal) {
        if (subtotal >= FREE_SHIPPING_LIMIT) return 0.0;
        double ship = subtotal * SHIPPING_RATE;
        if (order.getCountry() != null) {
            Double surcharge = COUNTRY_SURCHARGES.get(order.getCountry().toUpperCase());
            if (surcharge != null && subtotal < FREE_SHIPPING_LIMIT) ship += surcharge;
        }
        return Math.round(ship * 100.0) / 100.0;
    }

}
