package internetshop.dao;

import internetshop.model.Order;

import java.util.Optional;

public interface OrderDao {

    Order create(Order order);

    Optional<Order> get(Long id);

    Optional<Order> update(Order order);

    void delete(Long id);
}
