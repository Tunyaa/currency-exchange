package tunyaa.currencyexchange.common.exception;

public class ExchangeRateNotFoundException extends RuntimeException {

    public ExchangeRateNotFoundException(String code) {
        super(code);
    }

}
