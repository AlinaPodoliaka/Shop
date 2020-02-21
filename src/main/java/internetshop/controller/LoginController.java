package internetshop.controller;

import internetshop.exceptions.AuthenticationException;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.User;
import internetshop.service.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("login");
        String password = req.getParameter("psw");
        try {
            User user = userService.login(username, password);

            HttpSession session = req.getSession(true);
            session.setAttribute("user_id", user.getId());

            resp.sendRedirect(req.getContextPath() + "/servlet/index");
        } catch (AuthenticationException e) {
            req.setAttribute("errorMsg", e.getMessage());

            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
