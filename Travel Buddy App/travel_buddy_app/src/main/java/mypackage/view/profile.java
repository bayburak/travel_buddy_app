package mypackage.view;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/* TODO1: Return Button
 * TODO2: Edit Profile Button
 * TODO3: Journals Button
 * TODO4: Favourites Button
 */

public class profile extends JPanel {
    private Color darkBlue=new Color(34,86,153);
    private Color bioColor=new Color(221,224,247);

    public profilePanel() {
        setLayout(new BorderLayout(0, 30));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // top blue section which contains return button and profile label
        JPanel topSection=createTopSection();
        add(topSection, BorderLayout.NORTH);

        // contains profile section and menu section
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0)); 
        contentPanel.setBackground(Color.WHITE);

        // Profile section
        JPanel profileSection = createProfileSection();
        profileSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); 
        contentPanel.add(profileSection, BorderLayout.WEST);

        // Menu section
        JPanel menuSection = createMenuSection();
        contentPanel.add(menuSection, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }
    private JPanel createTopSection(){
        // blue panel
        JPanel panel=new JPanel();
        panel.setBackground(darkBlue);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new java.awt.Dimension(this.getWidth(),70));
        
        // return button
        JButton backButton = new JButton(" ←"); 
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO <3
            }
        });

        // white profile label
        JLabel profileLabel=new JLabel(" Profile");
        profileLabel.setFont(new Font("Arial", Font.BOLD, 25));
        profileLabel.setForeground(Color.WHITE);
        panel.add(backButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(profileLabel);
        return panel; 
    }

    private JPanel createProfileSection() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setBackground(Color.WHITE);
        informationPanel.setPreferredSize(new Dimension(250, -1));
    
        // Profil picture
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("finalProfile.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage roundedImage=(RoundImage.makePerfectCircle(bufferedImage, 200,Color.GRAY,1));
        JLabel profilePictureLabel = new JLabel(new ImageIcon(roundedImage));
        profilePictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePictureLabel.setPreferredSize(new Dimension(250, 250));
        profilePictureLabel.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        informationPanel.add(profilePictureLabel);
        informationPanel.add(Box.createVerticalStrut(20));
    
        // Name and surname
        JLabel nameSurname = new JLabel("Name Surname");
        nameSurname.setAlignmentX(Component.CENTER_ALIGNMENT); 
        informationPanel.add(nameSurname);
        informationPanel.add(Box.createVerticalStrut(10));
    
        // Username
        JLabel username = new JLabel("Username");
        username.setForeground(Color.GRAY);
        username.setAlignmentX(Component.CENTER_ALIGNMENT); 
        informationPanel.add(username);
        informationPanel.add(Box.createVerticalStrut(10));
    
        // Followers and following
        JLabel followersFollowing = new JLabel("0 Followers   0 Following");
        followersFollowing.setForeground(Color.GRAY);
        followersFollowing.setAlignmentX(Component.CENTER_ALIGNMENT); 
        informationPanel.add(followersFollowing);
        informationPanel.add(Box.createVerticalStrut(20));
    
        // Edit Profile Button
        JButton editProfile = new RoundedButton("Edit Profile", 15);
        editProfile.setFont(new Font("Arial", Font.BOLD, 14));
        editProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
        editProfile.setBackground(darkBlue);
        editProfile.setForeground(Color.WHITE);

        editProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO <3
                 */
            }
        });
        informationPanel.add(editProfile);
    
        return informationPanel;
    }

    private JPanel createMenuSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
    
        // A container contains "About Me" and line
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setPreferredSize(new Dimension(panel.getWidth(),30));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
        // "About Me" label
        JLabel aboutMeLabel = new JLabel("About Me");
        aboutMeLabel.setFont(new Font(aboutMeLabel.getFont().getName(), Font.BOLD, 20));
        aboutMeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); 
    
        // A panel for line
        JPanel linePanel = new JPanel();
        linePanel.setPreferredSize(new Dimension(Short.MAX_VALUE , 2));
        linePanel.setBackground(new Color(200, 200, 200));
        linePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 2));
        linePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 11111)); 
        headerPanel.add(aboutMeLabel);
        headerPanel.add(linePanel);
        panel.add(headerPanel);

        // about me text area
        JTextArea aboutMeTextArea = new JTextArea("Our Friendly Potato Love\n" + //
                        "By Travel Buddy App\n" + //
                        "A Love Song For Buddy\n" + //
                        "This is a stock cover image. We regularly add new custom covers so come back tomorrow to see if we've made one for you.\n" + //
                        "\n" + //
                        "\n" + //
                        "This one's for you Mr Traveller!\n" + //
                        "\n" + //
                        "My love for you is like the most Friendly Potato,\n" + //
                        "Your face reminds me of Funny Gorilla,\n" + //
                        "Together, we are like Pizza and Ketchup.\n" + //
                        "\n" + //
                        "Oh darling Buddy,\n" + //
                        "My Friendly Potato,\n" + //
                        "My Funny Apple,\n" + //
                        "The perfect companion to my Pizza soul.\n" + //
                        "\n" + //
                        "Rose are red,\n" + //
                        "Ocean are blue,\n" + //
                        "I like Stars,\n" + //
                        "But not as much as I love Making love with you!\n" + //
                        "\n" + //
                        "Oh darling Buddy,\n" + //
                        "Your foot are like Cool Buddy on a summer day,\n" + //
                        "You're like the most Hot Traveler to ever walk Bilkent.\n" + //
                        "\n" + //
                        "Your Funny Gorill face,\n" + //
                        "Your Ketchup soul,\n" + //
                        "Your Cool foot,\n" + //
                        "Your Hot Traveler being...\n" + //
                        "\n" + //
                        "How could I look at another when our Friendly Potato love is so strong?\n" + //
                        "\n" + //
                        "I love you Mr Traveller!");
        aboutMeTextArea.setBackground(bioColor);
        aboutMeTextArea.setEditable(false); 
        aboutMeTextArea.setLineWrap(true); 
        aboutMeTextArea.setWrapStyleWord(true);
        aboutMeTextArea.setBorder(BorderFactory.createCompoundBorder(
            aboutMeTextArea.getBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // A scrollPane for bio 
        JScrollPane scrollPane = new JScrollPane(aboutMeTextArea);
        scrollPane.setOpaque(false); 
        scrollPane.getViewport().setOpaque(false); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(panel.getWidth(), 300));
    
        // A panel contains favourites and journals button
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setBackground(Color.WHITE);
    
        // Journal Entries button
        JButton journalEntries = new RoundedButton("Journal Entries",40);
        journalEntries.setFont(new Font("Arial",Font.BOLD,30));
        journalEntries.setPreferredSize(new Dimension(300, 150));
        journalEntries.setBackground(darkBlue);
        journalEntries.setForeground(Color.WHITE);
        journalEntries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO <3
                 */
            }
        });
    
        // Favourites button
        JButton favourites = new RoundedButton("Favourites",40);
        favourites.setFont(new Font("Arial",Font.BOLD,30));
        favourites.setPreferredSize(new Dimension(300, 150));
        favourites.setBackground(darkBlue);
        favourites.setForeground(Color.WHITE);
        favourites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO <3
                 */
            }
        });
    
        buttons.add(journalEntries);
        buttons.add(Box.createRigidArea(new Dimension(70, 0)));
        buttons.add(favourites);
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(50));
        panel.add(buttons);
    
        return panel;
    }
}
