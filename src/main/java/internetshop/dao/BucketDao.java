package internetshop.dao;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Bucket;
import internetshop.model.User;

import java.util.Optional;

public interface BucketDao extends GenericDao<Bucket, Long> {
    Optional<Bucket> getByUser(User user) throws DataProcessingException;
}
