package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author oldca
 */
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
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

    public ExchangeRate addExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }
//PATCH /exchangeRate/USDRUB  Обновление существующего в базе обменного курса

    public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateRepository.update(exchangeRate);
    }
}
