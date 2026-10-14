package tunyaa.currencyexchange.common.exception;

/**
 *
 * @author oldca
 */
public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(String code) {
        super("Currency not found: " + code);
    }

}
