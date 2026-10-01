package tunyaa.currencyexchange.exchangerate;

import java.math.BigDecimal;

/**
 *
 * @author oldca
 */
public final class ExchangeRate {

    private final Long id;
    private final Long baseCurrencyId;
    private final Long targetCurrencyId;
    private final BigDecimal rate;

    public ExchangeRate(Long id, Long baseCurrencyId, Long targetCurrencyId, BigDecimal rate) {
        this.id = id;
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public Long getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public Long getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" + "id=" + id + ", baseCurrencyId=" + baseCurrencyId + ", targetCurrencyId=" + targetCurrencyId + ", rate=" + rate + '}';
    }

}
