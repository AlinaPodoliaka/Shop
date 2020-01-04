package internetshop.service;

import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order create(Order order);

    Optional<Order> get(Long id);

    Optional<Order> update(Order order);

    void delete(Long id);

    Order completeOrder(List<Item> items, User user);

    List<Order> getUserOrders(User user);

}
