package tunyaa.currencyexchange.common;

import tunyaa.currencyexchange.currency.CurrencyRepository;
import tunyaa.currencyexchange.currency.CurrencyService;
import tunyaa.currencyexchange.currency.InMemoryCurrencyRepository;
import tunyaa.currencyexchange.currency.JdbcCurrencyRepository;
import tunyaa.currencyexchange.exchange.Exchange;
import tunyaa.currencyexchange.exchange.ExchangeService;
import tunyaa.currencyexchange.exchangerate.ExchangeRateRepository;
import tunyaa.currencyexchange.exchangerate.ExchangeRateService;
import tunyaa.currencyexchange.exchangerate.InMemoryExchangeRateRepository;
import tunyaa.currencyexchange.exchangerate.JdbcExchangeRateRepository;
import tunyaa.currencyexchange.exchange.findcoefficient.FindCoefficient;

/**
 *
 * @author oldca
 */
public class AppContext {

    public static final CurrencyRepository CURRENCY_REPOSITORY
            //            = new InMemoryCurrencyRepository();
            = new JdbcCurrencyRepository();
    public static final ExchangeRateRepository EXCHANGE_RATE_REPOSITORY
            //            = new InMemoryExchangeRateRepository();// TODO убрать поле?
            = new JdbcExchangeRateRepository();

    public static final CurrencyService CURRENCY_SERVICE
            = new CurrencyService(CURRENCY_REPOSITORY);
    public static final ExchangeRateService EXCHANGE_RATE_SERVICE
            = new ExchangeRateService(EXCHANGE_RATE_REPOSITORY, CURRENCY_REPOSITORY);
    public static final ExchangeService EXCHANGE_SERVICE
            = new ExchangeService(EXCHANGE_RATE_REPOSITORY, CURRENCY_REPOSITORY, new FindCoefficient(), new Exchange());
}
