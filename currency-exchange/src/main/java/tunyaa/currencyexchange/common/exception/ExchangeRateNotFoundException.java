package tunyaa.currencyexchange.common.exception;

/**
 *
 * @author oldca
 */
public class ExchangeRateNotFoundException extends RuntimeException {

    public ExchangeRateNotFoundException(String code) {
        super(code);
    }

}
