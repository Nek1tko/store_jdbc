package ru.spbstu.java.server.connection;

import ru.spbstu.java.server.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleDBConnection extends DBConnection {
    private final String driver = "jdbc:oracle:thin:@";

    public OracleDBConnection(User user, String dataBasePath) {
        super(user, dataBasePath);
    }

    @Override
    public Connection connect() throws SQLException {
        Properties properties = new Properties();
        properties.put("user", user.getName());
        properties.put("password", user.getPassword());

        return DriverManager.getConnection(driver + dataBasePath, properties);
    }
}
