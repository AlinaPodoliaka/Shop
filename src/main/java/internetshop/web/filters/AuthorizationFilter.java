package internetshop.web.filters;

import static internetshop.model.Role.RoleName.ADMIN;
import static internetshop.model.Role.RoleName.USER;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.service.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);

    private static final String EMPTY_STRING = "";
    @Inject
    private static UserService userService;

    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        protectedUrls.put("/servlet/getAllUsers", ADMIN);
        protectedUrls.put("/servlet/addItem", ADMIN);
        protectedUrls.put("/servlet/addItemToBucket", USER);
        protectedUrls.put("/servlet/currentOrder", USER);
        protectedUrls.put("/servlet/bucket", USER);
        protectedUrls.put("/servlet/allUsersOrders", USER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String requestedUrl = req.getRequestURI().replace(req.getContextPath(), EMPTY_STRING);
        Role.RoleName roleName = protectedUrls.get(requestedUrl);

        if (roleName == null) {
            processAuthorized(req, resp, chain);
            return;
        }

        Long userId = (Long) req.getSession().getAttribute("user_id");

        try {
            User user = userService.get(userId);

            if (verifyRole(user, roleName)) {
                processAuthorized(req, resp, chain);
            } else {
                processDenied(req, resp);
            }
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles()
                .stream()
                .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    private void processAuthorized(HttpServletRequest req, HttpServletResponse resp,
                                   FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(req, resp);
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
    }
}
