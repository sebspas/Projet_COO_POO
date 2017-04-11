package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by FatePc on 3/23/2017.
 */
public class PanelUserContact extends JPanel{

    User user;

    JLabel photo;
    JLabel name;
    JLabel status;

    JButton discuter;

    Message msgView;

    public PanelUserContact(User user, Message msgView) {
        this.user = user;
        this.photo = new JLabel(user.getIcon());
        this.name = new JLabel(user.getPseudo());
        this.status = new JLabel(user.getStatus().toString());
        this.discuter = new JButton("Message");
        this.msgView = msgView;
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new GridLayout(1, 4));
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));


        discuter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                msgView.setVisible(true);
            }
        });

        this.add(photo);
        this.add(name);
        this.add(status);
        this.add(discuter);
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }
}
