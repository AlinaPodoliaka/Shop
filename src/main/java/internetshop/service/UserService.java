package internetshop.service;

import internetshop.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    List<User> getAllUsers();
}
