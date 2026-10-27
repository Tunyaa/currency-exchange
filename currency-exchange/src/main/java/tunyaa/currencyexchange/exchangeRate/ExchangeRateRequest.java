package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;

/**
 *
 * @author oldca
 */
public record ExchangeRateRequest(String baseCode, String targetCode, BigDecimal rate) {

}
