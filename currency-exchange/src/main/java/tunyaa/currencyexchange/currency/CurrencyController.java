package tunyaa.currencyexchange.currency;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import tunyaa.currencyexchange.common.AppContext;
import tunyaa.currencyexchange.common.exception.CurrencyNotFoundException;
import tunyaa.currencyexchange.common.util.JsonUtil;

/**
 *
 * @author oldca
 */
@WebServlet("/currency/*")
public class CurrencyController extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService(new InMemoryCurrencyRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();   // "/EUR" или null

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Currency> all = AppContext.CURRENCY_SERVICE.getCurrencies();
            JsonUtil.write(resp, 200, all);
            return;
        }

        String code = pathInfo.substring(1);   // убираем "/"
        try {
            Currency currency = AppContext.CURRENCY_SERVICE.getCurrency(code);
            JsonUtil.write(resp, 200, currency);
        } catch (CurrencyNotFoundException e) {
            JsonUtil.write(resp, 404, Map.of("message", e.getMessage()));
        }
    }

}
