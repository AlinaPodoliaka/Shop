package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.service.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class InjectDataController extends HttpServlet {

    private static Logger logger = Logger.getLogger(InjectDataController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setName("Scarlet");
        user.setSurname("O'Hara");
        user.addRole(Role.of("USER"));
        user.setLogin("wind");
        user.setPassword("1");
        try {
            userService.create(user);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }

        User admin = new User();
        admin.setName("Ret");
        admin.setSurname("Butler");
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("bad_guy");
        admin.setPassword("2");
        try {
            userService.create(admin);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/servlet/index");

    }
}
