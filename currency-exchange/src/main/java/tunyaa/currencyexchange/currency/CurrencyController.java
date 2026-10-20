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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyRequest request;
        try {
            request = JsonUtil.read(req.getReader(), CurrencyRequest.class);
        } catch (IOException e) {
            JsonUtil.write(resp, 400, Map.of("message", "Invalid JSON"));
            return;
        }

        try {
            Currency created = AppContext.CURRENCY_SERVICE.addCurrency(
                    request.code(), request.fullName(), request.sign());
            JsonUtil.write(resp, 201, created);
        } catch (RuntimeException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
        }
    }

}
