package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.service.BucketService;
import internetshop.service.ItemService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DeleteItemFromBucketController extends HttpServlet {

    private static Logger logger = Logger.getLogger(DeleteItemFromBucketController.class);

    @Inject
    private static ItemService itemService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        try {
            Bucket bucket = bucketService.getByUserId(userId);
            String itemId = req.getParameter("item_id");
            Item item = itemService.get(Long.valueOf(itemId)).get();
            bucketService.deleteItem(bucket, item);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
