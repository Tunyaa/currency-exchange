package tunyaa.currencyexchange.common;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String url = "jdbc:sqlite:exchange.db";
        sce.getServletContext().log("DB init START");

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver not found on classpath", e);
        }

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS currencies (
                id      INTEGER PRIMARY KEY AUTOINCREMENT,
                code    TEXT NOT NULL UNIQUE,
                name    TEXT NOT NULL,
                sign    TEXT NOT NULL
            )
        """);
            sce.getServletContext().log("DB init: currencies created");

            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS exchange_rates (
                id                 INTEGER PRIMARY KEY AUTOINCREMENT,
                base_currency_id   INTEGER NOT NULL,
                target_currency_id INTEGER NOT NULL,
                rate               DECIMAL(10,6) NOT NULL,
                FOREIGN KEY (base_currency_id)   REFERENCES currencies(id),
                FOREIGN KEY (target_currency_id) REFERENCES currencies(id),
                UNIQUE (base_currency_id, target_currency_id)
            )
        """);
            sce.getServletContext().log("DB init: exchange_rates created");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }

}
