package internetshop.dao.impl.jdbc;

import internetshop.dao.BucketDao;
import internetshop.dao.ItemDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.model.Item;

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
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {

    private static Logger logger = Logger.getLogger(BucketDaoJdbcImpl.class);

    @Inject
    private static ItemDao itemDao;

    public BucketDaoJdbcImpl(Connection connection) {

        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        String query = "INSERT INTO buckets (user_id) VALUES (?);";

        try (PreparedStatement stmt = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, bucket.getUserId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            while (rs.next()) {
                Long bucketId = rs.getLong(1);
                bucket.setId(bucketId);
            }
            addItemsToBucket(bucket, bucket.getItems());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create bucket", e);
        }

        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) throws DataProcessingException {

        String query = "SELECT * FROM buckets WHERE bucket_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long bucketId = rs.getLong("bucket_id");
                Long userId = rs.getLong("user_id");
                Bucket bucket = new Bucket(userId);
                bucket.setId(bucketId);
                addItemsToBucket(bucket, bucket.getItems());
                return Optional.of(bucket);
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Failed to get bucket", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Bucket> update(Bucket bucket) throws DataProcessingException {

        String query = "UPDATE buckets SET user_id = ? WHERE bucket_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, bucket.getUserId());
            stmt.setLong(2, bucket.getId());
            stmt.executeUpdate();

            List<Item> newItems = bucket.getItems();
            List<Item> itemsToDelete = new ArrayList<>(getItemsFromBucket(bucket));
            itemsToDelete.removeAll(newItems);
            deleteItems(bucket, itemsToDelete);
            List<Item> itemsToAdd = new ArrayList<>(newItems);
            itemsToAdd.removeAll(getItemsFromBucket(bucket));
            addItemsToBucket(bucket, itemsToAdd);

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update bucket", e);
        }
        return Optional.of(bucket);
    }

    private void deleteItems(Bucket bucket, List<Item> items) throws DataProcessingException {
        String query = "DELETE FROM bucket_items WHERE bucket_id = ? AND item_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Item item : items) {
                statement.setLong(1, bucket.getId());
                statement.setLong(2, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update items in bucket ", e);
        }
    }

    private List<Item> getItemsFromBucket(Bucket bucket) throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT item_id, name, price FROM items  JOIN bucket_items "
                + " ON items.item_id = bucket_items.item_id AND bucket_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, bucket.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String itemName = rs.getString("name");
                Double itemPrice = rs.getDouble("price");
                Item item = new Item(itemName, itemPrice);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get items from bucket", e);
        }
        return items;
    }

    @Override
    public void delete(Long id) throws DataProcessingException {
        String query = "DELETE FROM buckets WHERE bucket_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete bucket", e);
        }
    }

    @Override
    public List<Bucket> getAll() throws DataProcessingException {
        List<Bucket> buckets = new ArrayList<>();
        String query = "SELECT * FROM buckets;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long bucketId = rs.getLong("bucket_id");
                Long userId = rs.getLong("user_id");
                Bucket bucket = new Bucket(userId);
                bucket.setId(bucketId);
                addItemsToBucket(bucket, bucket.getItems());
                buckets.add(bucket);
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't get all buckets ", e);
        }
        return buckets;
    }

    @Override
    public void addItem(Long bucketId, Long itemId) throws DataProcessingException {
        String query = "INSERT INTO bucket_items (bucket_id, item_id) VALUES (?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            Bucket bucket = new Bucket(bucketId);
            for (Item item : bucket.getItems()) {
                stmt.setLong(1, bucketId);
                stmt.setLong(2, itemId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add items in bucket: ", e);
        }
    }

    private void addItemsToBucket(Bucket bucket, List<Item> items) throws DataProcessingException {
        String query = "INSERT INTO bucket_items (bucket_id, item_id) VALUES (?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Item item : items) {
                stmt.setLong(1, bucket.getId());
                stmt.setLong(2, item.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add items in bucket: ", e);
        }
    }
}
