package ru.netology.util;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import ru.netology.domain.testUser;

public class DataHelper {
    private DataHelper() {
    }

    private static final Faker faker = new Faker();
    private static final String username = faker.name().username();
    private static final String password = faker.internet().password();

    private static String user(boolean active) {
        return active ? new Gson().toJson(new testUser(username, password, "active")) : new Gson().toJson(new testUser(username, password, "blocked"));
    }

    public static String activeUser() {
        return user(true);
    }

    public static String inactiveUser() {
        return user(false);
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}