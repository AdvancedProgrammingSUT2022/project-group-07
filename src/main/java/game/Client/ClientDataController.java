package game.Client;

import game.Common.Model.User;

public class ClientDataController {

    public static User currentUser ;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
