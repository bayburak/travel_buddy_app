package mypackage.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import mypackage.model.User;

/*
 * TODO1:Return button
 * TODO2:Delete Account button
 * TODO3:Edit profile picture button->attach photo frame
 * TODO4:Change password button
 * TODO5:Save button
 */
public class editProfilePanel extends JPanel {
    private User user;
    private Color darkBlue  = new Color(34, 86, 153);
    private Color lightBlue = new Color(176, 204, 242);
    private Color bioColor  = new Color(220, 224, 247);

    // Buttons exposed via getters
    private JButton backButton;
    private JButton deleteAccountButton;
    private JButton editPhotoButton;
    private JButton changePasswordButton;
    private JButton saveButton;

    public editProfilePanel(User user) {
        this.user = user;

        setLayout(new BorderLayout(0, 30));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // top blue section which contains return button and Edit Profile label
        JPanel topSection = createTopSection();
        add(topSection, BorderLayout.NORTH);

        add(Box.createHorizontalStrut(20), BorderLayout.WEST);

        // contains profile section and bio section
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));
        contentPanel.setBackground(Color.WHITE);

        // Profile section
        JPanel profileSection = createProfileSection();
        profileSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        contentPanel.add(profileSection, BorderLayout.WEST);

        // bio section
        JPanel bioSection = createBioSection();
        contentPanel.add(bioSection, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
        add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.SOUTH);
    }

    private JPanel createTopSection() {
        JPanel panel = new JPanel();
        panel.setBackground(darkBlue);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(this.getWidth(), 70));

        // back button
        backButton = new JButton(" ←");
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                // TODO: returns to profile frame
            }
        });
        panel.add(backButton);

        // label
        JLabel profileLabel = new JLabel(" Edit Profile");
        profileLabel.setFont(new Font("Arial", Font.BOLD, 25));
        profileLabel.setForeground(Color.WHITE);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(profileLabel);
        panel.add(Box.createHorizontalStrut(800));

        // delete account button
        deleteAccountButton = new RoundedButton("Delete Account", 20);
        deleteAccountButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteAccountButton.setBackground(Color.RED);
        deleteAccountButton.setForeground(Color.WHITE);
        deleteAccountButton.setPreferredSize(new Dimension(200, 40));
        panel.add(deleteAccountButton);

        return panel;
    }

    private JPanel createProfileSection() 
    {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setBackground(lightBlue);
        informationPanel.setPreferredSize(new Dimension(400, 400));
        informationPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));


        BufferedImage image = null;
        //PFP
        try {
            URI uri = URI.create( user.getPhotoURL());
            URL url = uri.toURL();  // Preferred over new URL(String)
            
            try (InputStream in = url.openStream()) {
                
                image = ImageIO.read(in);

                if (image != null) {
                    System.out.println("Image successfully read!");
                } else {
                    System.out.println("Failed to decode the image.");
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        //

        // 3) Now it’s safe to circle‐crop
        BufferedImage roundedImage = (RoundImage.makePerfectCircle(image, 200,Color.GRAY,1));
        
        
        JLabel profilePictureLabel = new JLabel(new ImageIcon(roundedImage));
        profilePictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePictureLabel.setPreferredSize(new Dimension(300, 300));
        profilePictureLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        informationPanel.add(profilePictureLabel);

        // Edit Picture button
        editPhotoButton = new RoundedButton("Edit Picture", 20);
        editPhotoButton.setFont(new Font("Arial", Font.BOLD, 20));
        editPhotoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPhotoButton.setBackground(darkBlue);
        editPhotoButton.setForeground(Color.WHITE);
        editPhotoButton.setPreferredSize(new Dimension(20, 30));
        editPhotoButton.addActionListener(e -> {
            // TODO: open PhotoUploader and update user’s photoURL
        });
        informationPanel.add(editPhotoButton);
        informationPanel.add(Box.createVerticalStrut(20));

        // Name and surname
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        namePanel.setBackground(lightBlue);
        JTextField nameSurnameField = new JTextField(user.getNameSurname());
        nameSurnameField.setFont(new Font("Arial", Font.PLAIN, 20));
        nameSurnameField.setForeground(Color.GRAY);
        nameSurnameField.setEditable(true);
        nameSurnameField.setOpaque(true);
        nameSurnameField.setPreferredSize(new Dimension(380, 30));
        namePanel.add(nameSurnameField);
        informationPanel.add(namePanel);
        informationPanel.add(Box.createVerticalStrut(10));

        // Username
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        usernamePanel.setBackground(lightBlue);
        JTextField usernameField = new JTextField(user.getUsername());
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameField.setPreferredSize(new Dimension(380, 30));
        usernameField.setForeground(Color.GRAY);
        usernameField.setEditable(true);
        usernameField.setOpaque(true);
        usernamePanel.add(usernameField);
        informationPanel.add(usernamePanel);
        informationPanel.add(Box.createVerticalStrut(10));

        // e-mail
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        emailPanel.setBackground(lightBlue);
        JTextField emailField = new JTextField(user.getE_mail());
        emailField.setFont(new Font("Arial", Font.PLAIN, 20));
        emailField.setPreferredSize(new Dimension(380, 30));
        emailField.setForeground(Color.GRAY);
        emailField.setEditable(true);
        emailField.setOpaque(true);
        emailPanel.add(emailField);
        informationPanel.add(emailPanel);
        informationPanel.add(Box.createVerticalStrut(30));

        // Change Password button
        changePasswordButton = new RoundedButton("Change Password", 15);
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 20));
        changePasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePasswordButton.setBackground(darkBlue);
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setPreferredSize(new Dimension(70, 40));
        changePasswordButton.addActionListener(e -> {
            // TODO: open change-password dialog
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

        // "About Me" header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setPreferredSize(new Dimension(panel.getWidth(), 30));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel aboutMeLabel = new JLabel("About Me");
        aboutMeLabel.setFont(new Font(aboutMeLabel.getFont().getName(), Font.BOLD, 20));
        aboutMeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JPanel linePanel = new JPanel();
        linePanel.setPreferredSize(new Dimension(Short.MAX_VALUE, 2));
        linePanel.setBackground(new Color(200, 200, 200));
        linePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 2));
        headerPanel.add(aboutMeLabel);
        headerPanel.add(linePanel);
        panel.add(headerPanel);

        // about-me text area
        JTextArea aboutMeTextArea = new JTextArea(user.getAboutMe());
        aboutMeTextArea.setBackground(bioColor);
        aboutMeTextArea.setEditable(true);
        aboutMeTextArea.setLineWrap(true);
        aboutMeTextArea.setWrapStyleWord(true);
        aboutMeTextArea.setBorder(BorderFactory.createCompoundBorder(
            aboutMeTextArea.getBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JScrollPane scrollPane = new JScrollPane(aboutMeTextArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(panel.getWidth(), 500));
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(50));

        // Save button
        saveButton = new RoundedButton("Save", 30);
        saveButton.setFont(new Font("Arial", Font.BOLD, 30));
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setBackground(darkBlue);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                // TODO: update user's info and return to profile frame
            }
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setBackground(Color.WHITE);
        buttons.add(saveButton);
        buttons.add(Box.createRigidArea(new Dimension(70, 0)));
        panel.add(buttons);

        return panel;
    }

    // Getters for buttons

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
