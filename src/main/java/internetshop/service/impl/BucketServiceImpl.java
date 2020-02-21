package internetshop.service.impl;

import internetshop.dao.BucketDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.model.User;
import internetshop.service.BucketService;

import java.util.List;
import java.util.Optional;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) throws DataProcessingException {
        return bucketDao.get(id).orElseThrow(() ->
                new DataProcessingException("Can't get bucket with id "));
    }

    @Override
    public Bucket getByUser(User user) throws DataProcessingException {
        Optional<Bucket> bucket = bucketDao.getByUser(user);
        if (bucket.isPresent()) {
            return bucket.get();
        }
        return create(new Bucket(user));
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        return bucketDao.update(bucket);
    }

    @Override
    public void deleteById(Long id) throws DataProcessingException {
        bucketDao.deleteById(id);
    }

    @Override
    public void delete(Bucket bucket) throws DataProcessingException {
        bucketDao.delete(bucket);
    }

    @Override
    public List<Bucket> getAll() throws DataProcessingException {
        return bucketDao.getAll();
    }

    @Override
    public void addItem(Bucket bucket, Item item) throws DataProcessingException {
        bucket.addItem(item);
        update(bucket);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) throws DataProcessingException {
        bucket.deleteItem(item);
        update(bucket);
    }

    @Override
    public void clear(Bucket bucket) throws DataProcessingException {
        bucket.clear();
        update(bucket);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucket.getItems();
    }
}
