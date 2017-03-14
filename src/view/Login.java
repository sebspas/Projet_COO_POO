package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements GuiElement {

    private Controller controller;

    private JLabel labelError;

    public Login(Controller controller) {
        this.controller = controller;
        initComponents();
    }

    public void initComponents() {
        this.setTitle("Login - ChatWithBoo");
        this.setPreferredSize(new Dimension(400,200));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20,20,20,20));
        panel.setLayout(new BorderLayout());



        JTextField username = new JTextField();

        JButton login = new JButton("Login");
        login.setMargin(new Insets(20,20,20,20));
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.buttonLoginClicked(username.getText());
            }
        });

        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border empty = new EmptyBorder(0, 20, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        username.setBorder(border);



        JLabel labelInfo = new JLabel("Entrer un pseudo :");
        labelError = new JLabel(" ");

        JPanel panel_input = new JPanel();
        panel_input.setLayout(new BorderLayout());
        panel_input.add(labelInfo, BorderLayout.NORTH);
        panel_input.add(username, BorderLayout.CENTER);
        panel_input.add(labelError, BorderLayout.SOUTH);


        panel.add(panel_input);
        panel.add(login, BorderLayout.SOUTH);

        this.add(panel);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void notif(String msg) {
        labelError.setText(msg);
    }
}
