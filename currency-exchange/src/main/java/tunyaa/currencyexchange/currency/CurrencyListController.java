package tunyaa.currencyexchange.currency;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import tunyaa.currencyexchange.common.AppContext;
import tunyaa.currencyexchange.common.exception.DuplicateException;
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
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        if (name == null || code == null || sign == null) {
            JsonUtil.write(resp, 400, Map.of("message", "Missing form fields: name, code, sign"));
            return;
        }

        try {
            Currency created = AppContext.CURRENCY_SERVICE.addCurrency(name, code, sign);
            JsonUtil.write(resp, 201, created);
        } catch (DuplicateException e) {
            JsonUtil.write(resp, 409, Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            JsonUtil.write(resp, 400, Map.of("message", e.getMessage()));
        }
    }

}
