package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;
import tunyaa.currencyexchange.currency.Currency;

/**
 *
 * @author oldca
 */
public final class ExchangeRate {

    private final Long id;
    private final Currency baseCurrency;
    private final Currency targetCurrency;
    private final BigDecimal rate;

    public ExchangeRate(Long id, Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" + "id=" + id + ", baseCurrency=" + baseCurrency + ", targetCurrency=" + targetCurrency + ", rate=" + rate + '}';
    }

}
