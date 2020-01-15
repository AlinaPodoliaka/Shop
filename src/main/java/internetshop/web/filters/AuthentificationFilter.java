package internetshop.web.filters;

import internetshop.lib.Inject;
import internetshop.model.User;
import internetshop.service.UserService;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AuthentificationFilter implements Filter {

    private static Logger logger = Logger.getLogger(AuthentificationFilter.class);

    @Inject
    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (req.getCookies() == null) {
            processUnAuthentificated(req, response);
            return;
        }
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("MATE")) {
                Optional<User> user = userService.getByToken(cookie.getValue());
                if (user.isPresent()) {
                    logger.info("User " + user.get().getLogin() + " was authenticated");
                    chain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        logger.info("User was NOT authenticated");
        processUnAuthentificated(req, response);
    }

    private void processUnAuthentificated(HttpServletRequest req, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
