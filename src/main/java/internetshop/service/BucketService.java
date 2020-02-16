package internetshop.service;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.model.User;

import java.util.List;

public interface BucketService extends GenericService<Bucket,Long> {

    Bucket getByUser(User user) throws DataProcessingException;

    void addItem(Bucket bucket, Item item) throws DataProcessingException;

    void deleteItem(Bucket bucket, Item item) throws DataProcessingException;

    void clear(Bucket bucket) throws DataProcessingException;

    List<Item> getAllItems(Bucket bucket);

}
