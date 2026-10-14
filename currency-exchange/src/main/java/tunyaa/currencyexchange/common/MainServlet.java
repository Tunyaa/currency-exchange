package tunyaa.currencyexchange.common;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import tunyaa.currencyexchange.common.util.JsonUtil;
import tunyaa.currencyexchange.currency.Currency;

/**
 *
 * @author oldca
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        JsonUtil.write(resp, 200, new Currency(1L, "USD", "US Dollar", "$"));

    }
}
