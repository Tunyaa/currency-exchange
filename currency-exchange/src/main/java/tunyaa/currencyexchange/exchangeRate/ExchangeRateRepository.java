package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author oldca
 */
public interface ExchangeRateRepository {

    public List<ExchangeRate> findAll();

    public Optional<ExchangeRate> findByPair(String baseCode, String targetCode);

    public ExchangeRate save(ExchangeRate exchangeRate);

    public ExchangeRate update(ExchangeRate exchangeRate);

}
