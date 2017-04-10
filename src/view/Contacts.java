package view;

import controller.Controller;
import model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Contacts extends GuiElement {

    private JPanel panel;

    public Contacts(){
        super();
        this.initComponents();
    }

    public void initComponents(){

        this.setTitle("Contacts - ChatWithBoo");
        this.setPreferredSize(new Dimension(400,800));

        panel = new JPanel();
        panel.setLayout(new GridLayout(20, 1));
        panel.setBorder(new EmptyBorder(20,20,20,20));

        // USER LOCAL
        User user = Controller.getInstance().getModel().getCurrentUser();
        Message msg = (Message) GUIFactory.createGui(GUIFactory.TypeWindows.MESSAGE);
        msg.setDest(user);

        Controller.getInstance().addUserToChat(user.getPseudo(), msg);

        PanelUserContact panelUser = new PanelUserContact(user, msg);
        Controller.getInstance().addUserToPanel(user.getPseudo(), panelUser);
        panel.add(panelUser);

        this.add(panel);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public void addNewUser(User user) {

        Message msg = (Message) GUIFactory.createGui(GUIFactory.TypeWindows.MESSAGE);
        msg.setDest(user);

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
