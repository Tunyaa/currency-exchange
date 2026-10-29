package tunyaa.currencyexchange.exchange;

import java.math.BigDecimal;
import tunyaa.currencyexchange.currency.Currency;

/**
 *
 * @author oldca
 */
public record ExchangeResponse(
        Currency baseCurrency,
        Currency targetCurrency,
        BigDecimal rate,
        BigDecimal amount,
        BigDecimal convertedAmount
        ) {

}
