package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import tunyaa.currencyexchange.common.exception.DuplicateException;
import tunyaa.currencyexchange.currency.Currency;

/**
 *
 * @author oldca
 */
public class JdbcExchangeRateRepository implements ExchangeRateRepository {

    private static final String URL = "jdbc:sqlite:exchange.db";

    @Override
    public List<ExchangeRate> findAll() {
        String sql = """
                     SELECT 
                                 er.id AS rate_id,
                                 er.rate AS rate,
                                 bc.id AS base_id,
                                 bc.name AS base_name,
                                 bc.code AS base_code,
                                 bc.sign AS base_sign,
                                 tc.id AS target_id,
                                 tc.name AS target_name,
                                 tc.code AS target_code,
                                 tc.sign AS target_sign
                             FROM exchange_rates er
                             JOIN currencies bc ON er.base_currency_id = bc.id
                             JOIN currencies tc ON er.target_currency_id = tc.id
                             ORDER BY er.id
                             """;

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            List<ExchangeRate> allExchangeRates = new ArrayList<>();

            while (resultSet.next()) {
                allExchangeRates.add(mapRow(resultSet));
            }
            return allExchangeRates;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find exchange rates", e);
        }
    }

    @Override
    public Optional<ExchangeRate> findByPair(String baseCode, String targetCode) {
        String sql = """
                     SELECT 
                                 er.id AS rate_id,
                                 er.rate AS rate,
                                 bc.id AS base_id,
                                 bc.name AS base_name,
                                 bc.code AS base_code,
                                 bc.sign AS base_sign,
                                 tc.id AS target_id,
                                 tc.name AS target_name,
                                 tc.code AS target_code,
                                 tc.sign AS target_sign
                             FROM exchange_rates er
                             JOIN currencies bc ON er.base_currency_id = bc.id
                             JOIN currencies tc ON er.target_currency_id = tc.id
                             WHERE bc.code = ? AND tc.code = ?
                             """;

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, baseCode);
            statement.setString(2, targetCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find rate: " + baseCode + " -> " + targetCode, e);
        }
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        String sql = "INSERT INTO exchange_rates (base_currency_id, target_currency_id, rate) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, exchangeRate.getBaseCurrency().getId());
            statement.setLong(2, exchangeRate.getTargetCurrency().getId());
            statement.setBigDecimal(3, exchangeRate.getRate());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    return new ExchangeRate(
                            id,
                            exchangeRate.getBaseCurrency(),
                            exchangeRate.getTargetCurrency(),
                            exchangeRate.getRate()
                    );
                }
                throw new RuntimeException("No ID returned after INSERT");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint")) {
                throw new DuplicateException("Exchange rate already exists: "
                        + exchangeRate.getBaseCurrency().getCode()
                        + " -> "
                        + exchangeRate.getTargetCurrency().getCode());
            }
            throw new RuntimeException("Failed to save exchange rate", e);
        }
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) {
        String sql = "UPDATE exchange_rates SET rate = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, exchangeRate.getRate());
            statement.setLong(2, exchangeRate.getId());

            int updated = statement.executeUpdate();

            if (updated == 0) {
                throw new RuntimeException("Exchange rate with id " + exchangeRate.getId() + " not found");
            }
            return exchangeRate;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update exchange rate: " + exchangeRate.getId(), e);
        }
    }

    private ExchangeRate mapRow(ResultSet rs) throws SQLException {
        Currency base = new Currency(
                rs.getLong("base_id"),
                rs.getString("base_name"),
                rs.getString("base_code"),
                rs.getString("base_sign")
        );
        Currency target = new Currency(
                rs.getLong("target_id"),
                rs.getString("target_name"),
                rs.getString("target_code"),
                rs.getString("target_sign")
        );
        return new ExchangeRate(
                rs.getLong("rate_id"),
                base,
                target,
                rs.getBigDecimal("rate")
        );
    }

}
