package view;

import model.User;

public class GUIFactory {

    public enum TypeWindows{
        LOGIN,
        MESSAGE,
        CONTACTS
    }

    public static GuiElement createGui(TypeWindows typeWindows, User user) {
        return switchCreate(typeWindows, user);
    }

    public static GuiElement createGui(TypeWindows typeWindows) {
        return switchCreate(typeWindows, null);
    }

    private static GuiElement switchCreate(TypeWindows typeWindows, User user) {
        switch(typeWindows) {
            case LOGIN   :   return new Login();
            case CONTACTS : return new Contacts();
            case MESSAGE: return new Message(user);
            default :
                System.out.println("Element de GUI inexistant !");
                return null;
        }
    }

}
