package tunyaa.currencyexchange.currency;

/**
 *
 * @author oldca
 */
public final class Currency {

    private final Long id;
    private final String code;
    private final String fullName;
    private final String sign;

    public Currency(Long id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "Currency{" + "id=" + id + ", code=" + code + ", fullName=" + fullName + ", sign=" + sign + '}';
    }

}
