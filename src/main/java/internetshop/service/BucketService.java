package internetshop.service;

import internetshop.model.Bucket;
import internetshop.model.Item;

import java.util.List;
import java.util.Optional;

public interface BucketService {

    Optional<Bucket> addItem(Long bucketId, Long itemId);

    Bucket create(Bucket bucket);

    Optional<Bucket> get(Long id);

    Optional<Bucket> update(Bucket bucket);

    void deleteItem(Bucket bucket, Item item);

    void clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);

}
