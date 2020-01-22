package internetshop.dao.impl.jdbc;

import internetshop.dao.ItemDao;
import internetshop.lib.Dao;
import internetshop.model.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static String TABLE_NAME = "items";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = String.format("INSERT INTO %s(name,price) VALUES ('%s', %f) ",
                TABLE_NAME, item.getName(), item.getPrice());
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't create item", e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        Statement stmt = null;
        String query = String.format("SELECT * FROM %s WHERE item_id=%d;", TABLE_NAME, id);
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
            logger.warn("Can't get item by id " + id);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Item> update(Item item) {
        String query = String.format("UPDATE items SET name='%s' , price = %f ) WHERE item_id= %d",
                item.getName(), item.getPrice(), item.getId());
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't update item", e);
        }
        return Optional.of(item);
    }

    @Override
    public void delete(Long id) {
        Optional<Item> item = get(id);
        if (item.isPresent()) {
            String query = String.format("DELETE FROM items WHERE item_id = %d", id);
            try {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                logger.warn("Can't delete item " + id, e);
            }
        }
    }

    @Override
    public void delete(Item item) {
        Optional<Item> itemToDelete = get(item.getId());
        if (itemToDelete.isPresent()) {
            String query = String.format("DELETE FROM items WHERE item_id = %d", item.getId());
            try {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                logger.warn("Can't delete item ", e);
            }
        }
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_NAME);

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Item item = new Item(rs.getString("name"),
                        rs.getDouble("price"));
                item.setId(rs.getLong("item_id"));
                allItems.add(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get items", e);
        }
        return allItems;
    }
}
