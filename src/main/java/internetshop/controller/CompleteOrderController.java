package internetshop.controller;

import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;
import internetshop.service.BucketService;
import internetshop.service.OrderService;
import internetshop.service.UserService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompleteOrderController extends HttpServlet {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        User user = userService.get(userId);
        Bucket bucket = bucketService.getByUserId(userId);
        Order order = orderService.completeOrder(bucket.getItems(), user);

        bucketService.clear(bucket);

        List<Item> itemsInOrder = orderService.get(order.getId()).getItems();
        req.setAttribute("items", itemsInOrder);
        req.setAttribute("order_id", order.getId());

        req.getRequestDispatcher("WEB-INF/views/currentOrder.jsp").forward(req, resp);

    }
}
