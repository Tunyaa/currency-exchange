package tunyaa.currencyexchange.currency;

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
@WebServlet("/currencies")
public class CurrencyListController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonUtil.write(resp, 200, AppContext.CURRENCY_SERVICE.getCurrencies());
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
            Currency created = AppContext.CURRENCY_SERVICE.addCurrency(request.code(), request.fullName(), request.sign());
            JsonUtil.write(resp, 201, created);
        } catch (RuntimeException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
        }
    }
    
}
