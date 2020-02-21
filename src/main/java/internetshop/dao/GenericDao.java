package internetshop.dao;

import internetshop.exceptions.DataProcessingException;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, N> {
    T create(T entity) throws DataProcessingException;

    Optional<T> get(N entityN) throws DataProcessingException;

    T update(T entity) throws DataProcessingException;

    void deleteById(N entityN) throws DataProcessingException;

    void delete(T entity) throws DataProcessingException;

    List<T> getAll() throws DataProcessingException;
}
