package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.model.User;
import internetshop.service.BucketService;
import internetshop.service.OrderService;
import internetshop.service.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CompleteOrderController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CompleteOrderController.class);

    @Inject
    private static BucketService bucketService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long userId = (Long) req.getSession().getAttribute("user_id");
        try {
            User user = userService.get(userId);
            Bucket bucket = bucketService.getByUser(user);
            orderService.completeOrder(bucket.getItems(), user);
            bucketService.clear(bucket);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/servlet/allUsersOrders");
    }
}
