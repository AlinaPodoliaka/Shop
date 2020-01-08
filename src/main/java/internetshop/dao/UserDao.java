package internetshop.dao;

import internetshop.model.User;

import java.util.Optional;

public interface UserDao {

    User create(User user);

    Optional<User> get(Long id);

    Optional<User> update(User user);

    void delete(Long id);
}
