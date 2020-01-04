package internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static Long idGenerator = 0L;
    private String name;
    private Long id;
    private List<Order> orders;

    public User(String name) {
        this.name = name;
        id = idGenerator++;
        orders = new ArrayList<>();
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

    public List<Order> getOrders() {

        return orders;
    }

    public void setOrders(List<Order> orders) {

        this.orders = orders;
    }
}
