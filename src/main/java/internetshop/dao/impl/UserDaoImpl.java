package internetshop.dao.impl;

import internetshop.dao.Storage;
import internetshop.dao.UserDao;
import internetshop.lib.Dao;
import internetshop.model.User;

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

        return Storage.users
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> update(User user) {

        Optional<User> updateUser = get(user.getId());
        updateUser.get().setName(user.getName());

        return updateUser;
    }

    @Override
    public void delete(Long id) {

        Storage.users.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Storage.users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public Optional<User> findByToken(String token) {
        return Storage.users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }
}
