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
import java.lang.reflect.Executable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by tahel on 08/03/17.
 */
public class Message extends GuiElement {

    private User dest;

    JTextArea discussion;

    public Message(User user) {
        super();
        this.dest = user;
        initComponents();
    }

    public User getDest() {
        return dest;
    }

    public void setDest(User dest) {
        this.dest = dest;
    }

    @Override
    public void initComponents() {
        try {
            this.setTitle(dest.getPseudo() + " - Discussion");
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(500, 300));

            BackgroundPane backgroundPane = new BackgroundPane();
            backgroundPane.setBackground(ImageIO.read(new File("images/bg_message.gif")));
            this.setContentPane(backgroundPane);

            JPanel panel = new JPanel();
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            panel.setLayout(new BorderLayout());
            panel.setOpaque(false);
            panel.setPreferredSize(new Dimension(470, 280));

            JLabel imageUser = new JLabel(dest.getIcon());
            imageUser.setOpaque(false);

            discussion = new JTextArea();
            discussion.setEditable(false);
            discussion.setRows(20);
            discussion.setColumns(30);

            JTextArea sendtext = new JTextArea();
            sendtext.setRows(3);

            JButton send = new JButton("Send");
            send.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    Controller.getInstance().sendToUser(dest, new network.Message(network.Message.DataType.Text,
                            sendtext.getText(),
                            dest.getPseudo(),
                            Controller.getInstance().getCurrentUser().getPseudo()));
                    discussion.append(Controller.getInstance().getCurrentUser().getPseudo() + " : " + sendtext.getText() + "\n");
                    sendtext.setText("");
                }
            });

            //JToolBar toolBar = new JToolBar();
            JButton sendFiles = new JButton("Files");
            //toolBar.add(sendFiles);

            JPanel panel_top_right = new JPanel();
            panel_top_right.setLayout(new BorderLayout());
            panel_top_right.setOpaque(false);
            //panel_top.add(imageUser, BorderLayout.NORTH);
            //panel_top.add(labelDest, BorderLayout.WEST);
            panel_top_right.add(discussion, BorderLayout.CENTER);


            JPanel panel_bot = new JPanel();
            panel_bot.setLayout(new BorderLayout());
            panel_bot.setOpaque(false);


            panel_bot.add(sendtext, BorderLayout.CENTER);
            panel_bot.add(sendFiles, BorderLayout.NORTH);
            panel_bot.add(send, BorderLayout.EAST);


            panel.add(panel_top_right, BorderLayout.EAST);
            panel.add(imageUser, BorderLayout.WEST);
            panel.add(panel_bot, BorderLayout.SOUTH);

            this.add(panel);

            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {
                    Message.super.close();
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
            this.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notif(String msg) {

    }

    public static void main(String[] args) {
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
        try {
            User user = new User("Coucou", Inet4Address.getLocalHost());

            Message msg = new Message(user);
            msg.setVisible(true);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void addMessage(String data) {
        discussion.append(dest.getPseudo() + " : " + data + "\n");
        discussion.revalidate();
    }
}
