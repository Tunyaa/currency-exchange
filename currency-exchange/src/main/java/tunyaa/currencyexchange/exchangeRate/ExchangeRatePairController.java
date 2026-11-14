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
import tunyaa.currencyexchange.common.exception.RateNotFoundException;
import tunyaa.currencyexchange.common.util.JsonUtil;

@WebServlet("/exchangeRate/*")
public class ExchangeRatePairController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCode;
        String targetCode;

        try {
            String[] pair = parsePair(req.getPathInfo());
            baseCode = pair[0];
            targetCode = pair[1];
            ExchangeRate rate = AppContext.EXCHANGE_RATE_SERVICE
                    .getExchangeRate(baseCode, targetCode);
            JsonUtil.write(resp, 200, rate);
        } catch (ExchangeRateNotFoundException e) {
            JsonUtil.write(resp, 404, Map.of("message", e.getMessage()));
        } catch (IllegalArgumentException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String baseCode;
        String targetCode;

        ExchangeRateUpdateRequest request;
        try {
            String[] pair = parsePair(req.getPathInfo());
            baseCode = pair[0];
            targetCode = pair[1];
            request = JsonUtil.read(req.getReader(), ExchangeRateUpdateRequest.class);
        } catch (IOException e) {
            JsonUtil.write(resp, 400, Map.of("message", "Invalid JSON"));
            return;
        } catch (IllegalArgumentException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
            return;
        }
        BigDecimal rate = request.rate();

        try {
            ExchangeRate updateExchangeRate = AppContext.EXCHANGE_RATE_SERVICE.updateExchangeRate(baseCode, targetCode, rate);
            JsonUtil.write(resp, 200, updateExchangeRate);
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
