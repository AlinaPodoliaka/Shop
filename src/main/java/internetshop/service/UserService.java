package internetshop.service;

import internetshop.exceptions.AuthentificationException;
import internetshop.exceptions.DataProcessingException;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user) throws DataProcessingException;

    User get(Long id) throws DataProcessingException;

    User update(User user) throws DataProcessingException;

    void delete(Long id) throws DataProcessingException;

    List<User> getAllUsers() throws DataProcessingException;

    User login(String login, String password)
            throws AuthentificationException, DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;

}
