package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.service.UserService;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class RegistrationController extends HttpServlet {

    private static Logger logger = Logger.getLogger(RegistrationController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User newUser = new User();
        newUser.setLogin(req.getParameter("login"));
        newUser.setPassword(req.getParameter("psw"));
        newUser.setName(req.getParameter("user_name"));
        newUser.setSurname(req.getParameter("user_surname"));
        newUser.setRoles(Collections.singleton(Role.of("USER")));
        User user = null;
        try {
            user = userService.create(newUser);
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());

            Cookie cookie = new Cookie("MATE", user.getToken());
            resp.addCookie(cookie);

        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);

        }
        resp.sendRedirect(req.getContextPath() + "/servlet/index");
    }
}
