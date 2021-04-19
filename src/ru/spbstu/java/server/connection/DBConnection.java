package ru.spbstu.java.server.connection;

import ru.spbstu.java.server.User;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBConnection {
    protected User user;
    protected String dataBasePath;

    public DBConnection(User user, String dataBasePath) {
        this.user = user;
        this.dataBasePath = dataBasePath;
    }

    public abstract Connection connect() throws SQLException;
}
