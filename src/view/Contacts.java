package view;

import controller.Controller;
import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Contacts extends GuiElement {

    private JPanel panel;
    private JPanel panelOnline;
    private JPanel panelOffline;

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
            BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(layout);
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            panel.setOpaque(false);

            // // USER PANEL
            User user = Controller.getInstance().getModel().getCurrentUser();

            JPanel panelHeader = new JPanel();
            panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));
            panelHeader.setBorder(BorderFactory.createTitledBorder("Welcome " + user.getPseudo() + " !"));
            panelHeader.setPreferredSize(new Dimension(380, 100));
            panelHeader.setBackground(Color.WHITE);
            panelHeader.setOpaque(false);

            JPanel panelUser = new JPanel();
            panelUser.setLayout(new GridLayout(1, 3));
            panelUser.setOpaque(false);

            JLabel photo = new JLabel(user.getIcon());
            JLabel name = new JLabel(user.getPseudo());
            name.setFont(new Font("Arial", Font.BOLD, 20));
            JButton disconnect = new JButton("Log out");

            panelUser.add(photo);
            panelUser.add(name);
            panelUser.add(disconnect);
            panelHeader.add(panelUser);
            panel.add(panelHeader);

            /*
            // USER LOCAL
            //User user = Controller.getInstance().getModel().getCurrentUser();
            Message msg = (Message) GUIFactory.createGui(GUIFactory.TypeWindows.MESSAGE, user);

            Controller.getInstance().addUserToChat(user.getPseudo(), msg);

            PanelUserContact panelUser1 = new PanelUserContact(user, msg);
            Controller.getInstance().addUserToPanel(user.getPseudo(), panelUser1);
            //panel.add(panelUser1);
            */

            // // ONLINE
            panelOnline = new JPanel();
            panelOnline.setLayout(new BoxLayout(panelOnline, BoxLayout.Y_AXIS));
            panelOnline.setBorder(BorderFactory.createTitledBorder("Online"));
            panelOnline.setBackground(Color.WHITE);
            panelOnline.setOpaque(false);

            // // OFFLINE
            panelOffline = new JPanel();
            panelOffline.setLayout(new BoxLayout(panelOffline, BoxLayout.Y_AXIS));
            panelOffline.setBorder(BorderFactory.createTitledBorder("Offline"));
            panelOffline.setOpaque(false);

            panel.add(panelOnline);
            panel.add(panelOffline);

            // logout button action
            disconnect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        SoundFX logout = new SoundFX();
                        logout.playSound("logout");
                        TimeUnit.SECONDS.sleep(3);

                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    Controller.getInstance().disconnect();
                }
            });

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

        panelUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelOnline.add(panelUser);
        panel.revalidate();
    }

    public void setUserOffline(PanelUserContact panelUser) {

        panelOnline.remove(panelUser);
        panelUser.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        panelOffline.add(panelUser);

        panel.revalidate();
    }


    public void setUserOnline(PanelUserContact panelUser) {

        panelOffline.remove(panelUser);
        panelUser.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        panelOnline.add(panelUser);

        panel.revalidate();
    }


    @Override
    public void notif(String msg) {

    }

}
