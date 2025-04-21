package org.consoleapp;

import org.consoleapp.dao.UserDao;
import org.consoleapp.dao.UserDaoImpl;
import org.consoleapp.model.User;
import org.consoleapp.util.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl(HibernateUtil.getSessionFactory());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Выберите действие: ===");
            System.out.println("1. Добавить пользователя");
            System.out.println("2. Найти пользователя по ID");
            System.out.println("3. Посмотреть всех пользователей");
            System.out.println("4. Обновить пользоваетля");
            System.out.println("5. Удалить пользователя");
            System.out.println("6. Выход");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Имя: ");
                        String name = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Age: ");
                        int age = Integer.parseInt(scanner.nextLine());
                        User user = new User(name, email, age);
                        userDao.save(user);
                        break;
                    case "2":
                        System.out.print("User ID: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        User choseenUser = userDao.findById(id);
                        System.out.println(choseenUser);
                        break;
                    case "3":
                        List<User> users = userDao.findAll();
                        users.forEach(System.out::println);
                        break;
                    case "4":
                        System.out.println("Обновить пользователя с ID: ");
                        Long updateId = Long.parseLong(scanner.nextLine());
                        User existingUser = userDao.findById(updateId);
                        if (existingUser != null) {
                            System.out.print("Новое имя: ");
                            existingUser.setName((scanner.nextLine()));
                            System.out.println("Новый Email: ");
                            existingUser.setEmail(scanner.nextLine());
                            System.out.print("Новый возраст");
                            existingUser.setAge(Integer.parseInt(scanner.nextLine()));
                            userDao.update(existingUser);
                        } else {
                            System.out.println("Пользователь не найден");
                        }
                        break;
                    case "5":
                        System.out.print("Удалить пользователя с ID");
                        Long deleteId = Long.parseLong(scanner.nextLine());
                        User userToDelete = userDao.findById(deleteId);
                        if (userToDelete != null) {
                            userDao.delete(userToDelete);
                        } else {
                            System.out.println("Пользователь не найден");
                        }

                        break;
                    case "6":
                        HibernateUtil.shutdown();
                        return;
                    default:
                        System.out.println("Неверный ввод. Попробуйте снова");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
