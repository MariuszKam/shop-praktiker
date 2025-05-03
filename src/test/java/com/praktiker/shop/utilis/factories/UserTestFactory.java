package com.praktiker.shop.utilis.factories;

import com.praktiker.shop.entities.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserTestFactory {

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();

        users.add(createUser("Adam", "Password123", "adam@mail.com"));
        users.add(createUser("Alice", "Password456", "alice@mail.com"));
        users.add(createUser("Bob", "Password789", "bob@mail.com"));
        users.add(createUser("Eve", "Password012", "eve@mail.com"));
        users.add(createUser("Mallory", "Password345", "mallory@mail.com"));

        return users;
    }

    public static User createUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static User createUser() {
        return createUser("Adam", "Password123", "adam@mail.com");
    }
}
