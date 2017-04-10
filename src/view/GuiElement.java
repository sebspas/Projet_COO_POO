package view;

import model.User;

import javax.swing.*;

/**
 * Created by tahel on 08/03/17.
 */
public class GuiElement extends JFrame {

    public GuiElement() {
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

    void initComponents() {
    }

    public void notif(String msg){
    }

    public void close(){
        setVisible(false);
        dispose();
    }
}
