package mypackage.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import mypackage.model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/* TODO1: Return Button
 * TODO2: Edit Profile Button
 * TODO3: Journals Button
 * TODO4: Favourites Button
 */

public class otherProfile extends JPanel {
    private User owner;
    private User visitor;
    BufferedImage image;

    private Color darkBlue=new Color(34,86,153);
    private Color bioColor=new Color(221,224,247);

    boolean doesFollow;
    JButton editProfile;
    JLabel followersFollowing;

    public otherProfile(User owner, User visitor) throws InterruptedException, ExecutionException {
        this.owner= owner;
        this.visitor = visitor;

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

    private JPanel createProfileSection() throws InterruptedException, ExecutionException {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setBackground(Color.WHITE);
        informationPanel.setPreferredSize(new Dimension(250, -1));
    
        // Profil picture
        try {
            URI uri = URI.create(owner.getPhotoURL());
            URL url = uri.toURL();  
            
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

        
        BufferedImage roundedImage = (RoundImage.makePerfectCircle(image, 80,Color.WHITE,2));
        JLabel imageLabel = new JLabel(new ImageIcon(roundedImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBounds(150,30,100,100);

        informationPanel.add(imageLabel);
        informationPanel.add(Box.createVerticalStrut(20));
    
        // Name and surname
        JLabel nameSurname = new JLabel(owner.getNameSurname());
        nameSurname.setAlignmentX(Component.CENTER_ALIGNMENT); 
        nameSurname.setFont(new Font("Arial",Font.BOLD,24));
        informationPanel.add(nameSurname);
        informationPanel.add(Box.createVerticalStrut(10));
    
        // Username
        JLabel username = new JLabel(owner.getUsername());
        username.setForeground(Color.GRAY);
        username.setFont(new Font("Arial",Font.PLAIN,20));
        username.setAlignmentX(Component.CENTER_ALIGNMENT); 
        informationPanel.add(username);
        informationPanel.add(Box.createVerticalStrut(10));
    
        // Followers and following
        followersFollowing = new JLabel(owner.getFollowersObjectArray().size()+" Followers   "+ owner.getFollowingsObjextArray().size()+ " Following");
        followersFollowing.setForeground(Color.GRAY);
        followersFollowing.setAlignmentX(Component.CENTER_ALIGNMENT); 
        informationPanel.add(followersFollowing);
        informationPanel.add(Box.createVerticalStrut(20));
    
        // Edit Profile Button
        ArrayList<User> following = (ArrayList<User>) visitor.getFollowingsObjextArray();
        editProfile = new RoundedButton("", 15);
        editProfile.setBounds(90,50,100,50);

        doesFollow = following.contains(owner);

        //DO NOT DELETE
        if (doesFollow) {
            editProfile.setText("Following");
            editProfile.setBackground(Color.LIGHT_GRAY);
        } else {
            editProfile.setText("Follow");
            editProfile.setBackground(darkBlue); // back to blue
        }
        followersFollowing.setText(owner.getFollowersObjectArray().size()+" Followers   "+ owner.getFollowingsObjextArray().size()+ " Following");
        this.repaint();

        editProfile.addActionListener(e -> {
            try {
                manageFollow(e);
            } catch (InterruptedException | ExecutionException a) {
                // TODO Auto-generated catch block
                a.printStackTrace();
            }
        });        
        //DO NOT DELETE

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
        aboutMeLabel.setBounds(20, 20, 880, 180); //not done

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
        JTextArea aboutMeTextArea = new JTextArea(owner.getAboutMe());
        aboutMeTextArea.setFont(new Font("Arial",Font.PLAIN,24));
        aboutMeTextArea.setBackground(bioColor);
        aboutMeTextArea.setEditable(false); 
        aboutMeTextArea.setLineWrap(true); 
        aboutMeTextArea.setWrapStyleWord(true);
        aboutMeTextArea.setBorder(BorderFactory.createCompoundBorder(
            aboutMeTextArea.getBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        aboutMeTextArea.setText(owner.getAboutMe());

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

    //DO NOT DELETE
    private void manageFollow(ActionEvent e) throws InterruptedException, ExecutionException {
        doesFollow = !doesFollow;
        if (doesFollow) {
            visitor.unfollowUser(owner.getUserID());
            editProfile.setText("Following");
            editProfile.setBackground(Color.LIGHT_GRAY);
        } else {
            visitor.followUser(owner.getUserID());
            editProfile.setText("Follow");
            editProfile.setBackground(darkBlue);
        }
        followersFollowing.setText(owner.getFollowersObjectArray().size()+" Followers   "+ owner.getFollowingsObjextArray().size()+ " Following");
        this.repaint();
    }
    //DO NOT DELETE 

    //Getters
    public User getOtherUser() {
        return owner;
    }
}
