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

/**
 *
 * @author oldca
 */
public class JdbcCurrencyRepository implements CurrencyRepository {

    private static final String URL = "jdbc:sqlite:exchange.db";

    @Override
    public List<Currency> findAll() {
        List<Currency> result = new ArrayList<>();
        String sql = "SELECT id, code, name, sign FROM currencies";
//         String sql = "SELECT * FROM currencies";
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Currency save(String name, String code, String sign) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
