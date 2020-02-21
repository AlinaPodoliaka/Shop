package internetshop.service.impl;

import internetshop.dao.UserDao;
import internetshop.exceptions.AuthenticationException;
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
        byte[] salt = HashUtil.getSalt();
        String hashPassword = HashUtil.hashPassword(user.getPassword(), salt);
        user.setPassword(hashPassword);
        user.setSalt(salt);
        return userDao.create(user);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User get(Long id) throws DataProcessingException {
        return userDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Can't get user with id "));
    }

    @Override
    public User update(User user) throws DataProcessingException {
        return userDao.update(user);
    }

    @Override
    public void deleteById(Long entityId) throws DataProcessingException {
        userDao.deleteById(entityId);
    }

    @Override
    public void delete(User entity) throws DataProcessingException {
        userDao.deleteById(entity.getId());
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        return userDao.getAll();
    }

    @Override
    public User login(String login, String password)
            throws AuthenticationException, DataProcessingException {
        Optional<User> user = userDao.findByUsername(login);
        if (user.isEmpty() || !user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthenticationException("Incorrect login or password");
        }
        return user.get();
    }

}
