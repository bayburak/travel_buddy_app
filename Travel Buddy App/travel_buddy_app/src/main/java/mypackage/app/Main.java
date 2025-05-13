package mypackage.app;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import mypackage.controller.AuthController;
import mypackage.service.DatabaseService;
import mypackage.service.StorageService;
import mypackage.view.login;
import mypackage.view.signup;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        DatabaseService.initialize();
        StorageService.initialize();

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            System.out.println("Failed to set look and feel: " + e.getMessage());
        }
        
        login loginView = new login();
        signup signupView = new signup();
        signupView.setVisible(false);
        loginView.setVisible(true);
        new AuthController(loginView, signupView);
    }
}
