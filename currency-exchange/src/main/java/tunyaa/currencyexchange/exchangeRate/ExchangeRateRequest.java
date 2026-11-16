package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;

/**
 *
 * @author oldca
 */
public record ExchangeRateRequest(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {

}
