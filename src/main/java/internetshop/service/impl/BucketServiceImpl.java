package internetshop.service.impl;

import internetshop.dao.BucketDao;
import internetshop.dao.ItemDao;
import internetshop.dao.Storage;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.service.BucketService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public void addItem(Long bucketId, Long itemId) {
        Bucket newBucket = bucketDao.get(bucketId).get();
        Item newItem = itemDao.get(itemId).get();
        newBucket.getItems().add(newItem);
        bucketDao.update(newBucket);

    }

    @Override
    public Bucket create(Bucket bucket) {

        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) {

        return bucketDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket with id " + id));
    }

    @Override
    public Bucket getByUserId(Long userId) {
        return Storage.buckets
                .stream()
                .filter(bucket -> bucket.getUserId().equals(userId))
                .findFirst()
                .orElse(create(new Bucket(userId)));
    }

    @Override
    public Bucket update(Bucket bucket) {

        return bucketDao.update(bucket)
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket"));
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {

        Bucket bucket1 = bucketDao.get(bucket.getId()).get();
        List<Item> itemsInBucket = bucket1.getItems();
        itemsInBucket.remove(item);
        bucketDao.update(bucket1);
    }

    @Override
    public void clear(Bucket bucket) {

        bucketDao.delete(bucket.getId());
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {

        return bucketDao.get(bucket.getId()).get().getItems();
    }
}
