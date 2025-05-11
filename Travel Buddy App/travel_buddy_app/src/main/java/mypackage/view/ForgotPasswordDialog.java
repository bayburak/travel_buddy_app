package mypackage.view;

import mypackage.model.User;
import mypackage.service.EmailService;
import mypackage.service.UserDatabaseService;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class ForgotPasswordDialog extends JDialog
{

    private final JTextField txtUsername = new JTextField();
    private final JButton sendBtn = new JButton("Send Code");

    private final JTextField txtCode = new JTextField();
    private final JButton verifyBtn = new JButton("Verify Code");

    private String token;
    private User user;

    public ForgotPasswordDialog(Frame owner) {
        super(owner, "Forgot Password", true);
        setLayout(null);
        setSize(450, 300);
        setLocationRelativeTo(owner);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(30, 20, 380, 20);
        add(l1);
        txtUsername.setBounds(30, 45, 380, 28);
        add(txtUsername);
        sendBtn.setBounds(150, 80, 130, 28);
        add(sendBtn);

        JLabel l2 = new JLabel("Verification code:");
        l2.setBounds(30, 130, 380, 20);
        add(l2);
        txtCode.setBounds(30, 155, 380, 28);
        txtCode.setEnabled(false);
        add(txtCode);
        verifyBtn.setBounds(150, 190, 130, 28);
        verifyBtn.setEnabled(false);
        add(verifyBtn);

        sendBtn.addActionListener(e -> sendCode());
        verifyBtn.addActionListener(e -> verifyCode());

        setVisible(true);
    }

    private void sendCode()
    {
        String uname = txtUsername.getText().trim();
        if (uname.isEmpty())
        {
            warn("Username required"); return;
        }

        CompletableFuture.supplyAsync(() -> {
            try   { return UserDatabaseService.getUserByUsername(uname); }
            catch (InterruptedException | ExecutionException ex) { return null; }
        }).thenAccept(u -> SwingUtilities.invokeLater(() -> {
            if (u == null)
            { 
                warn("User not found"); 
                return; 
            }
            user  = u;
            token = String.valueOf(ThreadLocalRandom.current().nextInt(100_000, 1_000_000));
            if (EmailService.sendPasswordResetEmail(user, token))
            {
                info("Code sent to " + user.getE_mail());
                txtCode.setEnabled(true); verifyBtn.setEnabled(true);
                sendBtn.setEnabled(false); txtUsername.setEnabled(false);
            } 
            else 
            {
                warn("Email failed check SMTP/AppPassword");
            }
        }));
    }

    private void verifyCode()
    {
        if (EmailService.verifyEmailToken(user, token, txtCode.getText().trim()))
        {
            dispose();
            new NewPasswordDialog(user);
        } 
        else 
        {
            warn("Incorrect code");
        }
    }

    private void warn(String msg) { JOptionPane.showMessageDialog(this, msg, "Warning", JOptionPane.WARNING_MESSAGE); }
    private void info(String msg) { JOptionPane.showMessageDialog(this, msg); }
}
