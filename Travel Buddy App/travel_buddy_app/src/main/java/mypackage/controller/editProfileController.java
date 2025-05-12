package mypackage.controller;

import javax.swing.*;
import mypackage.model.User;
import mypackage.view.editProfilePanel;

public class editProfileController
{
    private final JFrame profileFrame;
    private final User   user;

    public editProfileController(JFrame profileFrame, User user) {
        this.profileFrame = profileFrame;
        this.user = user;
    }

    public void open() {
        SwingUtilities.invokeLater(() -> {

            profileFrame.setVisible(false);

            JFrame editFrame = new JFrame("Edit Profile");
            editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            editProfilePanel editPanel = new editProfilePanel(user);

            editPanel.getBackButton().addActionListener(e -> {
                editFrame.dispose();
                profileFrame.setVisible(true);
            });

            editPanel.getSaveButton().addActionListener(e -> {
                editFrame.dispose();
                profileFrame.setVisible(true);
            });

            editFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            editFrame.setContentPane(editPanel);
            editFrame.setLocationRelativeTo(null);
            editFrame.setVisible(true);
        });
    }
    /*
     * leaveWithoutSaving
                JOptionPane.showMessageDialog(
                    this,
                    "Photo attached!",
                    "Photo Uploader",
                    JOptionPane.INFORMATION_MESSAGE
                );
     */
}
