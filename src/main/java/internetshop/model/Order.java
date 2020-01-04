package internetshop.model;

import java.util.List;

public class Order {

    private static Long idGenerator = 0L;
    private Long id;
    private Long userId;
    private List<Item> items;

    public Order(List<Item> items, Long userId) {
        this.userId = userId;
        this.items = items;
        id = idGenerator++;
    }

    public Long getUserId() {

        return userId;
    }

    public Long getId() {

        return id;
    }

    public List<Item> getItems() {

        return items;
    }

    public void setItems(List<Item> items) {

        this.items = items;
    }

    @Override
    public String toString() {

        return "Order{" + "id=" + id + ", userId=" + userId + ", items=" + items + '}';
    }
}
