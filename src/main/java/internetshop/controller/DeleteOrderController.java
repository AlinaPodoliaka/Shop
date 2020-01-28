package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.service.OrderService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DeleteOrderController extends HttpServlet {
    private static Logger logger = Logger.getLogger(DeleteOrderController.class);

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            orderService.delete(Long.valueOf(req.getParameter("order_id")));
        } catch (DataProcessingException e) {
            logger.error(e);
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);

        }
        resp.sendRedirect(req.getContextPath() + "/servlet/allUsersOrders");
    }
}
