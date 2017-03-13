package view;

import javax.swing.*;

/**
 * Created by tahel on 08/03/17.
 */
public class GUIFactory {

    public GUIFactory() {
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
                case "Login" :  return new Login();

                case "Contacts" : return new Contacts();

                case "Message" : return new Message();

                default :
                    System.out.println("Element de GUI inexistant !");
                    return null;
            }
    }

}
