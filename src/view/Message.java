package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by tahel on 08/03/17.
 */
public class Message extends JFrame implements GuiElement {

    public Message() {

        initComponents();
    }

    @Override
    public void initComponents() {
        this.setTitle("Username - Discussion");
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(500,300));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20,20,20,20));
        panel.setLayout(new BorderLayout());

        JLabel labelDest = new JLabel("Username");
        labelDest.setBorder(new EmptyBorder(10, 10, 10, 10));

        Icon icon = new ImageIcon("images/user.png");
        JLabel imageUser = new JLabel(icon);

        JTextArea discussion = new JTextArea();
        discussion.setEditable(false);
        discussion.setRows(20);

        JTextArea sendtext = new JTextArea();
        sendtext.setRows(3);

        JButton send = new JButton("Send");

        JToolBar toolBar = new JToolBar();
        JButton sendEmotes = new JButton("Emotes");
        JButton sendFiles = new JButton("Files");
        toolBar.add(sendEmotes);
        toolBar.add(sendFiles);

        JPanel panel_top = new JPanel();
        panel_top.setLayout(new BorderLayout());

        panel_top.add(imageUser, BorderLayout.NORTH);
        panel_top.add(labelDest, BorderLayout.WEST);
        panel_top.add(discussion, BorderLayout.CENTER);

        JPanel panel_bot = new JPanel();
        panel_bot.setLayout(new BorderLayout());



        panel_bot.add(sendtext, BorderLayout.CENTER);
        panel_bot.add(toolBar, BorderLayout.NORTH);
        panel_bot.add(send, BorderLayout.EAST);



        panel.add(panel_top, BorderLayout.CENTER);
        panel.add(panel_bot, BorderLayout.SOUTH);

        this.add(panel);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
