package internetshop.dao.impl.jdbc;

import internetshop.dao.ItemDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) throws DataProcessingException {
        String query = "INSERT INTO items (name, price) VALUES (?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1,item.getName());
            stmt.setDouble(2,item.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create item", e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM items WHERE item_id = (?);";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1,String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get item", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Item> update(Item item) throws DataProcessingException {
        String query = "UPDATE items SET name = (?), price = (?) WHERE item_id = (?);";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1,item.getName());
            stmt.setDouble(2,item.getPrice());
            stmt.setString(3,String.valueOf(item.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update item", e);
        }
        return Optional.of(item);
    }

    @Override
    public void delete(Long id) throws DataProcessingException {
        Optional<Item> item = get(id);
        if (item.isPresent()) {
            String query = "DELETE FROM items WHERE item_id = (?);";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1,String.valueOf(id));
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataProcessingException("Can't delete item", e);
            }
        }
    }

    @Override
    public void delete(Item item) throws DataProcessingException {
        Optional<Item> itemToDelete = get(item.getId());
        if (itemToDelete.isPresent()) {
            String query = "DELETE FROM items WHERE item_id = (?);";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1,String.valueOf(item.getId()));
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataProcessingException("Can't delete item", e);
            }
        }
    }

    @Override
    public List<Item> getAllItems() throws DataProcessingException {
        List<Item> allItems = new ArrayList<>();
        String query = "SELECT * FROM items;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString("name"),
                        rs.getDouble("price"));
                item.setId(rs.getLong("item_id"));
                allItems.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all items", e);
        }
        return allItems;
    }
}
