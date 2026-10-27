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
@WebServlet("/exchangeRates")
public class ExchangeRateController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonUtil.write(resp, 200, AppContext.EXCHANGE_RATE_SERVICE.getExchangeRates());
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRateRequest request;
        try {
            request = JsonUtil.read(req.getReader(), ExchangeRateRequest.class);
        } catch (IOException e) {
            JsonUtil.write(resp, 400, Map.of("message", "Invalid JSON"));
            return;
        }
        
        try {
            ExchangeRate created = AppContext.EXCHANGE_RATE_SERVICE.addExchangeRate(request.baseCode(), request.targetCode(), request.rate());
            JsonUtil.write(resp, 201, created);
        } catch (RuntimeException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
        }
    }
    
}
