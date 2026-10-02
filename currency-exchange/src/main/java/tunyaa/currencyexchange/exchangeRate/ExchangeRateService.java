package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;
import java.util.List;
import tunyaa.currencyexchange.currency.CurrencyRepository;

/**
 *
 * @author oldca
 */
public class ExchangeRateService {
    
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;
    
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository,
            CurrencyRepository currencyRepository) {
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
                .orElseThrow(() -> new RuntimeException("ExchangeRate not found!"));
    }
//POST /exchangeRates  Добавление нового обменного курса в базу

    public ExchangeRate addExchangeRate(String baseCode, String targetCode, BigDecimal rate) {
        return exchangeRateRepository.save(baseCode, targetCode, rate);
    }
//PATCH /exchangeRate/USDRUB  Обновление существующего в базе обменного курса

    public ExchangeRate updateExchangeRate(String baseCode, String targetCode, BigDecimal rate) {
        return exchangeRateRepository.update(baseCode, targetCode, rate);
    }
}
