package tunyaa.currencyexchange.currency;

/**
 *
 * @author oldca
 */
public class CurrencyRequest {

    private String code;
    private String fullName;
    private String sign;

    public CurrencyRequest() {
    }  // нужен Jackson

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSign() {
        return sign;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
