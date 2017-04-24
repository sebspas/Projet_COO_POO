package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelUserContact extends JPanel{

    User user;

    Icon photoAlert;
    JLabel photo;

    Icon online;
    Icon offline;
    Icon busy;
    Icon away;
    Icon invisible;

    JLabel name;
    JLabel status;

    JButton discuter;

    Message msgView;

    public PanelUserContact(User user, Message msgView) {
        this.user = user;
        this.photo = new JLabel(user.getIcon());
        this.name = new JLabel(user.getPseudo());
        this.discuter = new JButton("Talk !");
        this.msgView = msgView;

        //this.status = new JLabel(user.getStatus().toString());
        this.online = new ImageIcon(this.getClass().getResource("/online.png"));
        this.offline = new ImageIcon(this.getClass().getResource("/offline.png"));
        this.busy = new ImageIcon(this.getClass().getResource("/busy.png"));
        this.away = new ImageIcon(this.getClass().getResource("/away.png"));
        this.invisible = new ImageIcon(this.getClass().getResource("/invisible.png"));
        this.status = new JLabel();

        this.photoAlert = new ImageIcon(this.getClass().getResource("/newmsg.gif"));
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new GridLayout(1, 4));
        this.setPreferredSize(new Dimension(360,60));
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        this.setBackground(Color.WHITE);
        this.setOpaque(false);


        discuter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                msgView.setVisible(true);
                disableAlert();
            }
        });

        this.setStatus();

        this.add(photo);
        this.add(name);
        this.add(status);
        this.add(discuter);
    }

    public void alertMsg(){
        this.photo.setIcon(this.photoAlert);
    }

    public void disableAlert(){
        this.photo.setIcon(user.getIcon());
    }

    public void enableTalk(){
        discuter.setEnabled(true);
    }

    public void disableTalk(){
        discuter.setEnabled(false);
    }

    public void setStatus() {
        String status= user.getStatus().toString();

        switch (status){
            case "Online":
                this.status.setIcon(online);
                break;
            case "Offline":
                this.status.setIcon(offline);
                break;
            case "Busy":
                this.status.setIcon(busy);
                break;
            case "Away":
                this.status.setIcon(away);
                break;
            default:
                this.status.setIcon(invisible);
                break;
        }
    }
}
