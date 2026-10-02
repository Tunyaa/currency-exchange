package tunyaa.currencyexchange.currency;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author oldca
 */
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

//    GET /currencies Получение списка валют
    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }
//GET /currency/EUR Получение конкретной валюты

    public Currency getCurrency(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Currency not found: " + code));
    }
//POST /currencies  Добавление новой валюты в базу

    public Currency addCurrency(String code, String fullName, String sign) {
        return currencyRepository.save(code, fullName, sign);
    }
}
