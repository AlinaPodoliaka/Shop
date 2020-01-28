package internetshop.dao;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User create(User user) throws DataProcessingException;

    Optional<User> get(Long id) throws DataProcessingException;

    Optional<User> update(User user) throws DataProcessingException;

    void delete(Long id) throws DataProcessingException;

    Optional<User> findByLogin(String login) throws DataProcessingException;

    Optional<User> findByToken(String token) throws DataProcessingException;

    List<User> getAllUsers() throws DataProcessingException;

}
