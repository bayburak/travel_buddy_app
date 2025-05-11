package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame implements ActionListener {
    String username;
    String password;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    static Color darkBlue = new Color(19,51,91);
    static Color lightBlue = new Color(174,195,224);
    static Color button = new Color(55,127,188);

    public login() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        }

        setTitle("Travel Buddy Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(darkBlue);

        //Travel Buddy
        JPanel titlePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(lightBlue);
        JLabel titleLabel = new JLabel("Travel Buddy",SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        titleLabel.setForeground(darkBlue);
        titlePanel.add(titleLabel);
        titlePanel.setBounds(screenWidth/2-300,100,600,100);
        this.add(titlePanel);

        //A Travel Journal App
        JLabel titleLabel2 = new JLabel("A Travel Journal App");
        titleLabel2.setFont(new Font("SansSerif", Font.PLAIN, 40));
        titleLabel2.setBounds(screenWidth/2-184,135,500,200);
        titleLabel2.setForeground(lightBlue);
        this.add(titleLabel2);

        //Username
        JTextField txtUsername = new JTextField("Username") {};
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 24));
        txtUsername.setBackground(new Color(253, 244, 220));        
        txtUsername.setBounds(screenWidth/2-150, screenHeight/2-100, 300, 50);
        this.add(txtUsername);
        this.repaint();

        //Password
        JTextField txtPassword = new JTextField("Password") {};
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 24));
        txtPassword.setBackground(new Color(253, 244, 220));        
        txtPassword.setBounds(screenWidth/2-150, screenHeight/2-30, 300, 50);
        this.add(txtPassword);
        this.repaint();

        //Sign In Button
        RoundedButton signIn = new JButton("Sign in",20);
        signIn.setFont(new Font("Arial", Font.PLAIN, 24));
        signIn.setForeground(Color.WHITE);
        signIn.setBackground(button);
        signIn.setBounds(screenWidth/2-150, screenHeight/2+40, 300, 50);
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        this.add(signIn);
        this.repaint();

        //Sign Up Button
        JLabel label3 = new JLabel("Don't have an account?");
        label3.setFont(new Font("SansSerif", Font.PLAIN, 30));
        label3.setBounds(screenWidth/2-320,screenHeight-250,500,200);
        label3.setForeground(Color.WHITE);
        this.add(label3);
        RoundedButton signUp = new JButton("Sign up",20);
        signUp.setFont(new Font("Arial", Font.PLAIN, 24));
        signUp.setForeground(Color.WHITE);
        signUp.setBackground(button);
        signUp.setBounds(screenWidth/2+100, screenHeight-170, 300, 50);
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        this.add(signUp);
        this.repaint();

        //Forget Password 
        JLabel label4 = new JLabel("Forget your password? Click");
        label4.setFont(new Font("SansSerif", Font.PLAIN, 30));
        label4.setBounds(screenWidth/2-320,screenHeight-350,500,200);
        label4.setForeground(Color.WHITE);
        this.add(label4);
        RoundedButton here = new JButton("Here",20);
        here.setFont(new Font("Arial", Font.PLAIN, 24));
        here.setForeground(Color.WHITE);
        here.setBackground(button);
        here.setBounds(screenWidth/2+100, screenHeight-270, 300, 50);
        here.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        this.add(here);
        this.repaint();

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
