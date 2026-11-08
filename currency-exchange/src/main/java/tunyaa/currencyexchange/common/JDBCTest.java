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

        CurrencyRepository repo = new JdbcCurrencyRepository();
//
//        Currency created = repo.save("Euro", "EUR", "€");
//        System.out.println("Created: " + created);
//
//        Currency created2 = repo.save("British Pound", "GBP", "£");
//        System.out.println("Created: " + created2);

        System.out.println("EUR: " + repo.findByCode("EUR"));
        System.out.println("GBP: " + repo.findByCode("GBP"));
        System.out.println("USD: " + repo.findByCode("USD"));

        
        try {
    repo.save("US Dollar Again", "USD", "$");
    System.out.println("BUG: duplicate allowed!");
} catch (RuntimeException e) {
    System.out.println("OK: duplicate rejected: " + e.getCause().getMessage());
}
    }
}
