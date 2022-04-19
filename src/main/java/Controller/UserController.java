package Controller;

import Model.User;

import java.util.ArrayList;

public class UserController {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;


    //TODO save and load users :: Gson


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserController.currentUser = currentUser;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)
            && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public static void login(User user) {
        user.setLoggedIn(true);
    }
}
