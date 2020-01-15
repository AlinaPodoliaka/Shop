package internetshop.service.impl;

import internetshop.dao.Storage;
import internetshop.dao.UserDao;
import internetshop.exceptions.AuthentificationException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {
        user.setToken(getToken());
        return userDao.create(user);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
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

    @Override
    public User login(String login, String password)
            throws AuthentificationException {
        User user = userDao.findByLogin(login)
                .orElseThrow(() -> new AuthentificationException("Incorrect username or password"));
        if (!user.getPassword().equals(password)) {
            throw new AuthentificationException("Incorrect username or password");
        }
        return user;
    }

    @Override
    public Optional<User> getByToken(String token) {
        return userDao.findByToken(token);
    }
}
