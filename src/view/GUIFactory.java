package view;

import model.User;

/**
 * Created by tahel on 08/03/17.
 */
public class GUIFactory {

    public enum TypeWindows{
        LOGIN,
        MESSAGE,
        CONTACTS
    }

    public static GuiElement createGui(TypeWindows typeWindows) {
            switch(typeWindows) {
                case LOGIN   :   return new Login();
                case MESSAGE : return new Contacts();
                case CONTACTS: return new Message();
                default :
                    System.out.println("Element de GUI inexistant !");
                    return null;
            }
    }

}
