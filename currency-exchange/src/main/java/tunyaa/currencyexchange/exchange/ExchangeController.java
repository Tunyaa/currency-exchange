package tunyaa.currencyexchange.exchange;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import tunyaa.currencyexchange.common.AppContext;
import tunyaa.currencyexchange.common.exception.RateNotFoundException;
import tunyaa.currencyexchange.common.util.JsonUtil;

/**
 *
 * @author oldca
 */
@WebServlet("/exchange")
public class ExchangeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amountStr = req.getParameter("amount");

        if (from == null || to == null || amountStr == null) {
            JsonUtil.write(resp, 400, Map.of("message", "Missing parameters"));
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(amountStr);
            ExchangeResponse exchangeResponse = AppContext.EXCHANGE_SERVICE.exchange(amount, from, to);
            JsonUtil.write(resp, 200, exchangeResponse);
            return;
        } catch (NumberFormatException e) {
            JsonUtil.write(resp, 400, Map.of("message", "Invalid amount"));
        } catch (RateNotFoundException e) {
            JsonUtil.write(resp, 404, Map.of("message", e.getMessage()));
        }

    }

}
