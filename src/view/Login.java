package view;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Login extends GuiElement {

    private JLabel labelError;

    public Login() {
        super();
        initComponents();
    }

    public void initComponents() {
        try {
            this.setTitle("Login - ChatWithBoo");
            this.setPreferredSize(new Dimension(500,250));

            /*JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("images/bg_test_tracer.jpg"))));
            this.setContentPane(background);
            this.setLayout(new BorderLayout());*/


            BackgroundPane backgroundPane = new BackgroundPane();
            backgroundPane.setBackground(ImageIO.read(new File("images/bg_login.png")));
            this.setContentPane(backgroundPane);

            JPanel panel = new JPanel();
            panel.setBorder(new EmptyBorder(20,20,20,20));
            panel.setLayout(new BorderLayout());
            panel.setOpaque(false);
            panel.setPreferredSize(new Dimension(300, 200));


            JTextField username = new JTextField();
            username.setPreferredSize(new Dimension(270, 15));
            username.setColumns(20);
            Font bigFont = username.getFont().deriveFont(Font.PLAIN, 15f);
            username.setFont(bigFont);

            JButton login = new JButton("Login");
            login.setMargin(new Insets(20,20,20,20));
            login.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(username.getText() != null && username.getText().length() != 0) {
                        Controller.getInstance().buttonLoginClicked(username.getText());
                    } else {
                        notif("<html><font color='red'>Merci de renseigner un pseudo valide !</font></html>");
                    }
                }
            });

            Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
            Border empty = new EmptyBorder(0, 20, 0, 0);
            CompoundBorder border = new CompoundBorder(line, empty);
            username.setBorder(border);



            JLabel labelInfo = new JLabel("<html><font color='white'>Entrer un pseudo :</font></html>");
            labelError = new JLabel(" ");

            JPanel panel_input = new JPanel();
            panel_input.setLayout(new BorderLayout());
            panel_input.setOpaque(false);
            panel_input.add(labelInfo, BorderLayout.NORTH);
            panel_input.add(username, BorderLayout.CENTER);
            panel_input.add(labelError, BorderLayout.SOUTH);


            panel.add(panel_input);
            panel.add(login, BorderLayout.SOUTH);

            this.add(panel);

            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void notif(String msg) {
        labelError.setText(msg);
    }
}
