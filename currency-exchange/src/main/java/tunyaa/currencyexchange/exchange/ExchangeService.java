package tunyaa.currencyexchange.exchange;

import java.math.BigDecimal;
import java.util.List;
import tunyaa.currencyexchange.exchangerate.ExchangeRate;
import tunyaa.currencyexchange.exchangerate.ExchangeRateRepository;
import tunyaa.currencyexchange.findcoefficient.FindCoefficient;

/**
 *
 * @author oldca
 */
public class ExchangeService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final FindCoefficient findCoefficient;
    private final Exchange exchange;

    public ExchangeService(ExchangeRateRepository exchangeRateRepository, FindCoefficient findCoefficient, Exchange exchange) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.findCoefficient = findCoefficient;
        this.exchange = exchange;
    }

    public BigDecimal exchange(BigDecimal amount, String from, String to) {
        List<ExchangeRate> rates = exchangeRateRepository.findAll();
        BigDecimal coef = findCoefficient.find(rates, from, to);
        return exchange.calculate(amount, coef);
    }
}
