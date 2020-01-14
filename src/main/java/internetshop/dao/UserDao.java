package internetshop.dao;

import internetshop.exceptions.AuthentificationException;
import internetshop.model.User;

import java.util.Optional;

public interface UserDao {

    User create(User user);

    Optional<User> get(Long id);

    Optional<User> update(User user);

    void delete(Long id);

    User login(String login, String password) throws AuthentificationException;

    Optional<User> findByLogin(String login);

    Optional<User> findByToken(String token);
}
