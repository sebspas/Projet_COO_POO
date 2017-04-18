package view;

import controller.Controller;
import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

public class Contacts extends GuiElement {

    private JPanel panel;

    public Contacts(){
        super();
        this.initComponents();
    }

    public void initComponents(){
        try {
            this.setTitle("Contacts - ChatWithBoo");
            this.setPreferredSize(new Dimension(400, 800));
            BackgroundPane backgroundPane = new BackgroundPane();
            backgroundPane.setBackground(ImageIO.read(this.getClass().getResourceAsStream("/bg_contacts.jpg")));
            this.setContentPane(backgroundPane);

            panel = new JPanel();
            panel.setLayout(new GridLayout(20, 1));
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            panel.setOpaque(false);

            // USER LOCAL
            User user = Controller.getInstance().getModel().getCurrentUser();
            Message msg = (Message) GUIFactory.createGui(GUIFactory.TypeWindows.MESSAGE, user);

            Controller.getInstance().addUserToChat(user.getPseudo(), msg);

            PanelUserContact panelUser = new PanelUserContact(user, msg);
            Controller.getInstance().addUserToPanel(user.getPseudo(), panelUser);
            panel.add(panelUser);

            this.add(panel);
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {
                    Controller.getInstance().disconnect();
                    System.exit(0);
                }

                @Override
                public void windowClosed(WindowEvent e) {

                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });
            this.pack();
            this.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewUser(User user) {

        Message msg = (Message) GUIFactory.createGui(GUIFactory.TypeWindows.MESSAGE, user);

        Controller.getInstance().addUserToChat(user.getPseudo(), msg);

        PanelUserContact panelUser = new PanelUserContact(user, msg);
        Controller.getInstance().addUserToPanel(user.getPseudo(), panelUser);

        panel.add(panelUser);
        panel.revalidate();
    }

    @Override
    public void notif(String msg) {

    }

}
