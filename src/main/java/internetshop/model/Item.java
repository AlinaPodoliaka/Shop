package internetshop.model;

public class Item {

    private static Long idGenerator = 0L;
    private long id;
    private String name;
    private Double price;

    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
        id = idGenerator++;
    }

    public long getId() {

        return id;
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
}
