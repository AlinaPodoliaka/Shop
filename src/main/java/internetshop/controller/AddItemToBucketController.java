package internetshop.controller;

import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.service.BucketService;
import internetshop.service.ItemService;
import internetshop.service.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddItemToBucketController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static ItemService itemService;

    private static final Long USER_ID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Bucket bucket = bucketService.getByUserId(USER_ID);

        String itemId = req.getParameter("item_id");
        Item item = itemService.get(Long.valueOf(itemId)).get();

        bucketService.addItem(bucket.getId(), Long.valueOf(itemId));

        resp.sendRedirect(req.getContextPath() + "/bucket");

    }
}