package internetshop.controller;

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

public class GetAllUsersOrdersController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Inject
    private static OrderService orderService;

    private static Long USER_ID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = userService.get(USER_ID);
        List<Order> usersOrders = orderService.getUserOrders(user);
        req.setAttribute("orders", usersOrders);
        req.getRequestDispatcher("WEB-INF/views/allUsersOrders.jsp").forward(req, resp);

    }
}
