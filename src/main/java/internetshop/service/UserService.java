package internetshop.service;

import internetshop.model.User;

import java.util.Optional;

public interface UserService {

    User create(User user);

    Optional<User> get(Long id);

    Optional<User> update(User user);

    void delete(Long id);
}
