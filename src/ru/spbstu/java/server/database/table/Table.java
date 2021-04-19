package ru.spbstu.java.server.database.table;

import java.util.List;

public interface Table<T> {
    List<T> get();
    void insert(T entity);
    void update(T entity);
    void delete(Long id);
}
