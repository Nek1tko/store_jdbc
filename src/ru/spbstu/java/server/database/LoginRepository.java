package ru.spbstu.java.server.database;

import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.database.exeption.InvalidCredoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRepository {
    private DBConnection dbConnection;

    public LoginRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void signIn(String login, String password) throws SQLException, InvalidCredoException {
        try (Connection connection = dbConnection.connect()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT PASSWORD " +
                            "FROM USERS " +
                            "WHERE LOGIN = ?")
            ) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String truePassword = resultSet.getString("password");
                        if (!truePassword.equals(password)) {
                            throw new InvalidCredoException("Invalid password!");
                        }
                    } else {
                        throw new InvalidCredoException("Invalid login!");
                    }
                }
            }
        }
    }
}
