package internetshop.service;

import internetshop.exceptions.DataProcessingException;

import java.util.List;

public interface GenericService<T, I> {
    T create(T entity) throws DataProcessingException;

    T get(I entityId) throws DataProcessingException;

    T update(T entity) throws DataProcessingException;

    void deleteById(I entityId) throws DataProcessingException;

    void delete(T entity) throws DataProcessingException;

    List<T> getAll() throws DataProcessingException;
}
