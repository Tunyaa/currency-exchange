package tunyaa.currencyexchange.exchange.findcoefficient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import tunyaa.currencyexchange.common.exception.RateNotFoundException;
import tunyaa.currencyexchange.exchangerate.ExchangeRate;

/**
 *
 * @author oldca
 */
public class FindCoefficient {

    public BigDecimal find(List<ExchangeRate> rates, String from, String to) {
        BigDecimal direct = findDirect(rates, from, to);
        if (direct != null) {
            return direct;
        }

        BigDecimal reverse = findReverse(rates, from, to);
        if (reverse != null) {
            return reverse;
        }

        BigDecimal transit = findTransit(rates, from, to);
        if (transit != null) {
            return transit;
        }

        throw new RateNotFoundException(from, to);
    }

    private BigDecimal findDirect(List<ExchangeRate> rates, String from, String to) {
        for (ExchangeRate exchangeRate : rates) {
            if (exchangeRate.getBaseCurrency().getCode().equals(from) && exchangeRate.getTargetCurrency().getCode().equals(to)) {
                return exchangeRate.getRate();
            }
        }
        return null;
    }

    private BigDecimal findReverse(List<ExchangeRate> rates, String from, String to) {
        for (ExchangeRate exchangeRate : rates) {
            if (exchangeRate.getTargetCurrency().getCode().equals(from) && exchangeRate.getBaseCurrency().getCode().equals(to)) {
                return BigDecimal.ONE.divide(exchangeRate.getRate(), 6, RoundingMode.HALF_UP);

            }
        }
        return null;
    }

    private BigDecimal findTransit(List<ExchangeRate> rates, String from, String to) {
        for (ExchangeRate first : rates) {
            if (first.getBaseCurrency().getCode().equals(from)) {
                String middle = first.getTargetCurrency().getCode();

                for (ExchangeRate second : rates) {
                    if (second.getBaseCurrency().getCode().equals(middle)
                            && second.getTargetCurrency().getCode().equals(to)) {
                        return first.getRate()
                                .multiply(second.getRate())
                                .setScale(6, RoundingMode.HALF_UP);
                    }
                    if (second.getTargetCurrency().getCode().equals(middle)
                            && second.getBaseCurrency().getCode().equals(to)) {
                        return first.getRate().divide(second.getRate(), 6, RoundingMode.HALF_UP);
                    }
                }
            }
            if (first.getTargetCurrency().getCode().equals(from)) {
                String middle = first.getBaseCurrency().getCode();

                for (ExchangeRate second : rates) {
                    if (second.getBaseCurrency().getCode().equals(middle)
                            && second.getTargetCurrency().getCode().equals(to)) {
                        return second.getRate().divide(first.getRate(), 6, RoundingMode.HALF_UP);
                    }
                    if (second.getTargetCurrency().getCode().equals(middle)
                            && second.getBaseCurrency().getCode().equals(to)) {
                        return BigDecimal.ONE.divide(
                                first.getRate().multiply(second.getRate()),
                                6,
                                RoundingMode.HALF_UP
                        );
                    }
                }
            }
        }
        throw new RateNotFoundException(from, to);

    }
//    private BigDecimal findTransit(List<ExchangeRate> rates, String from, String to) {
//        for (ExchangeRate first : rates) {
//            if (!first.getBaseCurrency().getCode().equals(from)) {
//                continue;
//            }
//            String middle = first.getTargetCurrency().getCode();
//
//            for (ExchangeRate second : rates) {
//                if (!second.getBaseCurrency().getCode().equals(middle)) {
//                    continue;
//                }
//                if (!second.getTargetCurrency().getCode().equals(to)) {
//                    continue;
//                }
//
//                return first.getRate().multiply(second.getRate());
//            }
//        }
//        return null;
//    }
}
