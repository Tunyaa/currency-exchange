package tunyaa.currencyexchange.common.exception;

public class DuplicateException extends RuntimeException {

    public DuplicateException(String code) {
        super(code);
    }

}
