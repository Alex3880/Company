package com.netCracker.dao;

import java.util.List;

public interface CRUDable<T> {
    boolean create(T entity);

    T getById(int id);

    List<T> getAll();

    boolean update(T entity);

    boolean delete(T entity);
}
