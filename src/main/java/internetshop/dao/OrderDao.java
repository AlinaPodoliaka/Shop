package internetshop.dao;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Order;
import internetshop.model.User;

import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    List<Order> getUserOrders(User user) throws DataProcessingException;
}
