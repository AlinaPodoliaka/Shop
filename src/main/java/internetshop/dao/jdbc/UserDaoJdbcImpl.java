package internetshop.dao.jdbc;

import internetshop.dao.UserDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Role;
import internetshop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        String query = "INSERT INTO users (username, password,"
                + " first_name, second_name, salt) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getSecondName());
            statement.setBytes(5, user.getSalt());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                Long userId = rs.getLong(1);
                user.setId(userId);
            }
            addRoles(user, user.getRoles());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user ", e);
        }

        return user;
    }

    @Override
    public Optional<User> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = userSetFields(rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user ", e);
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String query = "UPDATE users SET username = ?, password = ?, first_name = ?,"
                + " second_name = ?, salt = ? WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getSecondName());
            statement.setBytes(5, user.getSalt());
            statement.setLong(6, user.getId());
            statement.executeUpdate();

            Set<Role> oldRoles = getRoles(user);
            Set<Role> newRoles = user.getRoles();

            Set<Role> rolesToDelete = new HashSet<>(oldRoles);
            rolesToDelete.removeAll(newRoles);
            deleteRoles(user, rolesToDelete);

            Set<Role> rolesToAdd = new HashSet<>(newRoles);
            rolesToAdd.removeAll(oldRoles);
            addRoles(user, rolesToAdd);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user ", e);
        }
        return user;
    }

    @Override
    public void deleteById(Long id) throws DataProcessingException {
        get(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void delete(User user) throws DataProcessingException {
        deleteRoles(user, user.getRoles());
        String query = "DELETE FROM users WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user ", e);
        }
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = userSetFields(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users ", e);
        }
        return users;
    }

    @Override
    public Optional<User> findByUsername(String username) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE username = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = userSetFields(rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user ", e);
        }
        return Optional.empty();
    }

    private User userSetFields(ResultSet rs) throws SQLException, DataProcessingException {
        Long userId = rs.getLong("user_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        byte[] salt = rs.getBytes("salt");
        String firstName = rs.getString("first_name");
        String secondName = rs.getString("second_name");
        User user = new User(username);
        user.setPassword(password);
        user.setSalt(salt);
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setId(userId);
        user.setRoles(getRoles(user));
        return user;
    }

    private void addRoles(User user, Set<Role> roles) throws DataProcessingException {
        String query = "INSERT INTO user_roles (user_id, role_id) VALUES"
                + " (?, (SELECT role_id FROM roles WHERE role_name = ?));";
        changeRolesExecute(user, roles, query);
    }

    private void deleteRoles(User user, Set<Role> roles) throws DataProcessingException {
        String query = "DELETE FROM user_roles WHERE user_id = ? AND "
                + "role_id = (SELECT role_id FROM roles WHERE role_name = ?);";
        changeRolesExecute(user, roles, query);
    }

    private void changeRolesExecute(User user, Set<Role> roles, String query)
            throws DataProcessingException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Role role : roles) {
                statement.setLong(1, user.getId());
                statement.setString(2, role.getRoleName().toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user roles ", e);
        }
    }

    private Set<Role> getRoles(User user) throws DataProcessingException {
        Set<Role> roles = new HashSet<>();
        String query = "SELECT role_name, r.role_id FROM roles r JOIN user_roles ur"
                + " ON r.role_id = ur.role_id AND user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String roleName = rs.getString("role_name");
                Long roleId = rs.getLong("r.role_id");
                Role role = new Role(roleName);
                role.setId(roleId);
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user roles ", e);
        }
        return roles;
    }
}
