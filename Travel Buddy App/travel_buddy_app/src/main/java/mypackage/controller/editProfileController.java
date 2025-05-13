package mypackage.controller;

import javax.swing.*;
import mypackage.model.User;
import mypackage.service.UserDatabaseService;
import mypackage.view.editProfilePanel;
import mypackage.view.login;
import mypackage.view.newPassword;
import mypackage.view.signup;

public class editProfileController
{
    private final JFrame profileFrame;
    private final User user;
    private final JFrame host;

    

    public editProfileController(JFrame profileFrame, User user, JFrame host) {
        this.profileFrame = profileFrame;
        this.user = user;
        this.host = host;
    }

    public void open() {
        SwingUtilities.invokeLater(() -> {


            JFrame editFrame = new JFrame("Edit Profile");
            editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);



            editProfilePanel editPanel = new editProfilePanel(user);

            editPanel.getBackButton().addActionListener(e -> { //back button
                profileFrame.repaint();
                profileFrame.requestFocus();
                profileFrame.setVisible(true);
                editFrame.dispose();

            });

            editPanel.getSaveButton().addActionListener(e -> { //save button

                String name = editPanel.getNameSurname().getText();
                String userName = editPanel.getUsernameField().getText();
                String Email = editPanel.getEmailField().getText();
                String aboutMe = editPanel.getAboutMeTextArea().getText();

                Session.getCurrentUser().updateUserProfile(name, aboutMe, userName, Email);
                editFrame.dispose();
                host.setExtendedState(JFrame.MAXIMIZED_BOTH);
                host.toFront();
                host.requestFocus();
                host.setVisible(true);
                try 
                {
                    Thread.sleep(10);
                } catch (InterruptedException e1) 
                {
                    e1.printStackTrace();
                }
                profileFrame.dispose();
            });

            editPanel.getDeleteAccountButton().addActionListener( e -> { //delete account
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

            editPanel.getChangePasswordButton().addActionListener(e -> { //change password button
                newPassword pass = new newPassword();
                pass.getConfirmButton().addActionListener((a -> { //confirm password button
                    String passWord = pass.getTxtCode().getText();
                    User user = Session.getCurrentUser();
                    user.setPassword(passWord);
                    UserDatabaseService.updateUserProfile(user);
                    pass.dispose();
                }));
            });

            editFrame.setContentPane(editPanel);
            editFrame.setLocationRelativeTo(null);
            editFrame.revalidate();
            editFrame.setVisible(true);
            profileFrame.setVisible(false);


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
