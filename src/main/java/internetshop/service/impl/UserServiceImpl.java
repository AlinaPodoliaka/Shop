package internetshop.service.impl;

import internetshop.dao.Storage;
import internetshop.dao.UserDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {

        return userDao.create(user);
    }

    @Override
    public User get(Long id) {

        return userDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Can't find user with id " + id));
    }

    @Override
    public User update(User user) {

        return userDao.update(user)
                .orElseThrow(() -> new NoSuchElementException("Can't find user"));
    }

    @Override
    public void delete(Long id) {

        userDao.delete(id);
    }

    @Override
    public List<User> getAllUsers() {
        return Storage.users;
    }
}
