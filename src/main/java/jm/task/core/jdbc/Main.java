package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        service.createUsersTable();

        List<User> users = new ArrayList<>();
        users.add(new User("Иван", "Тестович", (byte) 30));
        users.add(new User("Пётр", "Фестович", (byte) 31));
        users.add(new User("Кирилл", "Хестович", (byte) 32));
        users.add(new User("Мефодий", "Цестович", (byte) 33));

        users.stream().forEachOrdered(x -> {
            service.saveUser(x.getName(), x.getLastName(), x.getAge());
            System.out.printf("User с именем – %s добавлен в базу данных\n", x.getName());
        });

        List<User> currentUsers = new ArrayList<>();
        currentUsers = service.getAllUsers();
        currentUsers.stream().forEachOrdered(System.out::println);

        service.cleanUsersTable();
        service.dropUsersTable();

    }
}
