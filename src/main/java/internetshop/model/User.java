package internetshop.model;

public class User {

    private static Long idGenerator = 0L;
    private String name;
    private Long id;

    public User(String name) {
        this.name = name;
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
}
