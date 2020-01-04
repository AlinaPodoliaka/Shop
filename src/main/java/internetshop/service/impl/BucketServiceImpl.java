package internetshop.service.impl;

import internetshop.dao.BucketDao;
import internetshop.dao.ItemDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.service.BucketService;

import java.util.List;
import java.util.Optional;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public Optional<Bucket> addItem(Long bucketId, Long itemId) {
        Optional<Bucket> bucket = bucketDao.get(bucketId);
        Optional<Item> item = itemDao.get(itemId);
        bucket.get().getItems().add(item.get());
        return bucketDao.update(bucket.get());
    }

    @Override
    public Bucket create(Bucket bucket) {

        return bucketDao.create(bucket);
    }

    @Override
    public Optional<Bucket> get(Long id) {

        return bucketDao.get(id);
    }

    @Override
    public Optional<Bucket> update(Bucket bucket) {

        return bucketDao.update(bucket);
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
        bucket = bucketDao.get(bucket.getId()).get();
        return bucket.getItems();
    }

}
