package internetshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Long id;
    private List<Item> items;
    private Long userId;

    public Order(User user) {
        items = new ArrayList<>();
        userId = user.getId();
    }

    public Order(Long userId) {
        items = new ArrayList<>();
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id)
                && Objects.equals(items, order.items)
                && userId.equals(order.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items, userId);
    }
}
