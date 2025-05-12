package mypackage.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import mypackage.model.*;

public class editProfilePanel extends JPanel {
    private User user;
    private Color darkBlue  = new Color(34, 86, 153);
    private Color lightBlue = new Color(176, 204, 242);
    private Color bioColor  = new Color(220, 224, 247);
    BufferedImage image;

    // ← Fields to hold each button
    private JButton backButton;
    private JButton deleteAccountButton;
    private JButton editPhotoButton;
    private JButton changePasswordButton;
    private JButton saveButton;

    public editProfilePanel(User user){
        this.user = user;

        setLayout(new BorderLayout(0, 30));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        JPanel topSection = createTopSection();
        add(topSection, BorderLayout.NORTH);

        add(Box.createHorizontalStrut(20), BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));
        contentPanel.setBackground(Color.WHITE);

        JPanel profileSection = createProfileSection();
        profileSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        contentPanel.add(profileSection, BorderLayout.WEST);

        JPanel bioSection = createBioSection();
        contentPanel.add(bioSection, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
        add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.SOUTH);
    }

    private JPanel createTopSection(){
        JPanel panel = new JPanel();
        panel.setBackground(darkBlue);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(this.getWidth(), 70));

        // ← assign to field, not a local
        backButton = new JButton(" ←");
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> {
            // TODO <3 returns to profile frame
        });
        panel.add(backButton);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel profileLabel = new JLabel(" Edit Profile");
        profileLabel.setFont(new Font("Arial", Font.BOLD, 25));
        profileLabel.setForeground(Color.WHITE);
        panel.add(profileLabel);

        panel.add(Box.createHorizontalStrut(800));

        // ← assign to field
        deleteAccountButton = new RoundedButton("Delete Account", 20);
        deleteAccountButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteAccountButton.setBackground(Color.RED);
        deleteAccountButton.setForeground(Color.WHITE);
        deleteAccountButton.setPreferredSize(new Dimension(200, 40));
        deleteAccountButton.addActionListener(e -> {
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
            if(option == JOptionPane.YES_OPTION){
                // TODO Delete account and return to sign page
            }
        });
        panel.add(deleteAccountButton);

        return panel; 
    }

    private JPanel createProfileSection() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setBackground(lightBlue);
        informationPanel.setPreferredSize(new Dimension(400, 400));
        informationPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        // load image from URL
        try {
            URI uri = URI.create(
                "https://firebasestorage.googleapis.com/v0/b/travelbuddyapp-35c7b.firebasestorage.app/"
              + "o/profile_photos%2Fdefault.jpeg?alt=media&token=6a937830-968e-4f9a-9d1e-0ed330fbbe91"
            );
            URL url = uri.toURL();
            try (InputStream in = url.openStream()) {
                image = ImageIO.read(in);
                System.out.println(image != null
                    ? "Image successfully read!"
                    : "Failed to decode the image.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage roundedImage =
            RoundImage.makePerfectCircle(image, 250, Color.GRAY, 1);
        JLabel profilePictureLabel = new JLabel(new ImageIcon(roundedImage));
        profilePictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePictureLabel.setPreferredSize(new Dimension(300, 300));
        profilePictureLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        informationPanel.add(profilePictureLabel);

        // ← assign to field
        editPhotoButton = new RoundedButton("Edit Picture", 20);
        editPhotoButton.setFont(new Font("Arial", Font.BOLD, 20));
        editPhotoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPhotoButton.setBackground(darkBlue);
        editPhotoButton.setForeground(Color.WHITE);
        editPhotoButton.setPreferredSize(new Dimension(20, 30));
        editPhotoButton.addActionListener(e -> {
            // TODO open PhotoUploader frame
        });
        informationPanel.add(editPhotoButton);
        informationPanel.add(Box.createVerticalStrut(20));

        // … name, username, email fields …

        // ← assign to field
        changePasswordButton = new RoundedButton("Change Password", 15);
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 20));
        changePasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePasswordButton.setBackground(darkBlue);
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setPreferredSize(new Dimension(70, 40));
        changePasswordButton.addActionListener(e -> {
            // TODO open newPassword frame
        });
        informationPanel.add(changePasswordButton);
        informationPanel.add(Box.createVerticalStrut(30));

        return informationPanel;
    }

    private JPanel createBioSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // … header + textarea …

        // ← assign to field
        saveButton = new RoundedButton("Save", 30);
        saveButton.setFont(new Font("Arial", Font.BOLD, 30));
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setBackground(darkBlue);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            // TODO update user and return to profile frame
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setBackground(Color.WHITE);
        buttons.add(saveButton);
        buttons.add(Box.createRigidArea(new Dimension(70, 0)));
        panel.add(buttons);

        return panel;
    }

    // ——— Getters for each button ———

    /** Return (back) button */
    public JButton getBackButton() {
        return backButton;
    }

    /** Delete Account button */
    public JButton getDeleteAccountButton() {
        return deleteAccountButton;
    }

    /** Edit Picture button */
    public JButton getEditPhotoButton() {
        return editPhotoButton;
    }

    /** Change Password button */
    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }

    /** Save button */
    public JButton getSaveButton() {
        return saveButton;
    }
}
