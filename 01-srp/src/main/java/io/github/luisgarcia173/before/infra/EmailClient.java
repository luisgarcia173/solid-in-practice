package io.github.luisgarcia173.before.infra;

public class EmailClient {
    public void send(String to, String subject, String body) {
        // fake send - in the god class this will be instantiated directly
        System.out.println("[EmailClient] Sending to=" + to + " subject=" + subject);
    }
}
