package test;

import view.GUIFactory;
import view.GuiElement;

/**
 * Created by tahel on 08/03/17.
 */
public class testLoginGUi {

    public static void main(String[] args) {

        GUIFactory factory = new GUIFactory();

        GuiElement login = factory.createGui("login");
        GuiElement message = factory.createGui("Message");
    }
}
