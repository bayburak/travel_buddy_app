package mypackage.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class emailCode extends JFrame implements ActionListener{

    int code;    
    boolean visible;
    static Color darkBlue = new Color(19,51,91);
    static Color lightBlue = new Color(174,195,224);
    static Color button = new Color(55,127,188);

    public emailCode()
    {
        this.visible = true;

        setTitle("Email");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(lightBlue);
        setSize(new Dimension(800,400));

        JLabel label = new JLabel("Enter the code sent to your e-mail to change your password");
        label.setForeground(darkBlue);
        label.setBounds(50,50,800,100);
        label.setFont(new Font("Arial",Font.BOLD,25));
        this.add(label);

        JTextField txtCode = new JTextField("Code");
        txtCode.setBounds(50,150,660,50);
        txtCode.setFont(new Font("Arial",Font.PLAIN,20));
        this.add(txtCode);

        RoundedButton okay = new RoundedButton("Okay",20);
        okay.setBackground(button);
        okay.setForeground(Color.WHITE);
        okay.setFont(new Font("Arial",Font.PLAIN,20));
        okay.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO
            }
        });
        okay.setBounds(300,230,200,50);;
        this.add(okay);

        setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

