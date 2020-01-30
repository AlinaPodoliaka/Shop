package internetshop.dao.impl.jdbc;

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
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        Long userId = null;
        String query = "INSERT INTO users (name, surname, login, password,token, salt)"
                + " VALUES (?, ?, ?,?,?,?);";
        try (PreparedStatement stmt
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getToken());
            stmt.setBytes(6, user.getSalt());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                userId = resultSet.getLong(1);
                user.setId(userId);
            }
            addRole(user, user.getRoles());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE user_id = (?);";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = setUsersFields(rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user) throws DataProcessingException {
        String query = "UPDATE users SET name = ?, surname = ?, login = ?,"
                + " password = ?, token= ?,salt = ? WHERE user_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getToken());
            stmt.setBytes(6, user.getSalt());
            stmt.setLong(7, user.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user ", e);
        }
        return Optional.of(user);
    }

    @Override
    public void delete(Long id) throws DataProcessingException {
        String query = "DELETE FROM users WHERE user_id = (?);";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user", e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws DataProcessingException {
        String query = "SELECT * FROM users JOIN user_roles ON users.user_id = user_roles.user_id "
                + "JOIN role ON user_roles.role_id = role.role_id WHERE login =?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = setUsersFields(rs);
                Role role = new Role(Role.RoleName.valueOf(rs.getString("role_name")));
                role.setId(rs.getLong("role_id"));
                user.addRole(role);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't findByLogin", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByToken(String token) throws DataProcessingException {
        String query = "SELECT * FROM users JOIN user_roles ON users.user_id = user_roles.user_id"
                + " JOIN role ON user_roles.role_id = role.role_id WHERE token =(?);";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = setUsersFields(rs);
                Role role = new Role(Role.RoleName.valueOf(rs.getString("role_name")));
                role.setId(rs.getLong("role_id"));
                user.addRole(role);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find by token", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() throws DataProcessingException {
        List<User> allUsers = new ArrayList<>();
        String query = "SELECT * FROM users;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = setUsersFields(rs);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users", e);
        }
        return allUsers;
    }

    private User setUsersFields(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setToken(rs.getString("token"));
        user.setSalt(rs.getBytes("salt"));

        return user;
    }

    private void addRole(User user, Set<Role> roles) throws DataProcessingException {
        String query = "INSERT INTO user_roles (user_id) VALUES (?);";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Role role : roles) {
                stmt.setLong(1, user.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user role", e);
        }
    }
}
