package internetshop.dao;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Bucket;

import java.util.List;
import java.util.Optional;

public interface BucketDao {

    Bucket create(Bucket bucket) throws DataProcessingException;

    Optional<Bucket> get(Long id) throws DataProcessingException;

    Optional<Bucket> update(Bucket bucket) throws DataProcessingException;

    void delete(Long id) throws DataProcessingException;

    List<Bucket> getAll() throws DataProcessingException;

    void addItem(Long bucketId, Long itemId) throws DataProcessingException;

}
