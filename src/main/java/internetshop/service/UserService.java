package internetshop.service;

import internetshop.exceptions.AuthentificationException;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    List<User> getAllUsers();

    User login(String login, String password) throws AuthentificationException;

    Optional<User> getByToken(String token);
}
