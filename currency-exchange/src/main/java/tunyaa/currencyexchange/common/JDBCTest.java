package tunyaa.currencyexchange.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import tunyaa.currencyexchange.currency.Currency;
import tunyaa.currencyexchange.currency.CurrencyRepository;
import tunyaa.currencyexchange.currency.JdbcCurrencyRepository;

/**
 *
 * @author oldca
 */
public class JDBCTest {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:exchange.db"); Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT OR IGNORE INTO currencies (code, name, sign) VALUES ('USD', 'US Dollar', '$')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CurrencyRepository repo = new JdbcCurrencyRepository();
        List<Currency> all = repo.findAll();
        System.out.println("Count: " + all.size());
        for (Currency c : all) {
            System.out.println(c);
        }
    }
}
