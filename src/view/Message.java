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

    JEditorPane discussion;
    JScrollPane scrollDiscussion;

    Message message;

    String contentText = "";

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
            message = this;
            this.setTitle(dest.getPseudo() + " - Discussion");
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(700, 450));

            BackgroundPane backgroundPane = new BackgroundPane();
            backgroundPane.setBackground(ImageIO.read(new File("images/bg_message.gif")));
            this.setContentPane(backgroundPane);

            JPanel panel = new JPanel();
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            panel.setLayout(new BorderLayout());
            panel.setOpaque(false);

            JLabel imageUser = new JLabel(dest.getIcon());
            imageUser.setOpaque(false);

            discussion = new JEditorPane();
            discussion.setEditable(false);
            discussion.setContentType("text/html");
            //discussion.setRows(20);
            //discussion.setColumns(30);

            scrollDiscussion = new JScrollPane(discussion, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollDiscussion.setPreferredSize(new Dimension(480, 270));

            final JTextArea sendtext = new JTextArea();
            sendtext.setRows(3);

            JScrollPane scrollSend = new JScrollPane(sendtext);
            scrollSend.setPreferredSize(new Dimension(new Dimension(540, 100)));

            JButton send = new JButton("Send");
            send.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    Controller.getInstance().sendToUser(dest, new network.Message(network.Message.DataType.Text,
                            sendtext.getText(),
                            dest.getPseudo(),
                            Controller.getInstance().getCurrentUser().getPseudo()));
                    contentText += "<font color=\"blue\"> Moi : </font>" + sendtext.getText() + "<br>";
                    discussion.setText("<html>" + contentText + "</html>");
                    sendtext.setText("");
                }
            });

            //JToolBar toolBar = new JToolBar();
            JButton sendFiles = new JButton("Files");
            sendFiles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    int result = fileChooser.showOpenDialog(message);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                        // send file
                        Controller.getInstance().sendFileToUser(selectedFile, dest.getPseudo());
                    }
                }
            });
            //toolBar.add(sendFiles);

            JPanel panel_top_right = new JPanel();
            panel_top_right.setLayout(new BorderLayout());
            panel_top_right.setOpaque(false);
            //panel_top.add(imageUser, BorderLayout.NORTH);
            //panel_top.add(labelDest, BorderLayout.WEST);
            panel_top_right.add(scrollDiscussion, BorderLayout.CENTER);


            JPanel panel_bot = new JPanel();
            panel_bot.setLayout(new BorderLayout());
            panel_bot.setOpaque(false);


            panel_bot.add(scrollSend, BorderLayout.CENTER);
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

    public void addMessage(String data, String source) {
        contentText += "<font color=\"red\">" + source + " : </font>" + data + "<br>";
        discussion.setText("<html>" + contentText + "</html>");
        discussion.revalidate();
        scrollDiscussion.revalidate();
    }
}
