package tunyaa.currencyexchange.exchangerate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import tunyaa.currencyexchange.common.AppContext;
import tunyaa.currencyexchange.common.exception.DuplicateException;
import tunyaa.currencyexchange.common.util.JsonUtil;

@WebServlet("/exchangeRates")
public class ExchangeRateController extends HttpServlet {

    // GET /exchangeRates
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonUtil.write(resp, 200, AppContext.EXCHANGE_RATE_SERVICE.getExchangeRates());
    }

    // POST /exchangeRates
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        try {
            if (baseCurrencyCode == null || targetCurrencyCode == null || rate == null) {
                JsonUtil.write(resp, 400, Map.of("message", "Missing form fields: baseCurrencyCode, targetCurrencyCode, rate"));
                return;
            }
            ExchangeRate created = AppContext.EXCHANGE_RATE_SERVICE.addExchangeRate(baseCurrencyCode, targetCurrencyCode, new BigDecimal(rate));
            JsonUtil.write(resp, 201, created);
        } catch (NumberFormatException e) {
            JsonUtil.write(resp, 400, Map.of("message", "Invalid rate"));
        } catch (DuplicateException e) {
            JsonUtil.write(resp, 409, Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
        }
    }

}
