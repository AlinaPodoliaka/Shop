package internetshop.dao.impl;

import internetshop.dao.OrderDao;
import internetshop.dao.Storage;
import internetshop.lib.Dao;
import internetshop.model.Order;

import java.util.Optional;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {

        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {

        return Storage.orders
                .stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Order> update(Order order) {

        Optional<Order> updateOrder = get(order.getId());
        updateOrder.get().setItems(order.getItems());
        return updateOrder;
    }

    @Override
    public void delete(Long id) {

        Storage.orders.removeIf(o -> o.getId().equals(id));
    }
}
