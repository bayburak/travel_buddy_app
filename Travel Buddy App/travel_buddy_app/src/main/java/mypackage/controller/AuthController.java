package mypackage.controller;

import mypackage.model.User;
import mypackage.service.UserDatabaseService;
import mypackage.view.*;

import javax.swing.*;
import java.awt.Frame;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AuthController {

    private final login loginView;
    private final signup signupView;

    public AuthController(login loginView, signup signupView) {
        this.loginView = loginView;
        this.signupView = signupView;
        wireLoginView();
        wireSignupView();
    }

    private void wireLoginView() {
        loginView.signUp.addActionListener(e -> switchToSignup());
        loginView.signIn.addActionListener(e -> {
            try {
                handleLogin();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        loginView.forgotBtn.addActionListener(e -> new ForgotPasswordDialog(loginView));
    }

    private void handleLogin() throws InterruptedException, ExecutionException {
        String username = loginView.txtUsername.getText().trim();
        String password = new String(loginView.txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty())
        {
            JOptionPane.showMessageDialog(loginView, "Please enter both username and password.", "Error", JOptionPane.WARNING_MESSAGE);
            bringLoginToFront();
            return;
        }

        loginView.signIn.setEnabled(false);

        try
        {
            User user = UserDatabaseService.getUserByUsername(username);
            if (user == null || !password.equals(user.getPassword()))
            {
                JOptionPane.showMessageDialog(loginView, "Oops! Incorrect username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                loginView.signIn.setEnabled(true);
                bringLoginToFront();
                return;
            }

            loginView.dispose();
            Session.setCurrentUser(user);

            JFrame mapFrame = new JFrame("Map Menu");
            mapFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            new MapController(mapFrame);
            mapFrame.setVisible(true);

        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(loginView, "Login failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
        finally
        {
            loginView.signIn.setEnabled(true);
        }
    }

    private void wireSignupView()
    {
        signupView.create.addActionListener(e -> {
            try 
            {
                handleSignup();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        signupView.goToLogin.addActionListener(e -> switchToLogin());
    }

    private void handleSignup() throws InterruptedException, ExecutionException {
        String nameSurname = signupView.txtNameSurname.getText().trim();
        String username = signupView.txtUsername.getText().trim();
        String email = signupView.txtEmail.getText().trim();
        String password = new String(signupView.txtPassword.getPassword());

        if (nameSurname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) 
        {
            JOptionPane.showMessageDialog(signupView, "All feilds must be filled!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            JOptionPane.showMessageDialog(signupView, "Invalid email format. Try again.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User existingUser = UserDatabaseService.getUserByUsername(username);
            if (existingUser != null) {
                JOptionPane.showMessageDialog(signupView, "Username is taken. Please try a different one.", "Username Taken", JOptionPane.WARNING_MESSAGE);
                return;
            }

            User user = new User(nameSurname, password, email, username);
            user.addUsertoDatabase;
            JOptionPane.showMessageDialog(signupView, "Account successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
            switchToLogin();
            signupView.txtPassword.setText("");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(signupView, "Error during registration: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        loginView.toFront();
        loginView.requestFocus();
    }
}
