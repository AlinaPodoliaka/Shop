package internetshop.service;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;

import java.util.List;

public interface OrderService {

    Order create(Order order) throws DataProcessingException;

    Order get(Long id) throws DataProcessingException;

    Order update(Order order) throws DataProcessingException;

    void delete(Long id) throws DataProcessingException;

    Order completeOrder(List<Item> items, User user) throws DataProcessingException;

    List<Order> getUserOrders(User user);
}
