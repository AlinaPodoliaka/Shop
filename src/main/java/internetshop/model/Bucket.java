package internetshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bucket {

    private static Long idGenerator = 0L;

    private Long id;
    private List<Item> items;
    private Long userId;

    public Bucket(Long userId) {
        this.userId = userId;
        id = idGenerator++;
        items = new ArrayList<>();
    }

    public Bucket() {
        id = idGenerator++;
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

    public void setUserId(Long userId) {

        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bucket)) {
            return false;
        }
        Bucket bucket = (Bucket) o;
        return id.equals(bucket.id)
                && items.equals(bucket.items)
                && userId.equals(bucket.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items, userId);
    }
}

