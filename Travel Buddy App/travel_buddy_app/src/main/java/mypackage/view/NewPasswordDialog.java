package mypackage.view;

import mypackage.model.User;
import mypackage.service.UserDatabaseService;

import javax.swing.*;
import java.awt.*;

/** Lets the verified user choose a new password. */
public class NewPasswordDialog extends JDialog {

    private final JPasswordField p1 = new JPasswordField();
    private final JPasswordField p2 = new JPasswordField();
    private final JButton change = new JButton("Change");

    private final User user;

    public NewPasswordDialog(User user)
    {
        super((Frame) null, "Set New Password", true);
        this.user = user;

        setLayout(null);
        setSize(400, 230);
        setLocationRelativeTo(null);

        addLabel("New password:", 30);
        p1.setBounds(30, 45, 340, 28); add(p1);

        addLabel("Confirm password:", 90);
        p2.setBounds(30, 115, 340, 28); add(p2);

        change.setBounds(140, 155, 120, 28); add(change);
        change.addActionListener(e -> save());

        setVisible(true);
    }

    private void save()
    {
        String s1 = new String(p1.getPassword());
        String s2 = new String(p2.getPassword());

        if (s1.isEmpty())
        {
            warn("Password cannot be empty");
            return;
        }
        if (!s1.equals(s2))
        { 
            warn("Passwords do not match");
            return;
        }

        user.setPassword(s1);
        UserDatabaseService.updateUserProfile(user);
        JOptionPane.showMessageDialog(this, "Password updated");
        dispose();
    }

    private void addLabel(String txt, int y)
    {
        JLabel l = new JLabel(txt); l.setBounds(30, y, 340, 20); add(l);
    }
    private void warn(String m)
    { 
        JOptionPane.showMessageDialog(this, m, "Input Error", JOptionPane.WARNING_MESSAGE);
    }
}
