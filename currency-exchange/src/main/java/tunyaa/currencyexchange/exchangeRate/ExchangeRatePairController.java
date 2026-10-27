package tunyaa.currencyexchange.exchangerate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import tunyaa.currencyexchange.common.AppContext;
import tunyaa.currencyexchange.common.util.JsonUtil;

/**
 *
 * @author oldca
 */
@WebServlet("/exchangeRate/*")
public class ExchangeRatePairController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            JsonUtil.write(resp, 400, Map.of("message", "Pair required"));
            return;
        }

        String pair = pathInfo.substring(1);

        if (pair.length() != 6) {
            JsonUtil.write(resp, 400, Map.of("message", "Pair must be 6 chars, e.g. USDRUB"));
            return;
        }

        String baseCode = pair.substring(0, 3);
        String targetCode = pair.substring(3);

        try {
            ExchangeRate rate = AppContext.EXCHANGE_RATE_SERVICE
                    .getExchangeRate(baseCode, targetCode);
            JsonUtil.write(resp, 200, rate);
        } catch (RuntimeException e) {// TODO заменить на ExchangeRateNotFoundException
            JsonUtil.write(resp, 404, Map.of("message", e.getMessage()));
        }
    }

}
