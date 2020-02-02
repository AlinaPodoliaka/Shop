package internetshop.service.impl;

import internetshop.dao.UserDao;
import internetshop.exceptions.AuthentificationException;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;
import internetshop.util.HashUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) throws DataProcessingException {
        user.setToken(getToken());
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
        return userDao.create(user);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User get(Long id) throws DataProcessingException {

        return userDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Can't find user with id " + id));
    }

    @Override
    public User update(User user) throws DataProcessingException {
        return userDao.update(user)
                .orElseThrow(() -> new NoSuchElementException("Can't update user"));
    }

    @Override
    public void delete(Long id) throws DataProcessingException {

        userDao.delete(id);
    }

    @Override
    public List<User> getAllUsers() throws DataProcessingException {
        return userDao.getAllUsers();
    }

    @Override
    public User login(String login, String password)
            throws AuthentificationException, DataProcessingException {
        Optional<User> user = userDao.findByLogin(login);
        if (user.isEmpty() || !user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthentificationException("Incorrect username or password");
        }
        return user.get();
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        return userDao.findByToken(token);
    }
}
