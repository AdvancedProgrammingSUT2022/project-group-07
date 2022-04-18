package Controller;

import Model.User;

import java.util.ArrayList;

public class UserController {
    private static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
    }
}
