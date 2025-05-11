package mypackage.controller;

import mypackage.model.User;
import mypackage.service.UserDatabaseService;
import mypackage.view.*;

import javax.swing.*;
import java.awt.Frame;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class AuthController {

    private final login  loginView;
    private final signup signupView;

    public AuthController(login loginView, signup signupView)
    {
        this.loginView  = loginView;
        this.signupView = signupView;
        wireLoginView();
        wireSignupView();
    }

    private void wireLoginView()
    {
        loginView.signUp.addActionListener(e -> switchToSignup());
        loginView.signIn.addActionListener(e -> handleLogin());
        loginView.forgotBtn.addActionListener(e -> new ForgotPasswordDialog(loginView));
    }

    private void handleLogin()
    {
        String username = loginView.txtUsername.getText().trim();
        String password = new String(loginView.txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty())
        {
            JOptionPane.showMessageDialog(loginView, "Username and password are required.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            bringLoginToFront();
            return;
        }

        loginView.signIn.setEnabled(false);

        CompletableFuture
            .supplyAsync(() -> {
                try { return UserDatabaseService.getUserByUsername(username); }
                catch (Exception ex) { throw new CompletionException(ex); }
            })
            .thenAccept(user -> SwingUtilities.invokeLater(() -> {
                loginView.signIn.setEnabled(true);

                if (user == null || !password.equals(user.getPassword()))
                {
                    JOptionPane.showMessageDialog(loginView,"Invalid username or password.","Login Failed",JOptionPane.ERROR_MESSAGE);
                    bringLoginToFront();
                    return;
                }

                loginView.dispose();
                new MainViewHande();
            }))
            .exceptionally(ex -> {
                SwingUtilities.invokeLater(() -> {
                    loginView.signIn.setEnabled(true);
                    JOptionPane.showMessageDialog(loginView, "Authentication error: " + ex.getCause().getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    bringLoginToFront();
                });
                return null;
            });
    }

    private void wireSignupView()
    {
        signupView.create.addActionListener(e -> handleSignup());
        signupView.goToLogin.addActionListener(e -> switchToLogin());
    }

    private void handleSignup()
    {
        String nameSurname = signupView.txtNameSurname.getText().trim();
        String username = signupView.txtUsername.getText().trim();
        String email = signupView.txtEmail.getText().trim();
        String password = new String(signupView.txtPassword.getPassword());

        if (nameSurname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty())
        {
            JOptionPane.showMessageDialog(signupView,"Please complete all fields.",  "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!email.matches("^\\S+@\\S+\\.\\S+$"))
        {
            JOptionPane.showMessageDialog(signupView,"Enter a valid email address.","Invalid Email", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CompletableFuture
            .supplyAsync(() -> {
                try
                { 
                    return UserDatabaseService.getUserByUsername(username);
                }
                catch (Exception ex)
                { 
                    throw new CompletionException(ex); 
                }
            })
            .thenAccept(existing -> SwingUtilities.invokeLater(() -> {
                if (existing != null)
                {
                    JOptionPane.showMessageDialog(signupView, "That username is already taken.\nPlease choose another.","Username Unavailable",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try
                {
                    User user = new User(nameSurname, password, email, username);
                    UserDatabaseService.createUser(user);
                    UserDatabaseService.incrementNumberOfUsers();
                }
                catch (IOException io)
                {
                    JOptionPane.showMessageDialog(signupView, "Could not create account: " + io.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(signupView, "Account created successfully. You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                switchToLogin();
                signupView.txtPassword.setText("");
            }))
            .exceptionally(ex -> {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(signupView, "Error checking username: " + ex.getCause().getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
                return null;
            });
    }

    private void switchToSignup()
    {
        loginView.setVisible(false);
        signupView.setVisible(true);
        signupView.setState(Frame.NORMAL);
        signupView.toFront();
        signupView.requestFocus();
    }

    private void switchToLogin()
    {
        signupView.setVisible(false);
        loginView.setVisible(true);
        loginView.setState(Frame.NORMAL);
        loginView.toFront();
        loginView.requestFocus();
    }

    private void bringLoginToFront()
    {
        loginView.setVisible(true);
        loginView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loginView.setState(Frame.NORMAL);
        loginView.toFront();
        loginView.requestFocus();
    }
}
