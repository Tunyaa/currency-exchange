package tunyaa.currencyexchange.currency;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author oldca
 */
public interface CurrencyRepository {

    public List<Currency> findAll();

    public Optional<Currency> findByCode(String code);

    public Currency save(String code, String fullName, String sign);
    
}
