package internetshop.service.impl;

import internetshop.dao.BucketDao;
import internetshop.dao.ItemDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.service.BucketService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public void addItem(Long bucketId, Long itemId) throws DataProcessingException {
        Bucket bucket = new Bucket(bucketId);
        Item item = new Item(itemId);
        bucket.getItems().add(item);
        bucketDao.update(bucket);

    }

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) throws DataProcessingException {
        return bucketDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket with id " + id));
    }

    @Override
    public Bucket getByUserId(Long userId) throws DataProcessingException {
        Optional<Bucket> bucket = getAll()
                .stream()
                .filter(b -> b.getUserId().equals(userId))
                .findFirst();
        if (bucket.isPresent()) {
            return bucket.get();
        }
        return create(new Bucket(userId));
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        return bucketDao.update(bucket)
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket"));
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) throws DataProcessingException {
        Bucket delBucket = bucketDao.get(bucket.getId())
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket"));
        List<Item> itemsInBucket = delBucket.getItems();
        itemsInBucket.remove(item);
        bucketDao.update(delBucket);
    }

    @Override
    public void clear(Bucket bucket) throws DataProcessingException {
        bucketDao.delete(bucket.getId());
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) throws DataProcessingException {
        return bucketDao.get(bucket.getId())
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket")).getItems();
    }

    @Override
    public List<Bucket> getAll() throws DataProcessingException {
        return bucketDao.getAll();
    }
}
