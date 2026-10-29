package tunyaa.currencyexchange.exchange;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import tunyaa.currencyexchange.common.AppContext;
import tunyaa.currencyexchange.common.exception.CurrencyNotFoundException;
import tunyaa.currencyexchange.currency.Currency;
import tunyaa.currencyexchange.currency.CurrencyRepository;
import tunyaa.currencyexchange.exchangerate.ExchangeRate;
import tunyaa.currencyexchange.exchangerate.ExchangeRateRepository;
import tunyaa.currencyexchange.findcoefficient.FindCoefficient;

/**
 *
 * @author oldca
 */
public class ExchangeService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;
    private final FindCoefficient findCoefficient;
    private final Exchange exchange;

    public ExchangeService(ExchangeRateRepository exchangeRateRepository, CurrencyRepository currencyRepository, FindCoefficient findCoefficient, Exchange exchange) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;
        this.findCoefficient = findCoefficient;
        this.exchange = exchange;
    }

    public ExchangeResponse exchange(BigDecimal amount, String from, String to) {
        List<ExchangeRate> rates = exchangeRateRepository.findAll();
        BigDecimal coef = findCoefficient.find(rates, from, to);
        BigDecimal convertedAmount = exchange.calculate(amount, coef);

        Currency baseCurrency = currencyRepository.findByCode(from)
                .orElseThrow(() -> new CurrencyNotFoundException(from));
        Currency targetCurrency = currencyRepository.findByCode(to)
                .orElseThrow(() -> new CurrencyNotFoundException(to));

        return new ExchangeResponse(baseCurrency, targetCurrency, coef, amount, convertedAmount);
    }
}
