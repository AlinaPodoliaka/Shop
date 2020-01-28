package internetshop.service;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item create(Item item) throws DataProcessingException;

    Optional<Item> get(Long id) throws DataProcessingException;

    Item update(Item item) throws DataProcessingException;

    void delete(Long id) throws DataProcessingException;

    void delete(Item item) throws DataProcessingException;

    List<Item> getAllItems() throws DataProcessingException;

}
