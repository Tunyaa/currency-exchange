package tunyaa.currencyexchange.currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author oldca
 */
public class InMemoryCurrencyRepository implements CurrencyRepository {

    private final Map<Long, Currency> storage = new HashMap<>();
    private long nextId = 1L;

    @Override
    public List<Currency> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        return storage.values().stream()
                .filter(c -> c.getCode().equals(code))
                .findFirst();
    }

    @Override
    public Currency save(String code, String fullName, String sign) {
        Long id = nextId++;
        Currency currency = new Currency(id, code, fullName, sign);
        storage.put(id, currency);
        return currency;
    }

}
