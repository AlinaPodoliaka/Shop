package internetshop.dao;

import internetshop.model.Item;

import java.util.Optional;

public interface ItemDao {

    Item create(Item item);

    Optional<Item> get(Long id);

    Optional<Item> update(Item item);

    void delete(Long id);

    void delete(Item item);
}
