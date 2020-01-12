package internetshop.service;

import internetshop.model.Bucket;
import internetshop.model.Item;

import java.util.List;
import java.util.Optional;

public interface BucketService {

    void addItem(Long bucketId, Long itemId);

    Bucket create(Bucket bucket);

    Optional<Bucket> get(Long id);

    Bucket getByUserId(Long userId);

    Optional<Bucket> update(Bucket bucket);

    void deleteItem(Bucket bucket, Item item);

    void clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);
}
