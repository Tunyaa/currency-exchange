package tunyaa.currencyexchange.exchangerate;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author oldca
 */
public class InMemoryExchangeRateRepository implements ExchangeRateRepository {

    @Override
    public List<ExchangeRate> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<ExchangeRate> findByPair(String baseCode, String targetCode) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
