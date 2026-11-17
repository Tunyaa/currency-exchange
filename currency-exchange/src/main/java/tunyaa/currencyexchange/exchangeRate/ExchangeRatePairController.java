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
import tunyaa.currencyexchange.common.exception.ExchangeRateNotFoundException;
import tunyaa.currencyexchange.common.util.JsonUtil;

@WebServlet("/exchangeRate/*")
public class ExchangeRatePairController extends HttpServlet {

    // GET /exchangeRate/USDRUB
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode;
        String targetCurrencyCode;

        try {
            String[] pair = parsePair(req.getPathInfo());
            baseCurrencyCode = pair[0];
            targetCurrencyCode = pair[1];
            ExchangeRate rate = AppContext.EXCHANGE_RATE_SERVICE
                    .getExchangeRate(baseCurrencyCode, targetCurrencyCode);
            JsonUtil.write(resp, 200, rate);
        } catch (ExchangeRateNotFoundException e) {
            JsonUtil.write(resp, 404, Map.of("message", e.getMessage()));
        } catch (IllegalArgumentException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
        }
    }

    // PATCH /exchangeRate/USDRUB
    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String baseCurrencyCode;
        String targetCurrencyCode;

        try {
            String[] pair = parsePair(req.getPathInfo());
            baseCurrencyCode = pair[0];
            targetCurrencyCode = pair[1];

        } catch (IllegalArgumentException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
            return;
        }
        String rate = req.getParameter("rate");

        if (baseCurrencyCode == null || targetCurrencyCode == null || rate == null) {
            JsonUtil.write(resp, 400, Map.of("message", "Missing form fields: "
                    + "baseCurrencyCode - " + baseCurrencyCode
                    + ", targetCurrencyCode - " + targetCurrencyCode
                    + ", rate - " + rate));
            return;
        }

        try {
            ExchangeRate updateExchangeRate = AppContext.EXCHANGE_RATE_SERVICE.updateExchangeRate(
                    baseCurrencyCode,
                    targetCurrencyCode,
                    new BigDecimal(rate));

            JsonUtil.write(resp, 200, updateExchangeRate);
        } catch (NumberFormatException e) {
            JsonUtil.write(resp, 400, Map.of("message", "Invalid rate"));
        } catch (IllegalArgumentException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
        } catch (ExchangeRateNotFoundException e) {
            JsonUtil.write(resp, 404, Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
        }

    }

    private String[] parsePair(String pathInfo) {
        String[] codes = new String[2];

        if (pathInfo == null || pathInfo.equals("/")) {
            throw new IllegalArgumentException("Pair required");
        }

        String pair = pathInfo.substring(1);

        if (pair.length() != 6) {
            throw new IllegalArgumentException("Pair must be 6 chars, e.g. USDRUB");
        }

        codes[0] = pair.substring(0, 3);
        codes[1] = pair.substring(3);
        return codes;
    }

}
