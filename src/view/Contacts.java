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
            backgroundPane.setBackground(ImageIO.read(new File("images/bg_contacts.jpg")));
            this.setContentPane(backgroundPane);

            panel = new JPanel();
            GridLayout layout = new GridLayout(20, 1);
            panel.setLayout(layout);
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            panel.setOpaque(false);
            layout.setVgap(10);

            /*
            // USER LOCAL
            User user = Controller.getInstance().getModel().getCurrentUser();
            Message msg = (Message) GUIFactory.createGui(GUIFactory.TypeWindows.MESSAGE, user);

            Controller.getInstance().addUserToChat(user.getPseudo(), msg);

            PanelUserContact panelUser = new PanelUserContact(user, msg);
            Controller.getInstance().addUserToPanel(user.getPseudo(), panelUser);
            panel.add(panelUser);
            */

            // // USER PANEL
            JPanel panelUser = new JPanel();
            panelUser.setLayout(new GridLayout(1, 3));
            panelUser.setBorder(new EmptyBorder(5, 10, 5, 10));
            panelUser.setPreferredSize(new Dimension(400, 70));
            panelUser.setOpaque(false);

            User user = Controller.getInstance().getModel().getCurrentUser();
            JLabel photo = new JLabel(user.getIcon());
            JLabel name = new JLabel(user.getPseudo());
            name.setFont(new Font("Arial", Font.BOLD, 20));
            //JLabel status = new JLabel(user.getStatus().toString());
            //status.setFont(new Font("Arial", Font.PLAIN, 20));
            JButton disconnect = new JButton("Déconnexion");

            disconnect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panelUser.setOpaque(true);
                    panelUser.setBackground(Color.RED);
                    //tatus.setText("Offline");
                }
            });

            panelUser.add(photo);
            panelUser.add(name);
            //panelUser.add(status);
            panelUser.add(disconnect);
            // //

            // // ONLINE
            JPanel panelOnline = new JPanel();
            JLabel online = new JLabel("ONLINE");
            online.setFont(new Font("Arial", Font.BOLD, 14));
            panelOnline.setBorder(new EmptyBorder(0, 0, 0, 0));
            panelOnline.setPreferredSize(new Dimension(100, 20));
            panelOnline.setBackground(Color.WHITE);
            panelOnline.add(online);

            // // OFFLINE
            JPanel panelOffline = new JPanel();
            JLabel offline = new JLabel("OFFLINE");
            offline.setFont(new Font("Arial", Font.BOLD, 14));
            panelOffline.setBorder(new EmptyBorder(0, 0, 0, 0));
            panelOffline.setPreferredSize(new Dimension(100, 20));
            panelOffline.add(offline);
            // //

            panel.add(panelUser);
            panel.add(panelOnline);
            panel.add(panelOffline);

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
