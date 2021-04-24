package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl dao = new UserDaoHibernateImpl();

        dao.setManualCommit();

        dao.createUsersTable();

        List<User> users = new ArrayList<>();
        users.add(new User("Иван", "Тестович", (byte) 30));
        users.add(new User("Пётр", "Фестович", (byte) 31));
        users.add(new User("Кирилл", "Хестович", (byte) 32));
        users.add(new User("Мефодий", "Цестович", (byte) 33));

        users.stream().forEachOrdered(x -> {
            dao.saveUser(x.getName(), x.getLastName(), x.getAge());
            System.out.printf("User с именем – %s добавлен в базу данных\n", x.getName());
        });

        List<User> currentUsers = new ArrayList<>();
        currentUsers = dao.getAllUsers();
        currentUsers.stream().forEachOrdered(System.out::println);

        dao.cleanUsersTable();
        dao.dropUsersTable();

        dao.commit();
    }
}
