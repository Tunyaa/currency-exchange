package tunyaa.currencyexchange.exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author oldca
 */
public class Exchange {

    public BigDecimal calculate(BigDecimal amount, BigDecimal coefficient) {
        return amount.multiply(coefficient).setScale(6, RoundingMode.HALF_UP);
    }
}
