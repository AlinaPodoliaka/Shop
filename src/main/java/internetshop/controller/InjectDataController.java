package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.service.ItemService;
import internetshop.service.OrderService;
import internetshop.service.UserService;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class InjectDataController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(InjectDataController.class);

    @Inject
    private static ItemService itemService;

    @Inject
    private static OrderService orderService;

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {

            User user = new User("user");
            user.setPassword("1");
            user.setFirstName("Martin");
            user.setSecondName("Fourcade");
            user.addRoles(Collections.singleton(Role.of("USER")));
            userService.create(user);

            User admin = new User("admin");
            admin.setPassword("2");
            admin.setFirstName("Simon");
            admin.setSecondName("Fourcade");
            admin.addRoles(Collections.singleton(Role.of("ADMIN")));
            userService.create(admin);

        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
