package tunyaa.currencyexchange.currency;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

/**
 *
 * @author oldca
 */
@WebServlet("/currencies/*")
public class CurrencyController extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService(new InMemoryCurrencyRepository());
    
    
}
