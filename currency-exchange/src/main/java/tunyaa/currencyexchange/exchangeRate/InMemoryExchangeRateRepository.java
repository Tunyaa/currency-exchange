package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import tunyaa.currencyexchange.currency.Currency;

/**
 *
 * @author oldca
 */
public class InMemoryExchangeRateRepository implements ExchangeRateRepository {

    private final Map<Long, ExchangeRate> storage = new HashMap<>();
    private long nextId = 1L;

    @Override
    public List<ExchangeRate> findAll() {
        return new ArrayList(storage.values());
    }

    @Override
    public Optional<ExchangeRate> findByPair(String baseCode, String targetCode) {
        return storage.values().stream()
                .filter(e -> e.getBaseCurrency().equals(baseCode) && e.getTargetCurrency().equals(targetCode))
                .findFirst();
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {//TODO поменять аргумент на DTO?
        Long id = nextId++;
        ExchangeRate exchangeRate1 = new ExchangeRate(id, exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency(), exchangeRate.getRate());
        
        storage.put(id, exchangeRate1);
        return exchangeRate1;//TODO испранить название 
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) {//TODO поменять на DTO 
        Long id = exchangeRate.getId();
        ExchangeRate get = storage.get(id);
        ExchangeRate exchangeRate1 = 
                new ExchangeRate(id, exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency(), exchangeRate.getRate());
        storage.put(id, exchangeRate1);
        return exchangeRate1;
    }

}
