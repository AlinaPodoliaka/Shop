package internetshop.dao;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByUsername(String username) throws DataProcessingException;
}
