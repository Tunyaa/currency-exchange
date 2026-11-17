package tunyaa.currencyexchange.common.exception;

public class RateNotFoundException extends RuntimeException {

    public RateNotFoundException(String from, String to) {
        super("Exchange rate not found: " + from + " -> " + to);
    }
}
