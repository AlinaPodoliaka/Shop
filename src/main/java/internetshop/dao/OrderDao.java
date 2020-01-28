package internetshop.dao;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Order;
import java.util.Optional;

public interface OrderDao {

    Order create(Order order) throws DataProcessingException;

    Optional<Order> get(Long id) throws DataProcessingException;

    Optional<Order> update(Order order) throws DataProcessingException;

    void delete(Long id) throws DataProcessingException;
}
