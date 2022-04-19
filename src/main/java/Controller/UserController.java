package Controller;

import Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;


    public static void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/Database/database.json")));
            users = new Gson().fromJson(json , new TypeToken<List<User>>(){}.getType());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void saveUsers()  {
        try {
            FileWriter fileWriter = new FileWriter("./src/main/Database/database.json");
            fileWriter.write(new Gson().toJson(users));
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


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
