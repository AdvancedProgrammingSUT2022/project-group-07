package Enum;

public enum MenuName {
    LOGIN_MENU("loginMenu"),
    MAIN_MENU("mainMenu"),
    PROFILE_MENU("profileMenu"),
    GAME_MENU("gameMenu");

    private String menuName;

    MenuName(String menuName) { this.menuName = menuName;}

    public String getMenuName() {
        return menuName;
    }
}
