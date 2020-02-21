package internetshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bucket {
    private Long id;
    private List<Item> items;
    private Long userId;

    public Bucket(User user) {
        items = new ArrayList<>();
        userId = user.getId();
    }

    public Bucket(Long userId) {
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

    public Long getUserId() {
        return userId;
    }

    public void addItem(Item item) {
        if (items.contains(item)) {
            System.out.println(item.getName() + " already in bucket");
            return;
        }
        items.add(item);
    }

    public void addItems(List<Item> items) {
        this.items.removeAll(items);
        this.items.addAll(items);
    }

    public void deleteItem(Item item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bucket bucket = (Bucket) o;
        return Objects.equals(id, bucket.id)
                && Objects.equals(items, bucket.items)
                && userId.equals(bucket.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items, userId);
    }
}
