package mypackage.controller;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

import mypackage.model.User;
import mypackage.view.explore;
import mypackage.view.findTravelbuddy;
import mypackage.view.profile;

public class FindBuddyController 
{
    public void open(JFrame host) throws ExecutionException, InterruptedException 
    {
        SwingUtilities.invokeLater(() -> {
            host.setVisible(false);

            JFrame explore = new JFrame("Explorer");
            explore.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            explore.setExtendedState(JFrame.MAXIMIZED_BOTH);

            User currentUser = Session.getCurrentUser();
            findTravelbuddy findTravelbuddyPanel = null;

            findTravelbuddyPanel = new findTravelbuddy();

            findTravelbuddyPanel.getBackButton().addActionListener(e -> {
                explore.dispose();
                host.setVisible(true);
            });

            explore.setContentPane(findTravelbuddyPanel);
            explore.invalidate();
            explore.pack();
            explore.setLocationRelativeTo(null);
            explore.setExtendedState(JFrame.MAXIMIZED_BOTH);
            explore.validate();
            explore.toFront();
            explore.requestFocus();
            explore.setVisible(true);
        });
    }
}
