package internetshop.dao.jdbc;

import internetshop.dao.OrderDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) throws DataProcessingException {
        String query = "INSERT INTO orders (user_id) VALUES (?);";

        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                Long orderId = rs.getLong(1);
                order.setId(orderId);
            }
            addItems(order, order.getItems());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order ", e);
        }

        return order;
    }

    @Override
    public Optional<Order> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM orders WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Long orderId = rs.getLong("order_id");
                Long userId = rs.getLong("user_id");
                Order order = new Order(userId);
                order.setId(orderId);
                order.setItems(getItems(order));
                return Optional.of(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> getUserOrders(User user) throws DataProcessingException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            orders.addAll(getOrders(statement));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders ", e);
        }
        return orders;
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        String query = "UPDATE orders SET user_id = ? WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();

            List<Item> oldItems = getItems(order);
            List<Item> newItems = order.getItems();

            List<Item> itemsToDelete = new ArrayList<>(oldItems);
            itemsToDelete.removeAll(newItems);
            deleteItems(order, itemsToDelete);

            List<Item> itemsToAdd = new ArrayList<>(newItems);
            itemsToAdd.removeAll(oldItems);
            addItems(order, itemsToAdd);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order ", e);
        }
        return order;
    }

    @Override
    public void deleteById(Long id) throws DataProcessingException {
        Order order = get(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void delete(Order order) throws DataProcessingException {
        deleteItems(order, order.getItems());
        String query = "DELETE FROM orders WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order ", e);
        }
    }

    @Override
    public List<Order> getAll() throws DataProcessingException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            orders.addAll(getOrders(statement));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders ", e);
        }
        return orders;
    }

    private List<Order> getOrders(PreparedStatement statement)
            throws SQLException, DataProcessingException {
        List<Order> orders = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Long bucketId = rs.getLong("order_id");
            Long userId = rs.getLong("user_id");
            Order order = new Order(userId);
            order.setId(bucketId);
            order.setItems(getItems(order));
            orders.add(order);
        }
        return orders;
    }

    private void addItems(Order order, List<Item> items) throws DataProcessingException {
        String query = "INSERT INTO order_items (order_id, item_id) VALUES (?, ?);";
        changeOrderItemsExecute(order, items, query);
    }

    private void deleteItems(Order order, List<Item> items) throws DataProcessingException {
        String query = "DELETE FROM order_items WHERE order_id = ? AND item_id = ?;";
        changeOrderItemsExecute(order, items, query);
    }

    private void changeOrderItemsExecute(Order order, List<Item> items, String query)
            throws DataProcessingException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Item item : items) {
                statement.setLong(1, order.getId());
                statement.setLong(2, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update items in order ", e);
        }
    }

    private List<Item> getItems(Order order) throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT i.item_id, name, price FROM items i JOIN order_items oi"
                + " ON i.item_id = oi.item_id AND order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long itemId = rs.getLong("i.item_id");
                String itemName = rs.getString("name");
                Double itemPrice = rs.getDouble("price");
                Item item = new Item(itemName, itemPrice);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get items from order ", e);
        }
        return items;
    }
}
