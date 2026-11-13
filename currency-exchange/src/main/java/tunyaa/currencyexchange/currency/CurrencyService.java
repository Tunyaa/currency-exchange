package tunyaa.currencyexchange.currency;

import java.util.List;
import tunyaa.currencyexchange.common.exception.CurrencyNotFoundException;

public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

//GET /currencies Получение списка валют
    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }
//GET /currency/EUR Получение конкретной валюты

    public Currency getCurrency(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new CurrencyNotFoundException(code));
    }
//POST /currencies  Добавление новой валюты в базу

    public Currency addCurrency(String name, String code, String sign) {
        if (checkCurrencyField(name, code, sign)) {
            return currencyRepository.save(name, code, sign);
        }
        throw new IllegalArgumentException("Currency fields cannot be empty");// TODO проверить
    }

    private boolean checkCurrencyField(String... str) {
        for (String string : str) {
            if (string == null || string.isBlank()) {
                return false;
            }
        }
        return true;
    }
}
