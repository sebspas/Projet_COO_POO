package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class Contacts extends JFrame implements GuiElement {

    public Controller controller;

    public Contacts(Controller controller){
        this.controller = controller;

        this.initComponents();
    }

    public JPanel createPanel(String nom, String stat, Icon icon){

        //JPanel panelContact = new JPanel(new BorderLayout());
        JPanel panelContact = new JPanel(new GridLayout(1,4));
        JLabel photo = new JLabel(icon);

        JLabel name = new JLabel(nom);
        JLabel status = new JLabel(stat);

				/*
				panelContact.add(photo, BorderLayout.WEST);
				panelContact.add(name, BorderLayout.CENTER);
				panelContact.add(status, BorderLayout.EAST);
				*/
        panelContact.add(photo);
        panelContact.add(name);
        panelContact.add(status);

        return panelContact;
    }

    public void initComponents(){

        this.setTitle("Contacts - ChatWithBoo");
        this.setPreferredSize(new Dimension(400,800));

        this.setLayout(new GridLayout(8, 1));

        Icon iconContacts = new ImageIcon("images/contact.png");
        this.add(createPanel("username", "online", iconContacts));

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void notif(String msg) {

    }

}
