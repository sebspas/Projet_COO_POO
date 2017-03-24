package view;

import controller.Controller;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

public class Contacts extends GuiElement {

    private Controller controller;

    public Contacts(Controller controller){
        this.controller = controller;

        this.initComponents();
    }

    public void initComponents(){

        this.setTitle("Contacts - ChatWithBoo");
        this.setPreferredSize(new Dimension(400,800));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(20, 1));
        panel.setBorder(new EmptyBorder(20,20,20,20));

        // USER LOCAL
        User user = controller.getModel().getCurrentUser();
        GUIFactory guiFactory = new GUIFactory(controller);
        Message msg = (Message) guiFactory.createGui("Message", user);
        msg.setDest(user);

        controller.addUserToChat(user.getPseudo(), msg);

        PanelUserContact panelUser = new PanelUserContact(user, msg);
        controller.addUserToPanel(user.getPseudo(), panelUser);
        panel.add(panelUser);

        this.add(panel);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void notif(String msg) {

    }

}
