package view;

import controller.Controller;

import javax.swing.*;

/**
 * Created by tahel on 08/03/17.
 */
public class GUIFactory {

    private Controller controller;

    public GUIFactory(Controller controller) {
        this.controller = controller;

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // not worth my time
            }
        }
    }

    public GuiElement createGui(String gui) {

            switch(gui) {
                case "Login" :  return new Login(controller);

                case "Contacts" : return new Contacts(controller);

                case "Message" : return new Message(controller);

                default :
                    System.out.println("Element de GUI inexistant !");
                    return null;
            }
    }

}
