package mypackage.controller;

import javax.swing.*;
import mypackage.model.User;
import mypackage.view.editProfilePanel;
import mypackage.view.login;
import mypackage.view.signup;

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

            editPanel.getDeleteAccountButton().addActionListener( e -> {
                Object[] options = {"Yes", "No"};
                int option = JOptionPane.showOptionDialog(
                    null,
                    "Do you want to delete your account?",
                    "",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]
                );
                if (option == JOptionPane.YES_OPTION)
                {
                    user.deleteUser();
                    editFrame.dispose();
                    login loginView = new login();
                    signup signupView = new signup();
                    signupView.setVisible(false);
                    loginView.setVisible(true);
                    new AuthController(loginView, signupView);

                }
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
