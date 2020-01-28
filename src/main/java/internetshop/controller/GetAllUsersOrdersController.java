package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Order;
import internetshop.model.User;
import internetshop.service.OrderService;
import internetshop.service.UserService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class GetAllUsersOrdersController extends HttpServlet {

    private static Logger logger = Logger.getLogger(GetAllUsersOrdersController.class);

    @Inject
    private static UserService userService;

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        User user = null;
        try {
            user = userService.get(userId);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
        List<Order> usersOrders = orderService.getUserOrders(user);
        req.setAttribute("orders", usersOrders);
        req.getRequestDispatcher("/WEB-INF/views/allUsersOrders.jsp").forward(req, resp);

    }
}
