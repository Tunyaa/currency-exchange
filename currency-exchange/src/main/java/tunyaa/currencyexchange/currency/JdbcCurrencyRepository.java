package tunyaa.currencyexchange.currency;

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

public class JdbcCurrencyRepository implements CurrencyRepository {

    private static final String URL = "jdbc:sqlite:exchange.db";

    @Override
    public List<Currency> findAll() {
        List<Currency> result = new ArrayList<>();
        String sql = "SELECT id, code, name, sign FROM currencies";

        try (Connection conn = DriverManager.getConnection(URL); PreparedStatement statement = conn.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                result.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load currencies", e);
        }
        return result;
    }

    private Currency mapRow(ResultSet rs) throws SQLException {
        return new Currency(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("code"),
                rs.getString("sign")
        );
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        String sql = "SELECT id, code, name, sign FROM currencies WHERE code = ?";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find currency by code: " + code, e);
        }
    }

    @Override
    public Currency save(String name, String code, String sign) {
        String sql = "INSERT INTO currencies (name, code, sign) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, name);
            statement.setString(2, code);
            statement.setString(3, sign);
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    return new Currency(id, name, code, sign);
                }
                throw new RuntimeException("No ID returned after INSERT");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint")) {
                throw new DuplicateException("Currency already exists: " + code);
            }
            throw new RuntimeException("Failed to save currency: " + code, e);
        }
    }

}
