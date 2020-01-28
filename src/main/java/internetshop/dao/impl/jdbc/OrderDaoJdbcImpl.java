package internetshop.dao.impl.jdbc;

import internetshop.dao.ItemDao;
import internetshop.dao.OrderDao;
import internetshop.dao.UserDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.lib.Inject;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {

    private static Logger logger = Logger.getLogger(OrderDaoJdbcImpl.class);

    @Inject
    private static UserDao userDao;

    @Inject
    private static ItemDao itemDao;

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) throws DataProcessingException {
        Long orderId = null;
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (PreparedStatement stmt
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, order.getUserId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order", e);
        }
        String insertOrderQuery = "INSERT INTO orders_items (order_id, item_id) VALUES (?,?);";
        try (PreparedStatement stmt = connection.prepareStatement(insertOrderQuery)) {
            stmt.setLong(1, orderId);
            for (Item item : order.getItems()) {
                stmt.setLong(2, item.getId());
                stmt.execute();
            }
            return new Order(orderId, order.getItems());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order", e);
        }
    }

    @Override
    public Optional<Order> get(Long id) throws DataProcessingException {
        String query = "SELECT orders.order_id, orders.user_id, "
                + "items.item_id, items.name, items.price "
                + "FROM orders "
                + "INNER JOIN orders_items ON orders.order_id = orders_items.order_id "
                + "INNER JOIN items ON orders_items.item_id = items.item_id "
                + "WHERE orders.order_id = ?;";
        List<Item> itemsList = new ArrayList<>();
        Long userId = null;
        Optional<User> user = Optional.of(new User());
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userId = rs.getLong("user_id");
                user = userDao.get(userId);
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Item item = new Item();
                item.setId(itemId);
                item.setName(name);
                item.setPrice(price);
                itemsList.add(item);
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't get order by id", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Order> update(Order order) throws DataProcessingException {
        String query = "UPDATE orders SET user_id = (?) WHERE order_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, order.getUserId());
            stmt.setLong(2, order.getId());
            stmt.executeUpdate();
            return Optional.of(order);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order", e);
        }
    }

    @Override
    public void delete(Long id) throws DataProcessingException {
        String query = "DELETE FROM orders_items WHERE order_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order", e);
        }
        String deleteOrderQquery = "DELETE FROM orders WHERE order_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(deleteOrderQquery)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order", e);
        }
    }
}
