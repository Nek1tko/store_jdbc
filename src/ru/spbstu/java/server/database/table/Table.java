package ru.spbstu.java.server.database.table;

import java.sql.SQLException;
import java.util.List;

public interface Table<T> {
    List<T> get() throws SQLException;
    void insert(T entity);
    void update(T entity);
    void delete(Long id);
}
