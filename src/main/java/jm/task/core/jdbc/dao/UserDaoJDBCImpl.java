package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    private Statement statement;

    public void createUsersTable() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                    "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` TINYINT(3) NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            statement.execute(sql);
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS users";
            statement.execute(sql);
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection = Util.getConnection();
            String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            connection = Util.getConnection();
            String sql = "DELETE FROM users WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            connection = Util.getConnection();
            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            connection.commit();
            statement.close();
            while (rs.next()) {
                long id = rs.getInt("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");

                User user = new User(id, name, lastName, age);
                userList.add(user);
            }
            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String sql = "TRUNCATE TABLE users";
            statement.execute(sql);
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
