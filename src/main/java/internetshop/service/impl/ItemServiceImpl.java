package internetshop.service.impl;

import internetshop.dao.ItemDao;
import internetshop.dao.Storage;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Item;
import internetshop.service.ItemService;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Inject
    private static ItemDao itemDao;

    @Override
    public Item create(Item item) {
        return itemDao.create(item);
    }

    @Override
    public Optional<Item> get(Long id) {

        return itemDao.get(id);
    }

    @Override
    public Optional<Item> update(Item item) {

        return itemDao.update(item);
    }

    @Override
    public void delete(Long id) {

        itemDao.delete(id);
    }

    @Override
    public void delete(Item item) {

        itemDao.delete(item);
    }

    @Override
    public List<Item> getAllItems() {

        return Storage.items;
    }
}
