package mypackage.controller;

import mypackage.model.City;
import mypackage.view.cityEntries;
import mypackage.model.User;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public final class ShowCityJournalController {

    private ShowCityJournalController() {}

    public static void createForm(JFrame mainWindow, City preselectedCity) {
        try 
        {
            Container previousView = mainWindow.getContentPane();
            
            cityEntries entriesPanel = new cityEntries(Session.getCurrentUser(), preselectedCity);

            mainWindow.setContentPane(entriesPanel);

            mainWindow.invalidate();
            mainWindow.validate();
            mainWindow.repaint();

            entriesPanel.getBackButton().addActionListener(e -> {
                mainWindow.setContentPane(previousView);
                mainWindow.invalidate();
                mainWindow.validate();
                mainWindow.repaint();
            });

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainWindow, "Error loading city entries.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
