package internetshop.dao.jdbc;

import internetshop.dao.ItemDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) throws DataProcessingException {
        String query = "INSERT INTO items (name, price) VALUES (?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                Long itemId = rs.getLong(1);
                item.setId(itemId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create item ", e);
        }

        return item;
    }

    @Override
    public Optional<Item> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM items WHERE item_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get item ", e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        String query = "UPDATE items SET name = ?, price = ? WHERE item_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setLong(3, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update item ", e);
        }
        return item;
    }

    @Override
    public void deleteById(Long id) throws DataProcessingException {
        String query = "DELETE FROM items WHERE item_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete item ", e);
        }
    }

    @Override
    public void delete(Item item) throws DataProcessingException {
        deleteById(item.getId());
    }

    @Override
    public List<Item> getAll() throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all items ", e);
        }
        return items;
    }
}
