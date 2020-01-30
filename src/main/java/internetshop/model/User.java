package internetshop.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

    private static Long idGenerator = 0L;
    private String name;
    private String surname;
    private String login;
    private String password;
    private byte[] salt;
    private String token;
    private Long id;
    private Set<Role> roles = new HashSet<>();

    public User(String name) {
        this.name = name;
        id = idGenerator++;
    }

    public User() {
        id = idGenerator++;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Long getId() {

        return id;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return name.equals(user.name)
                && surname.equals(user.surname)
                && login.equals(user.login)
                && password.equals(user.password)
                && token.equals(user.token)
                && id.equals(user.id)
                && roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, login, password, token, id, roles);
    }
}
