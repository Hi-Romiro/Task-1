package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        // 1. Создание таблицы
        userService.createUsersTable();

        // 2. Добавление пользователей
        userService.saveUser("John", "Doe", (byte) 25);
        userService.saveUser("Jane", "Smith", (byte) 30);
        userService.saveUser("Mike", "Johnson", (byte) 35);
        userService.saveUser("Anna", "Brown", (byte) 28);

        // 3. Получение и вывод всех пользователей
        userService.getAllUsers().forEach(System.out::println);

        // 4. Очистка таблицы
        userService.cleanUsersTable();

        // 5. Удаление таблицы
        userService.dropUsersTable();
    }
}
