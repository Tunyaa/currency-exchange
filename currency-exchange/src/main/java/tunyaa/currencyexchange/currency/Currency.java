package tunyaa.currencyexchange.currency;

/**
 *
 * @author oldca
 */
public final class Currency {

    private final Long id;
    private final String name;
    private final String code;
    private final String sign;

    public Currency(Long id, String name, String code, String sign) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.sign = sign;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "Currency{" + "id=" + id + ", name=" + name + ", code=" + code + ", sign=" + sign + '}';
    }

}
