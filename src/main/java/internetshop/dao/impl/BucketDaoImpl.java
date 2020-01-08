package internetshop.dao.impl;

import internetshop.dao.BucketDao;
import internetshop.dao.Storage;
import internetshop.lib.Dao;
import internetshop.model.Bucket;

import java.util.Optional;

@Dao
public class BucketDaoImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {

        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) {

        return Storage.buckets
                .stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Bucket> update(Bucket bucket) {

        Optional<Bucket> updateBucket = get(bucket.getId());
        updateBucket.get().setItems(bucket.getItems());

        return updateBucket;
    }

    @Override
    public void delete(Long id) {

        Storage.buckets.removeIf(bucket -> bucket.getId().equals(id));
    }
}
