package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl dao = new UserDaoJDBCImpl();
//        dao.dropUsersTable();
//        dao.createUsersTable();
//
//        dao.cleanUsersTable();
        dao.saveUser("Тест", "Тестович", (byte) 33);
    }
}
