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

    //GET /currency/EUR Получение валюты
    public Currency getCurrency(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new CurrencyNotFoundException(code));
    }

    //POST /currencies  Добавление новой валюты
    public Currency addCurrency(String name, String code, String sign) {
        if (checkCurrencyField(name, code, sign)) {
            return currencyRepository.save(name, code, sign);
        }
        throw new IllegalArgumentException("Currency fields cannot be empty. Code must contains 3 characters");
    }

    private boolean checkCurrencyField(String name, String code, String sign) {
        return name != null && !name.isBlank()
                && code != null && code.length() == 3
                && sign != null && !sign.isBlank();
    }
}
