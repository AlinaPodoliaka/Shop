package internetshop.service;

import internetshop.exceptions.AuthenticationException;
import internetshop.exceptions.DataProcessingException;
import internetshop.model.User;

import java.util.List;

public interface UserService extends GenericService<User, Long> {
    List<User> getAll() throws DataProcessingException;

    User login(String login, String password) throws AuthenticationException,
            DataProcessingException;
}
