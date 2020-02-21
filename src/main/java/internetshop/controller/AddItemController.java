package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Item;
import internetshop.service.ItemService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AddItemController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddItemController.class);

    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/addItem.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Item newItem = new Item(req.getParameter("item_name"),
                Double.valueOf(req.getParameter("item_price")));

        try {
            itemService.create(newItem);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");

    }
}
