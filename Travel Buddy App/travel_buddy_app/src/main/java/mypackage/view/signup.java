package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class signup extends JFrame implements ActionListener
{
    public JTextField txtNameSurname;
    public JTextField txtUsername;
    public JTextField txtEmail;
    public JPasswordField txtPassword;
    public JButton create;
    public JButton goToLogin;

    private static final Color DARK_BLUE = new Color(19, 51, 91);
    private static final Color LIGHT_BLUE = new Color(174, 195, 224);
    private static final Color BUTTON_BLUE = new Color(55, 127, 188);
    private static final Font  FONT_24 = new Font("Arial", Font.PLAIN, 24);

    public signup()
    {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(LIGHT_BLUE);
        setSize(new Dimension(800, 800));

        JLabel heading = new JLabel("Sign Up");
        heading.setFont(new Font("SansSerif", Font.BOLD, 40));
        heading.setForeground(DARK_BLUE);
        heading.setBounds(250, 0, 400, 150);
        add(heading);

        txtNameSurname = new JTextField("Name Surname");
        txtNameSurname.setBounds(50, 130, 600, 50);
        txtNameSurname.setFont(FONT_24);
        add(txtNameSurname);

        txtUsername = new JTextField("Username");
        txtUsername.setBounds(50, 200, 600, 50);
        txtUsername.setFont(FONT_24);
        add(txtUsername);

        txtEmail = new JTextField("Email");
        txtEmail.setBounds(50, 270, 600, 50);
        txtEmail.setFont(FONT_24);
        add(txtEmail);

        txtPassword = new JPasswordField("Password");
        txtPassword.setBounds(50, 340, 600, 50);
        txtPassword.setFont(FONT_24);
        add(txtPassword);

        create = new RoundedButton("Create new account", 20);
        create.setBackground(BUTTON_BLUE);
        create.setForeground(Color.WHITE);
        create.setFont(FONT_24);
        create.setBounds(100, 450, 500, 50);
        create.addActionListener(this);
        add(create);

        goToLogin = new RoundedButton("Back to Login", 20);
        goToLogin.setBackground(BUTTON_BLUE);
        goToLogin.setForeground(Color.WHITE);
        goToLogin.setFont(FONT_24);
        goToLogin.setBounds(100, 520, 500, 50);
        goToLogin.addActionListener(this);
        add(goToLogin);

        setVisible(false);
    }

    @Override public void actionPerformed(ActionEvent e)
    { 

    }
}
