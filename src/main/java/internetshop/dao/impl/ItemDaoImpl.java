package internetshop.dao.impl;

import internetshop.dao.ItemDao;
import internetshop.dao.Storage;
import internetshop.lib.Dao;
import internetshop.model.Item;

import java.util.List;
import java.util.Optional;

@Dao
public class ItemDaoImpl implements ItemDao {

    @Override
    public Item create(Item item) {

        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {

        return Storage.items
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Item> update(Item item) {

        Optional<Item> updateItem = get(item.getId());
        updateItem.get().setName(item.getName());
        updateItem.get().setPrice(item.getPrice());

        return updateItem;
    }

    @Override
    public void delete(Long id) {

        Storage.items.removeIf(item -> item.getId().equals(id));
    }

    @Override
    public void delete(Item item) {

        Storage.items.removeIf(i -> i.equals(item));
    }

    @Override
    public List<Item> getAllItems() {
        return Storage.items;
    }
}
