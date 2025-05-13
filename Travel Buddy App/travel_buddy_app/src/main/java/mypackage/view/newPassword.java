package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newPassword extends JFrame implements ActionListener {

    String password;    
    boolean visible;
    static Color darkBlue = new Color(19,51,91);
    static Color lightBlue = new Color(174,195,224);
    static Color button = new Color(55,127,188);
    RoundedButton confirm;
    JLabel label;
    JTextField txtCode;

    public newPassword() {
        this.visible = true;

        setTitle("New Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(lightBlue);
        setSize(new Dimension(800,400));

        //Enter code
        label = new JLabel("Enter your new password");
        label.setForeground(darkBlue);
        label.setBounds(250,50,800,100);
        label.setFont(new Font("Arial",Font.BOLD,25));
        this.add(label);

        //Code
        txtCode = new JTextField("New Password");
        txtCode.setBounds(50,150,660,50);
        txtCode.setFont(new Font("Arial",Font.PLAIN,20));
        this.add(txtCode);

        //Confirm button
        confirm = new RoundedButton("Confirm",20);
        confirm.setBackground(button);
        confirm.setForeground(Color.WHITE);
        confirm.setFont(new Font("Arial",Font.PLAIN,20));
        confirm.setBorder(null);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        confirm.setBounds(300,230,200,50);;
        this.add(confirm);

        setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    public JButton getConfirmButton()
    {
        return confirm;
    }

    public JLabel getJLabel()
    {
        return label;
    }

    public JTextField getTxtCode()
    {
        return txtCode;
    }
    
}

  
