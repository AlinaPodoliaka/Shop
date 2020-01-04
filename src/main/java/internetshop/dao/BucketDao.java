package internetshop.dao;

import internetshop.model.Bucket;
import java.util.Optional;

public interface BucketDao {

    Bucket create(Bucket bucket);

    Optional<Bucket> get(Long id);

    Optional<Bucket> update(Bucket bucket);

    void delete(Long id);

}
