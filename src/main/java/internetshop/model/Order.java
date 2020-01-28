package internetshop.model;

import java.util.List;
import java.util.Objects;

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

    public Order(Long id, Long userId, List<Item> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
    }

    public Order(Long orderId, List<Item> items) {
        this.id = orderId;
        this.items = items;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return id.equals(order.id)
                && userId.equals(order.userId)
                && items.equals(order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, items);
    }
}
