package mypackage.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * TODO1:Return button
 * TODO2:Delete Account button
 * TODO3:Edit profile picture button->attach photo frame
 * TODO4:Change password button
 * TODO5:Save button
 */

public class editProfilePanel extends JPanel {
    Color darkBlue=new Color(34,86,153);
    Color lightBlue=new Color(176,204,242);
    Color bioColor=new Color(220,224,247);

    public editProfilePanel(){

        setLayout(new BorderLayout(0, 30));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // top blue section which contains return button and Edit Profile label
        JPanel topSection=createTopSection();
        add(topSection, BorderLayout.NORTH);

        add(Box.createHorizontalStrut(20),BorderLayout.WEST);
        
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
        add(Box.createRigidArea(new Dimension(0, 30)),BorderLayout.SOUTH);
    }

    private JPanel createTopSection(){
        // blue panel
        JPanel panel=new JPanel();
        panel.setBackground(darkBlue);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new java.awt.Dimension(this.getWidth(),70));
        
        // back button
        JButton backButton = new JButton(" ←"); 
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                /*
                 * TODO <3
                 */
            }
        });

        // white edit profile label
        JLabel profileLabel=new JLabel(" Edit Profile");
        profileLabel.setFont(new Font("Arial", Font.BOLD, 25));
        profileLabel.setForeground(Color.WHITE);
        panel.add(backButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(profileLabel);
        panel.add(Box.createHorizontalStrut(800));

        // delete account button
        JButton deleteAccount=new RoundedButton("Delete Account", 20);
        deleteAccount.setFont(new Font("Arial", Font.BOLD, 20));
        deleteAccount.setBackground(Color.RED);
        deleteAccount.setForeground(Color.WHITE);
        deleteAccount.setPreferredSize(new Dimension(200, 40));
        deleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                /*
                 * TODO <3
                 */
            }
        });
        panel.add(deleteAccount);

        return panel; 
    }

    private JPanel createProfileSection() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setBackground(lightBlue);
        informationPanel.setPreferredSize(new Dimension(400, 400));
        informationPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); 

        // Profil picture
        BufferedImage bufferedImage=null;
        try {
            bufferedImage = ImageIO.read(new File("finalProfile.jpeg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BufferedImage roundedImage = (RoundImage.makePerfectCircle(bufferedImage, 250, Color.GRAY, 1));
        JLabel profilePictureLabel = new JLabel(new ImageIcon(roundedImage));
        profilePictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePictureLabel.setPreferredSize(new Dimension(300, 300));
        profilePictureLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        informationPanel.add(profilePictureLabel);
    
        // Edit Picture button
        JButton editPhoto = new RoundedButton("Edit Picture", 20);
        editPhoto.setFont(new Font("Arial", Font.BOLD, 20));
        editPhoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPhoto.setBackground(darkBlue);
        editPhoto.setForeground(Color.WHITE);
        editPhoto.setPreferredSize(new Dimension(20, 30));
        editPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                /*
                 * TODO <3
                 */
            }
        });
        informationPanel.add(editPhoto);
        informationPanel.add(Box.createVerticalStrut(20));
    
        // Name and surname 
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0)); 
        namePanel.setBackground(lightBlue);
        JTextField nameSurname = new JTextField("Name Surname");
        nameSurname.setFont(new Font("Arial", Font.PLAIN, 20));
        nameSurname.setForeground(Color.GRAY);
        nameSurname.setEditable(true);
        nameSurname.setOpaque(true);
        nameSurname.setPreferredSize(new Dimension(380, 30));
        namePanel.add(nameSurname);
        informationPanel.add(namePanel);
        informationPanel.add(Box.createVerticalStrut(10));
    
        // Username 
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        usernamePanel.setBackground(lightBlue);
        JTextField username = new JTextField("Username");
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setPreferredSize(new Dimension(380, 30));
        username.setForeground(Color.GRAY);
        username.setEditable(true);
        username.setOpaque(true);
        usernamePanel.add(username);
        informationPanel.add(usernamePanel);
        informationPanel.add(Box.createVerticalStrut(10));
    
        // e-mail 
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        emailPanel.setBackground(lightBlue);
        JTextField e_mail = new JTextField("E-mail");
        e_mail.setFont(new Font("Arial", Font.PLAIN, 20));
        e_mail.setPreferredSize(new Dimension(380, 30));
        e_mail.setForeground(Color.GRAY);
        e_mail.setEditable(true); 
        e_mail.setOpaque(true);
        emailPanel.add(e_mail);
        informationPanel.add(emailPanel);
        informationPanel.add(Box.createVerticalStrut(30));
    
        // Change Password Button
        JButton changePassword = new RoundedButton("Change Password", 15);
        changePassword.setFont(new Font("Arial", Font.BOLD, 20));
        changePassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePassword.setBackground(darkBlue);
        changePassword.setForeground(Color.WHITE);
        changePassword.setPreferredSize(new Dimension(70, 40));
        editPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                /*
                 * TODO <3
                 */
            }
        });
        informationPanel.add(changePassword);
        informationPanel.add(Box.createVerticalStrut(30));
    
        return informationPanel;
    }

    private JPanel createBioSection() {
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
        aboutMeTextArea.setEditable(true); 
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
        scrollPane.setPreferredSize(new Dimension(panel.getWidth(), 500));
    
        // A panel contains save button
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setBackground(Color.WHITE);
    
        // Save button
        JButton save = new RoundedButton("Save",30);
        save.setFont(new Font("Arial",Font.BOLD,30));
        save.setPreferredSize(new Dimension(120, 40));
        save.setBackground(darkBlue);
        save.setForeground(Color.WHITE);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO <3
                 */
            }
        });
    
        buttons.add(save);
        buttons.add(Box.createRigidArea(new Dimension(70, 0)));
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(50));
        panel.add(buttons);
    
        return panel;
    }
}
