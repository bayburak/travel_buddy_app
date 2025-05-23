package mypackage.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import mypackage.controller.Session;
import mypackage.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class profile extends JPanel {
    private final Color darkBlue = new Color(34, 86, 153);
    private final Color bioColor  = new Color(221, 224, 247);

    // Buttons to expose via getters
    private JButton backButton;
    private JButton editProfileButton;
    private JButton journalEntriesButton;
    private JButton favouritesButton;
    JLabel followersFollowing;

    // Profile picture label (for internal updates)
    private JLabel profilePictureLabel;
    boolean doesFollow;
    User user;
    protected Object getBackButton;

    public profile(User user) throws InterruptedException, ExecutionException {
        this.user = user;
        setLayout(new BorderLayout(0, 30));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // Top section (back button + title)
        add(createTopSection(), BorderLayout.NORTH);

        // Main content: left = profile, center = menu
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(createProfileSection(), BorderLayout.WEST);
        contentPanel.add(createMenuSection(), BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createTopSection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(darkBlue);
        panel.setPreferredSize(new Dimension(0, 70)); // height = 70

        backButton = new JButton(" ←");
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);
        // default no-op; external code can attach via getBackButton()
        backButton.addActionListener((ActionEvent e) -> { /* TODO: navigate back */ });

        JLabel titleLabel = new JLabel(" Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);

        panel.add(backButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(titleLabel);

        return panel;
    }

    private JPanel createProfileSection() throws InterruptedException, ExecutionException {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);
        info.setPreferredSize(new Dimension(250, -1));

        // PFP
        BufferedImage image = null;
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

        BufferedImage circ = RoundImage.makePerfectCircle(image, 200, Color.GRAY, 1);
        profilePictureLabel = new JLabel(new ImageIcon(circ));
        profilePictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePictureLabel.setPreferredSize(new Dimension(250, 250));
        info.add(profilePictureLabel);
        info.add(Box.createVerticalStrut(20));

        // Name / username / followers
        JLabel nameLabel = new JLabel(user.getNameSurname());
        nameLabel.setFont(new Font("Arial",Font.BOLD,24));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(nameLabel);
        info.add(Box.createVerticalStrut(10));

        JLabel usernameLabel = new JLabel(user.getUsername());
        usernameLabel.setForeground(Color.GRAY);
        usernameLabel.setFont(new Font("Arial",Font.PLAIN,20));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(usernameLabel);
        info.add(Box.createVerticalStrut(10));

        followersFollowing = new JLabel(user.FollowersObjectArray().size()+" Followers   "+ user.FollowingsObjextArray().size()+ " Following");
        followersFollowing.setForeground(Color.GRAY);
        followersFollowing.setAlignmentX(Component.CENTER_ALIGNMENT); 
        info.add(followersFollowing);
        info.add(Box.createVerticalStrut(20));

        if (Session.getCurrentUser().getUserID().equals(user.getUserID())) {
            // Edit Profile button
            editProfileButton = null;
            editProfileButton = new RoundedButton("Edit Profile", 15);
            editProfileButton.setFont(new Font("Arial", Font.BOLD, 14));
            editProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            editProfileButton.setBackground(darkBlue);
            editProfileButton.setForeground(Color.WHITE);
            info.add(editProfileButton);
        }

        else {
            editProfileButton = null;
            ArrayList<User> following = (ArrayList<User>) user.FollowingsObjextArray();
            editProfileButton = new RoundedButton("", 15);
            editProfileButton.setBounds(90,50,100,50);

            doesFollow = following.contains(user);

            //DO NOT DELETE
            if (doesFollow) {
                editProfileButton.setText("Following");
                editProfileButton.setBackground(Color.LIGHT_GRAY);
            } else {
                editProfileButton.setText("Follow");
                editProfileButton.setBackground(darkBlue); // back to blue
            }
            followersFollowing.setText(user.FollowersObjectArray().size()+" Followers   "+ user.FollowingsObjextArray().size()+ " Following");
            this.repaint();

            editProfileButton.addActionListener(e -> {
                try {
                    manageFollow(e);
                } catch (InterruptedException | ExecutionException a) {
                    a.printStackTrace();
                }
            });        
            //DO NOT DELETE

            info.add(editProfileButton);
        }

        return info;
    }

    private JPanel createMenuSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // "About Me" header
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel aboutLabel = new JLabel("About Me");
        aboutLabel.setFont(new Font(aboutLabel.getFont().getName(), Font.BOLD, 20));
        aboutLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        aboutLabel.setBounds(20, 20, 880, 180); //not done


        JPanel line = new JPanel();
        line.setBackground(new Color(200, 200, 200));
        line.setPreferredSize(new Dimension(Short.MAX_VALUE, 2));
        line.setMaximumSize(new Dimension(Short.MAX_VALUE, 2));

        header.add(aboutLabel);
        header.add(line);
        panel.add(header);

        // Bio text area
        JTextArea bioArea = new JTextArea(user.getAboutMe());
        bioArea.setFont(new Font("Arial",Font.PLAIN,24));
        bioArea.setBackground(bioColor);
        bioArea.setEditable(false);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setBorder(BorderFactory.createCompoundBorder(
            bioArea.getBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JScrollPane bioScroll = new JScrollPane(bioArea);
        bioScroll.setOpaque(false);
        bioScroll.getViewport().setOpaque(false);
        bioScroll.setBorder(BorderFactory.createEmptyBorder());
        bioScroll.setPreferredSize(new Dimension(panel.getWidth(), 300));
        panel.add(bioScroll);
        panel.add(Box.createVerticalStrut(50));

        // Journal Entries & Favourites buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        journalEntriesButton = new RoundedButton("Journal Entries", 40);
        journalEntriesButton.setFont(new Font("Arial", Font.BOLD, 30));
        journalEntriesButton.setPreferredSize(new Dimension(300, 150));
        journalEntriesButton.setBackground(darkBlue);
        journalEntriesButton.setForeground(Color.WHITE);
        // TODO3: attach listener externally via getter

        favouritesButton = new RoundedButton("Favourites", 40);
        favouritesButton.setFont(new Font("Arial", Font.BOLD, 30));
        favouritesButton.setPreferredSize(new Dimension(300, 150));
        favouritesButton.setBackground(darkBlue);
        favouritesButton.setForeground(Color.WHITE);
        // TODO4: attach listener externally via getter

        buttonPanel.add(journalEntriesButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(70, 0)));
        buttonPanel.add(favouritesButton);
        panel.add(buttonPanel);

        return panel;
    }

    //DO NOT DELETE
    private void manageFollow(ActionEvent e) throws InterruptedException, ExecutionException {
        doesFollow = !doesFollow;
        if (doesFollow) {
            Session.getCurrentUser().unfollowUser(user.getUserID());
            editProfileButton.setText("Following");
            editProfileButton.setBackground(Color.LIGHT_GRAY);
        } else {
            Session.getCurrentUser().followUser(user.getUserID());
            editProfileButton.setText("Follow");
            editProfileButton.setBackground(darkBlue);
        }
        followersFollowing.setText(user.FollowersObjectArray().size()+" Followers   "+ user.FollowingsObjextArray().size()+ " Following");
        this.repaint();
    }
    //DO NOT DELETE 

    // Getters for all buttons

    /** Back (return) button in the top section */
    public JButton getBackButton() {
        return backButton;
    }

    /** "Edit Profile" button under the profile picture */
    public JButton getEditProfileButton() {
        return editProfileButton;
    }

    /** "Journal Entries" button in the menu section */
    public JButton getJournalEntriesButton() {
        return journalEntriesButton;
    }

    /** "Favourites" button in the menu section */
    public JButton getFavouritesButton() {
        return favouritesButton;
    }

    public User getUser() {
        return user;
    }
}
