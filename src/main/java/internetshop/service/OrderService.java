package internetshop.service;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;

import java.util.List;

public interface OrderService extends GenericService<Order, Long> {

    Order completeOrder(List<Item> items, User user) throws DataProcessingException;

    List<Order> getUserOrders(User user) throws DataProcessingException;
}
