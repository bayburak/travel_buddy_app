package mypackage.view;

import javax.swing.*;
import java.awt.*;

public class login extends JFrame
{
    public JTextField txtUsername;
    public JPasswordField txtPassword;
    public JButton signIn;
    public JButton signUp;
    public JButton forgotBtn;

    private static final Color DARK_BLUE = new Color(19, 51, 91);
    private static final Color LIGHT_BLUE = new Color(174, 195, 224);
    private static final Color BUTTON_BLUE = new Color(55, 127, 188);
    private static final Font  FONT_24 = new Font("Arial", Font.PLAIN, 24);

    public login()
    {
        setTitle("Travel Buddy Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(DARK_BLUE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        int w = scr.width;
        int h = scr.height;

        JPanel titleBanner = new JPanel()
        {
            @Override protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(LIGHT_BLUE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        titleBanner.setLayout(new BorderLayout());
        titleBanner.setOpaque(false);
        JLabel titleLabel = new JLabel("Travel Buddy", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        titleLabel.setForeground(DARK_BLUE);
        titleBanner.add(titleLabel);
        titleBanner.setBounds(w / 2 - 300, 100, 600, 100);
        add(titleBanner);

        JLabel subtitle = new JLabel("A Travel Journal App");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 40));
        subtitle.setForeground(LIGHT_BLUE);
        subtitle.setBounds(w / 2 - 184, 135, 500, 200);
        add(subtitle);

        txtUsername = new JTextField("Username");
        txtUsername.setFont(FONT_24);
        txtUsername.setBackground(new Color(253, 244, 220));
        txtUsername.setBounds(w / 2 - 150, h / 2 - 100, 300, 50);
        add(txtUsername);

        txtPassword = new JPasswordField("Password");
        txtPassword.setFont(FONT_24);
        txtPassword.setBackground(new Color(253, 244, 220));
        txtPassword.setBounds(w / 2 - 150, h / 2 - 30, 300, 50);
        add(txtPassword);

        signIn = new RoundedButton("Sign in", 20);
        signIn.setFont(FONT_24);
        signIn.setForeground(Color.WHITE);
        signIn.setBackground(BUTTON_BLUE);
        signIn.setBounds(w / 2 - 150, h / 2 + 40, 300, 50);
        add(signIn);

        JLabel footer = new JLabel("Don't have an account?");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 30));
        footer.setForeground(Color.WHITE);
        footer.setBounds(w / 2 - 320, h - 250, 500, 200);
        add(footer);

        signUp = new RoundedButton("Sign up", 20);
        signUp.setFont(FONT_24);
        signUp.setForeground(Color.WHITE);
        signUp.setBackground(BUTTON_BLUE);
        signUp.setBounds(w / 2 + 100, h - 170, 300, 50);
        add(signUp);

        JLabel forgotLbl = new JLabel("Forgot your password? Click");
        forgotLbl.setFont(new Font("SansSerif", Font.PLAIN, 30));
        forgotLbl.setForeground(Color.WHITE);
        forgotLbl.setBounds(w / 2 - 320, h - 350, 500, 200);
        add(forgotLbl);

        forgotBtn = new RoundedButton("Here", 20);
        forgotBtn.setFont(FONT_24);
        forgotBtn.setForeground(Color.WHITE);
        forgotBtn.setBackground(BUTTON_BLUE);
        forgotBtn.setBounds(w / 2 + 100, h - 270, 300, 50);
        add(forgotBtn);

        setVisible(true);
        validate();
    }
}
