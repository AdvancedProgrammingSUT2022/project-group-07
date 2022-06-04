package game.Controller.menu;

import game.Controller.UserController;
import game.Enum.MenuName;
import game.Model.User;

import java.util.regex.Matcher;

public class LoginMenuController {

    public String exit() {
        MenuName.setCurrentMenu(MenuName.TERMINATE);
        UserController.saveUsers();
        return "game ended!";
    }

    public String navigateMenu(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("game.Main Menu")) {
            if (UserController.getCurrentUser() != null && UserController.getCurrentUser().isLoggedIn()) {
                MenuName.setCurrentMenu(MenuName.MAIN_MENU);
                return "entered game.Main Menu!";
            }
            else return "please login first";
        }
        else if (menuName.equals("Profile Menu") || menuName.equals("Game Menu") || menuName.equals("Login Menu")) {
            return "menu navigation is not possible";
        }
        return "invalid menu name!";
    }


    public String createUser(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        String nickname = matcher.group("nickname");

        if (ProfileValidation.usernameIsUsed(username))
            return "user with username " + username + " already exists";
        if (ProfileValidation.nicknameIsUsed(nickname))
            return "user with nickname " + nickname + " already exists";
        if (!ProfileValidation.usernameIsValid(username))
            return "invalid username : at least 3 characters and must have at least a letter";
        if (!ProfileValidation.passwordIsValid(password))
            return "invalid password : at least 4 characters and must have at least a capital and a small and a number";
        if (!ProfileValidation.nicknameIsValid(nickname))
            return "invalid nickname : only alphabetical characters!";
        User user = new User(username , password , nickname);
        UserController.addUser(user);
        return "user created successfully";
    }

    public String login(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        User user = UserController.getUser(username , password);
        if (user == null) return "username and password didn't match!";
        else {
            UserController.login(user);
            UserController.setCurrentUser(user);
            return "user logged in successfully!";
        }
    }
}
