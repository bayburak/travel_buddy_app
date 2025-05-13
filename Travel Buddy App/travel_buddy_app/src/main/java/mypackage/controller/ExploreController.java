package mypackage.controller;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

import mypackage.model.User;
import mypackage.view.explore;
import mypackage.view.profile;

public class ExploreController 
{
    public void open(JFrame host) 
    {
        SwingUtilities.invokeLater(() -> {

            JFrame explore = new JFrame("Explorer");
            explore.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            explore.setExtendedState(JFrame.MAXIMIZED_BOTH);

            User currentUser = Session.getCurrentUser();
            explore explorePanel = null;

            try 
            {
                explorePanel = new explore(currentUser);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            explorePanel.getBackButton().addActionListener(e -> {
                explore.dispose();
                host.setVisible(true);
            });

            explore.setContentPane(explorePanel);
            explore.invalidate();
            explore.pack();
            explore.setLocationRelativeTo(null);
            explore.setExtendedState(JFrame.MAXIMIZED_BOTH);
            explore.validate();
            explore.toFront();
            explore.requestFocus();
            explore.setVisible(true);
            host.setVisible(false);



        });
    }
}
