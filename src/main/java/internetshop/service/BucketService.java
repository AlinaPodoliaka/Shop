package internetshop.service;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Bucket;
import internetshop.model.Item;

import java.util.List;

public interface BucketService {

    void addItem(Long bucketId, Long itemId) throws DataProcessingException;

    Bucket create(Bucket bucket) throws DataProcessingException;

    Bucket get(Long id) throws DataProcessingException;

    Bucket getByUserId(Long userId) throws DataProcessingException;

    Bucket update(Bucket bucket) throws DataProcessingException;

    void deleteItem(Bucket bucket, Item item) throws DataProcessingException;

    void clear(Bucket bucket) throws DataProcessingException;

    List<Item> getAllItems(Bucket bucket) throws DataProcessingException;

    List<Bucket> getAll() throws DataProcessingException;

}
