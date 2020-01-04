package internetshop.dao.impl;

import internetshop.dao.Storage;
import internetshop.dao.UserDao;
import internetshop.lib.Dao;
import internetshop.model.User;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(Storage.users
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find user with id " + id)));
    }

    @Override
    public Optional<User> update(User user) {
        Optional<User> updateUser = get(user.getId());
        updateUser.get().setOrders(user.getOrders());

        return updateUser;
    }

    @Override
    public void delete(Long id) {
        Storage.users.removeIf(u -> u.getId().equals(id));
    }
}
