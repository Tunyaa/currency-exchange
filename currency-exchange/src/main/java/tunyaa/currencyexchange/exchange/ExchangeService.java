package tunyaa.currencyexchange.exchange;

import java.math.BigDecimal;
import java.util.List;
import tunyaa.currencyexchange.common.exception.CurrencyNotFoundException;
import tunyaa.currencyexchange.currency.Currency;
import tunyaa.currencyexchange.currency.CurrencyRepository;
import tunyaa.currencyexchange.exchangerate.ExchangeRate;
import tunyaa.currencyexchange.exchangerate.ExchangeRateRepository;
import tunyaa.currencyexchange.exchange.findcoefficient.FindCoefficient;

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
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount cannot be 0 or negative.");
        }
        if (from.equals(to)) {
            throw new IllegalArgumentException("Cannot exchange currency to itself");
        }

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
