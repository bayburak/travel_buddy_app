package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class signup extends JFrame implements ActionListener{
    String nameSurname;
    String username;
    String email;
    String password;
    boolean visible;
    static Color darkBlue = new Color(19,51,91);
    static Color lightBlue = new Color(174,195,224);
    static Color button = new Color(55,127,188);
    static Font font = new Font("Arial",Font.PLAIN,24);

    public signup() {
        this.visible = false;

        setTitle("Email");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(lightBlue);
        setSize(new Dimension(800,800));

        //Sign Up label 
        JLabel label = new JLabel("Sign Up");
        label.setBounds(250,0,400,150);
        label.setFont(new Font("SansSerif",Font.BOLD,40));
        label.setForeground(darkBlue);
        this.add(label);

        //Name Surname 
        JTextField txtNameSurname = new JTextField("Name Surname");
        txtNameSurname.setBounds(50,130,600,50);
        txtNameSurname.setFont(font);
        this.add(txtNameSurname);

        //Username
        JTextField txtUsername = new JTextField("Username");
        txtUsername.setBounds(50,200,600,50);
        txtUsername.setFont(font);
        this.add(txtUsername);

        //Email
        JTextField txtEmail = new JTextField("Email");
        txtEmail.setBounds(50,270,600,50);
        txtEmail.setFont(font);
        this.add(txtEmail);

        //Password
        JTextField txtPassword = new JTextField("Password");
        txtPassword.setBounds(50,340,600,50);
        txtPassword.setFont(font);
        this.add(txtPassword);

        //Create Button
        RoundedButton create = new RoundedButton("Create new account",20);
        create.setBackground(button);
        create.setForeground(Color.WHITE);
        create.setFont(font);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            }
        });
        create.setBounds(100,450,500,50);
        this.add(create);

        setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
