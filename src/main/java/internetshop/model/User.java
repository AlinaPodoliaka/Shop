package internetshop.model;

public class User {

    private static Long idGenerator = 0L;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String token;
    private Long id;

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
}
