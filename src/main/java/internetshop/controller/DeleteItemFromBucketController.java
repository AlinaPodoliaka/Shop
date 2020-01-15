package internetshop.controller;

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

public class DeleteItemFromBucketController extends HttpServlet {
    @Inject
    private static ItemService itemService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        Bucket bucket = bucketService.getByUserId(userId);
        String itemId = req.getParameter("item_id");
        Item item = itemService.get(Long.valueOf(itemId));
        bucketService.deleteItem(bucket, item);
        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
