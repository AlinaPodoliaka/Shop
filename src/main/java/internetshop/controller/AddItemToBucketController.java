package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.service.BucketService;
import internetshop.service.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AddItemToBucketController extends HttpServlet {

    private static Logger logger = Logger.getLogger(AddItemToBucketController.class);

    @Inject
    private static UserService userService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long userId = (Long) req.getSession().getAttribute("userId");
            Bucket bucket = null;
            bucket = bucketService.getByUserId(userId);
            String itemId = req.getParameter("item_id");
            bucketService.addItem(bucket.getId(), Long.valueOf(itemId));
        } catch (DataProcessingException e) {
            logger.error(e);
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");

    }
}
