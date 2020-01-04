package internetshop.service.impl;

import internetshop.dao.UserDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {

        return userDao.create(user);
    }

    @Override
    public Optional<User> get(Long id) {

        return userDao.get(id);
    }

    @Override
    public Optional<User> update(User user) {

        return userDao.update(user);
    }

    @Override
    public void delete(Long id) {

        userDao.delete(id);
    }

}
