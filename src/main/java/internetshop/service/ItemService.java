package internetshop.service;

import internetshop.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item create(Item item);

    Optional<Item> get(Long id);

    Item update(Item item);

    void delete(Long id);

    void delete(Item item);

    List<Item> getAllItems();

}
