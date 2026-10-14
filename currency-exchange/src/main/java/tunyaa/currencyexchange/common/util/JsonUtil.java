package tunyaa.currencyexchange.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author oldca
 */
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void write(HttpServletResponse resp, int status, Object body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        MAPPER.writeValue(resp.getWriter(), body);
    }
}
