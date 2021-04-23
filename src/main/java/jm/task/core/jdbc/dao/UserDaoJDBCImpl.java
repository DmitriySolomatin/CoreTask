package jm.task.core.jdbc.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    private Statement statement;

    public UserDaoJDBCImpl() {
        try {
            Util utilInstance = Util.getInstance();
            connection = utilInstance.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void createUsersTable() {
        try {
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                    "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` TINYINT(3) NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void dropUsersTable() {
        try {
            statement = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS users";
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void removeUserById(long id) {
        try {
            String sql = "DELETE FROM users WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {

            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
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
        } catch (SQLException e) {
            System.out.println(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            statement = connection.createStatement();
            String sql = "TRUNCATE TABLE users";
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void setManualCommit() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit()  {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
