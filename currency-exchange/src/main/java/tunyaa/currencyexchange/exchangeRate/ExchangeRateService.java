package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;
import java.util.List;
import tunyaa.currencyexchange.common.exception.CurrencyNotFoundException;
import tunyaa.currencyexchange.currency.Currency;
import tunyaa.currencyexchange.currency.CurrencyRepository;

/**
 *
 * @author oldca
 */
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository, CurrencyRepository currencyRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;
    }

//    GET /exchangeRates  Получение списка всех обменных курсов
    public List<ExchangeRate> getExchangeRates() {
        return exchangeRateRepository.findAll();
    }

//GET /exchangeRate/USD&RUB  Получение конкретного обменного курса
    public ExchangeRate getExchangeRate(String baseCode, String targetCode) {
        return exchangeRateRepository.findByPair(baseCode, targetCode)
                .orElseThrow(() -> new RuntimeException("ExchangeRate not found! " + baseCode + " - " + targetCode + "."));
    }

//POST /exchangeRates  Добавление нового обменного курса в базу
    public ExchangeRate addExchangeRate(String baseCode, String targetCode, BigDecimal rate) {
        Currency baseCurrency = currencyRepository.findByCode(baseCode).orElseThrow(() -> new CurrencyNotFoundException(baseCode));
        Currency targetCurrency = currencyRepository.findByCode(targetCode).orElseThrow(() -> new CurrencyNotFoundException(targetCode));
        ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, rate);
        return exchangeRateRepository.save(exchangeRate);
    }

//PATCH /exchangeRate/USDRUB  Обновление существующего в базе обменного курса
    public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate) {// TODO сделать DTO
        ExchangeRate existing = getExchangeRate(
                exchangeRate.getBaseCurrency().getCode(),
                exchangeRate.getTargetCurrency().getCode());

        ExchangeRate updated = new ExchangeRate(
                existing.getId(),
                existing.getBaseCurrency(),
                existing.getTargetCurrency(),
                exchangeRate.getRate());

        return exchangeRateRepository.update(updated);
    }
}
