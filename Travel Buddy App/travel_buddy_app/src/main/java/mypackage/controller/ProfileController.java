package mypackage.controller;

import javax.swing.*;
import java.awt.*;
import mypackage.model.User;
import mypackage.view.profile;

public class ProfileController {

    JFrame host = new JFrame();
    public void open(JFrame host) 
    {
        SwingUtilities.invokeLater(() -> {
            this.host = host;
            host.setVisible(false);

            JFrame profileFrame = new JFrame("Your Profile");
            profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            profileFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            User currentUser = Session.getCurrentUser();
            profile profilePanel = new profile(currentUser);

            profilePanel.getBackButton().addActionListener(e -> {
                profileFrame.dispose();
                host.setVisible(true);
            });

            profilePanel.getEditProfileButton().addActionListener(e -> {
                profileFrame.setVisible(false);
                new editProfileController(profileFrame, currentUser, host).open();
            });

            profileFrame.setContentPane(profilePanel);
            profileFrame.invalidate();
            profileFrame.pack();
            profileFrame.setLocationRelativeTo(null);
            profileFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            profileFrame.validate();
            profileFrame.toFront();
            profileFrame.requestFocus();
            profileFrame.setVisible(true);
        });
    }

    public JFrame getHost()
    {
        return host;
    }
}
