package app.controllers.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "/*")
public class RouteFilter implements Filter {
    private static final Pattern FILE_FORMAT = Pattern.compile("(\\.js|\\.html|\\.css|\\.otf|\\.jpg|\\.ico|\\.png)");

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest httpRequest = (HttpServletRequest) req;
        final String urlPath = httpRequest.getServletPath();

        if (urlPath.contains("data-api") || FILE_FORMAT.matcher(urlPath).find()) {
            chain.doFilter(req, resp);
        } else {
            httpRequest.getRequestDispatcher("/index.html").forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
