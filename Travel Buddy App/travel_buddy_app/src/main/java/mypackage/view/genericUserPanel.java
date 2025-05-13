package mypackage.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import mypackage.controller.OtherProfileController;
import mypackage.controller.Session;
import mypackage.model.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class genericUserPanel extends JPanel{
    
    static Color blueBack = new Color(204,228,252);
    static Color blueFront = new Color(180,204,244);
    private BufferedImage image;
    private JButton visit;
    User owner;
    User visitor;
    Object[] visitAndOwner;

    public genericUserPanel(User owner) {
        this.owner = owner;
        this.visitor = Session.getCurrentUser();

        this.setBackground(blueBack);
        this.setSize(new Dimension(200,200));
        this.setLayout(null);
        
        try {
            URI uri = URI.create(owner.getPhotoURL());
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

        
        BufferedImage roundedImage = (RoundImage.makePerfectCircle(image, 80,Color.WHITE,2));
        JLabel imageLabel = new JLabel(new ImageIcon(roundedImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBounds(150,30,100,100);
        this.add(imageLabel);
        


        //Name Panel
        JPanel namePanel = new JPanel();
        namePanel.setBackground(blueFront);
        namePanel.setSize(300,300);
        namePanel.setBounds(80,150,260,150);
        namePanel.setLayout(null);

        JLabel name = new JLabel(owner.getNameSurname());
        JLabel username = new JLabel(owner.getUsername());
        name.setForeground(Color.BLACK); name.setFont(new Font("Arial",Font.BOLD,18));
        username.setForeground(Color.BLACK); username.setFont(new Font("Arial",Font.PLAIN,12));
        name.setBounds(10,20,400,100);
        username.setBounds(10,80,400,100);
        namePanel.add(name);
        namePanel.add(username);
        this.add(namePanel);

        //Visit Profile Button
        visit = new RoundedButton("Visit Profile",20);
        visit.setForeground(Color.WHITE);
        visit.setBackground(new Color(55,127,188));
        visit.setBorder(null);
        visit.setFont(new Font("Arial",Font.BOLD,12));
        visit.setBounds(100,320,200,50);
        visit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findTravelbuddy buddyPanel = (findTravelbuddy) SwingUtilities.getAncestorOfClass(findTravelbuddy.class,genericUserPanel.this);
                buddyPanel.setClickedUser(owner);
                buddyPanel.setVisitButton(visit);
                
                try {
                    profile shownProfile = new profile(owner);
                    JFrame newFrame = new JFrame();
                    newFrame.setContentPane(shownProfile);

                    shownProfile.getBackButton().addActionListener((ActionEvent y) ->
                    {
                        newFrame.dispose();
                        buddyPanel.setVisible(true);
                    });

                    // Journal Entries Button
                    shownProfile.getJournalEntriesButton().addActionListener((ActionEvent y) ->
                    {
                        JFrame journalFrame = new JFrame();
                        allJournals journals = null;
                        try {
                            journals = new allJournals(owner,true);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (ExecutionException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        journalFrame.add(journals);
                        journalFrame.invalidate();
                        journalFrame.toFront();
                        journalFrame.setLocationRelativeTo(null);
                        journalFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        journalFrame.validate();
                        journalFrame.toFront();
                        journalFrame.requestFocus();
                        journalFrame.setVisible(true);

                        journals.getBackButton().addActionListener((ActionEvent z) ->
                        {
                            journalFrame.dispose();
                            newFrame.setVisible(true);
                        });
                    });

                    //Favourites Button
                    shownProfile.getFavouritesButton().addActionListener((ActionEvent y) ->
                    {
                        JFrame favFrame = new JFrame();
                        favorites favJournals = null;
                        try {
                            favJournals = new favorites(owner);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (ExecutionException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        favFrame.add(favJournals);
                        favFrame.invalidate();
                        favFrame.toFront();
                        favFrame.setLocationRelativeTo(null);
                        favFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        favFrame.validate();
                        favFrame.toFront();
                        favFrame.requestFocus();
                        favFrame.setVisible(true);

                        favJournals.getBackButton().addActionListener((ActionEvent z) ->
                        {
                            favFrame.dispose();
                            newFrame.setVisible(true);
                        });

                    });

                    newFrame.invalidate();
                    newFrame.toFront();
                    newFrame.setLocationRelativeTo(null);
                    newFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    newFrame.validate();
                    newFrame.toFront();
                    newFrame.requestFocus();
                    newFrame.setVisible(true);


                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            
        });
        this.add(visit);

        visitAndOwner = new Object[2];
        visitAndOwner[0] = visit;
        visitAndOwner[1] = owner;
    }

    public JButton getVisitButton() {
        return visit;
    }

    public Object[] getVisitAndOwner() {
        return visitAndOwner;
    }
    public User getOwner() {
        return owner;
    }
    public User getVisitor() {
        return visitor;
    }
}
