package io.github.luisgarcia173.before.infra;

public class JdbcConnection {
    private final String url;

    public JdbcConnection(String url) {
        this.url = url;
    }

    public void execute(String sql) {
        // fake execute
        System.out.println("[JdbcConnection] Executing: " + sql);
    }
}
