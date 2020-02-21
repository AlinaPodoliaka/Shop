package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Item;
import internetshop.service.ItemService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class GetAllItemsController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GetAllItemsController.class);
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = null;
        try {
            items = itemService.getAll();
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }

        req.setAttribute("items", items);
        req.getRequestDispatcher("/WEB-INF/views/servlet/allItems.jsp").forward(req, resp);
    }
}
