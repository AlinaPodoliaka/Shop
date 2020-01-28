package internetshop.model;

import java.util.Objects;

public class Item {

    private static Long idGenerator = 0L;
    private Long id;
    private String name;
    private Double price;

    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
        id = idGenerator++;
    }

    public Item() {
        id = idGenerator++;
    }

    public Item(Long id) {
        this.id = id;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(Double price) {

        this.price = price;
    }

    @Override
    public String toString() {

        return "Item{" + "id=" + id + ", name='" + name + '\''
                + ", price=" + price + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id.equals(item.id)
                && name.equals(item.name)
                && price.equals(item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
