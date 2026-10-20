package tunyaa.currencyexchange.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

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

    public static <T> T read(Reader reader, Class<T> clazz) throws IOException {
        return MAPPER.readValue(reader, clazz);
    }
}
