package game.Common.Enum;

public enum MenuName {
    LOGIN_MENU,
    MAIN_MENU,
    PROFILE_MENU,
    GAME_MENU,
    TERMINATE;

    private static MenuName currentMenu = LOGIN_MENU; // vase shorooe bazi!

    public static MenuName getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(MenuName currentMenu) {
        MenuName.currentMenu = currentMenu;
    }
}
