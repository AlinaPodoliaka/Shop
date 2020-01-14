package internetshop.service.impl;

import internetshop.dao.OrderDao;
import internetshop.dao.Storage;
import internetshop.dao.UserDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;
import internetshop.service.OrderService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static UserDao userDao;

    @Override
    public Order create(Order order) {

        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) {

        return orderDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Can't find order with id " + id));
    }

    @Override
    public Order update(Order order) {

        return orderDao.update(order)
                .orElseThrow(() -> new NoSuchElementException("Can't find order"));
    }

    @Override
    public void delete(Long id) {

        orderDao.delete(id);
    }

    @Override
    public Order completeOrder(List<Item> items, User user) {

        Order order = new Order(items, user.getId());
        order.setItems(items);
        orderDao.create(order);

        return order;
    }

    @Override
    public List<Order> getUserOrders(User user) {

        return Storage.orders
                .stream()
                .filter(o -> o.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
