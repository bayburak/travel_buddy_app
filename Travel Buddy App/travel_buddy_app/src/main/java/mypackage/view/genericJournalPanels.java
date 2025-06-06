package mypackage.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import mypackage.model.City;
import mypackage.model.JournalEntry;
import mypackage.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.awt.image.BufferedImage;

public class genericJournalPanels extends JPanel implements ActionListener {
    
    static Color blue = new Color(34, 86, 153);
    static Color blueBack = new Color(204,228,252);
    static Color blueFront = new Color(180,204,244);
    static Color blueMenu = new Color(55,127,188);
    static Font menuText = new Font("Arial",Font.BOLD,19);
    int panelsWidth;
    int panelsHeight;
    User user;

    User visitor;

    JournalEntry entry;
    static boolean isFaved;
    JButton addFav;
    JButton editPhoto;
    JButton deletePhoto;
    JButton editText;
    JButton deleteText;
    JButton deleteEntry;

    // New added fields
    JLabel cityName;
    JLabel date;
    JLabel username;
    JLabel noOfFavs;
    JTextArea title;
    JTextArea entryArea;
    User currentUser;

    public genericJournalPanels(JournalEntry entry, User visitor, JPanel parent) throws InterruptedException, ExecutionException {
        this.entry = entry;
        this.visitor = visitor;

        this.panelsWidth = 350;
        this.panelsHeight = 100;

        currentUser = Session.getCurrentUser();
        user = User.getUserByID(entry.getAuthorID());
        if (currentUser.SavedEntries().contains(entry)) {
            isFaved = true;
        } else {
            isFaved = false;
        }

        JPanel menu = new JPanel();
        menu.setVisible(false);

        this.setLayout(null);
        this.setSize(panelsWidth, panelsHeight);
        this.setBackground(blueBack);

        // Labels
        cityName = new JLabel(City.getCitybyID(entry.getCityID()).getName());
        date = new JLabel(entry.getCreationDate());
        username = new JLabel(User.getUserByID(entry.getAuthorID()).getUsername());
        title = new JTextArea(entry.getTitle());
        title.setEditable(false);
        title.setBackground(null);
        noOfFavs = new JLabel("No of likes: " + entry.getNoOfFavorites());
        noOfFavs.setForeground(Color.BLACK); noOfFavs.setFont(new Font("Arial",Font.PLAIN,12));

        cityName.setForeground(Color.BLACK); cityName.setFont(new Font("Arial",Font.PLAIN,12));
        date.setForeground(Color.BLACK); date.setFont(new Font("Arial",Font.PLAIN,12));
        username.setForeground(Color.BLACK); username.setFont(new Font("Arial",Font.PLAIN,12));
        title.setForeground(Color.BLACK); title.setFont(new Font("Arial",Font.BOLD,24));

        cityName.setBounds(250,20,100,20);
        date.setBounds(250,50,100,20);
        username.setBounds(50,50,100,20);
        noOfFavs.setBounds(120,50,100,20);
        this.add(cityName); this.add(date); this.add(username); this.add(noOfFavs);

        title.setBounds(350,8,500,32);
        this.add(title);

        // Panel for photo
         if(entry.getPhotoURL() != null && entry.getPhotoURL() != ""){
            BufferedImage image = null;
        try {
            URI uri = URI.create(entry.getPhotoURL());
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

            JLabel photoLabel;
            photoLabel = new JLabel(new ImageIcon(image));
            photoLabel.setBackground(blueFront);
            photoLabel.setBounds(30,90,300,150);
            this.add(photoLabel);
        }
        else{
            JPanel photopanel = new JPanel();
            photopanel.setBackground(blueFront);
            photopanel.setBounds(30,90,300,150);
            this.add(photopanel);
            
        }

        // Panel for entry
        JPanel entryPanel = new JPanel();
        entryPanel.setBackground(blueFront);
        entryPanel.setBounds(350,40,900,200);
        entryPanel.setLayout(null);
        this.add(entryPanel);

        entryArea = new JTextArea(entry.getContent());
        entryArea.setForeground(Color.BLACK);
        entryArea.setBackground(null);
        entryArea.setFont(new Font("Arial",Font.PLAIN,24));
        entryArea.setBackground(null);
        entryArea.setBounds(20, 20, 880, 180);
        entryArea.setLineWrap(true);
        entryArea.setWrapStyleWord(true);
        entryArea.setEditable(false);
        entryPanel.add(entryArea);

        // Menu Button
        RoundedButton dots = new RoundedButton("...", 10);
        dots.setBackground(blue);
        dots.setForeground(Color.WHITE);
        dots.setFont(new Font("Arial", Font.BOLD,30));
        dots.addActionListener(e -> {
            menu.setVisible(!menu.isVisible());
            if (menu.isVisible()) {
                menu.removeAll();
            }

            // Add to favorites
            addFav = new JButton();
            addFav.setBackground(blueMenu);
            addFav.setBorder(null);
            addFav.setFocusPainted(false);
            addFav.setForeground(Color.WHITE);
            addFav.setFont(new Font("Arial",Font.BOLD,15));

            //DO NOT DELETE THIS ANYMORE!!!!!!!!!
            if (isFaved) {
                addFav.setText("Remove from Favorites");
            } else {
                addFav.setText("Add to Favorites");
            }
            addFav.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    manageFavs();
                }
                
            });
            //DO NOT DELETE THIS ANYMORE!!!!!!!!!

            // Edit photo
            editPhoto = new JButton("Edit Photo");
            editPhoto.setBackground(blueMenu);
            editPhoto.setBorder(null);
            editPhoto.setFocusPainted(false);
            editPhoto.setForeground(Color.WHITE);
            editPhoto.setFont(menuText);
            editPhoto.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    new PhotoUploader(fileURL ->{
                        try {
                            entry.setPhoto(fileURL);
                        if (parent instanceof explore) 
                        {
                            ((explore) parent).refreshEntries();
                        } 
                        else if (parent instanceof cityEntries) 
                        {
                            ((cityEntries) parent).refreshEntries();
                        }  
                        else if(parent instanceof allJournals)
                        {
                            ((allJournals) parent).refreshEntries();
                        }
                        else if(parent instanceof favorites)
                        {
                            ((favorites) parent).refreshEntries();
                        }                      
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });
                }
            });
            

            // Delete photo
            deletePhoto = new JButton("Delete Photo");
            deletePhoto.setBackground(blueMenu);
            deletePhoto.setBorder(null);
            deletePhoto.setFocusPainted(false);
            deletePhoto.setForeground(Color.WHITE);
            deletePhoto.setFont(menuText);
            deletePhoto.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    entry.removePhoto("entry_photos/" + entry.getEntryID());
                    if (parent instanceof explore) 
                    {
                        ((explore) parent).refreshEntries();
                    } 
                    else if (parent instanceof cityEntries) 
                    {
                        ((cityEntries) parent).refreshEntries();
                    }
                    else if(parent instanceof allJournals)
                    {
                        ((allJournals) parent).refreshEntries();
                    }      
                
                }
                
            });
            

            // Edit text
            editText = new JButton("Edit Text");
            editText.setBackground(blueMenu);
            editText.setBorder(null);
            editText.setFocusPainted(false);
            editText.setForeground(Color.WHITE);
            editText.setFont(menuText);
            editText.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    title.setEditable(true);
                    entryArea.setEditable(true);
                }
                
            });
            

            // Save text
            deleteText = new JButton("Save Text");
            deleteText.setBackground(blueMenu);
            deleteText.setBorder(null);
            deleteText.setFocusPainted(false);
            deleteText.setForeground(Color.WHITE);
            deleteText.setFont(menuText);
            deleteText.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    title.setEditable(false);
                    entryArea.setEditable(false);
                    entry.updateEntry(title.getText(), entryArea.getText(), entry.isPublicEntry());
                }
                
            });

            // Delete Entry
            deleteEntry = new JButton("Delete Entry");
            deleteEntry.setBackground(blueMenu);
            deleteEntry.setBorder(null);
            deleteEntry.setFocusPainted(false);
            deleteEntry.setForeground(Color.WHITE);
            deleteEntry.setFont(menuText);
            deleteEntry.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JournalEntry.deleteEntry(entry.getEntryID());
                    try {
                        Thread.sleep(350); //not final
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    if (parent instanceof explore) 
                    {
                        ((explore) parent).refreshEntries();
                    } 
                    else if (parent instanceof cityEntries) 
                    {
                        ((cityEntries) parent).refreshEntries();
                    }
                    else if(parent instanceof allJournals)
                    {
                        ((allJournals) parent).refreshEntries();
                    }      
                    else if(parent instanceof favorites)
                    {
                        ((favorites) parent).refreshEntries();
                    }
                    else if(parent instanceof favorites)
                    {
                        ((favorites) parent).refreshEntries();
                    }  
                }
                
            });
            
            

            if (currentUser.getUserID().equals(user.getUserID())) {
                menu.removeAll();
                menu.setLayout(new GridLayout(6,1));
                menu.setBounds(70,4,200,250);
                menu.add(addFav);
                menu.add(editPhoto);
                menu.add(deletePhoto);
                menu.add(editText);
                menu.add(deleteText);
                menu.add(deleteEntry);
            } else {
                menu.removeAll();
                menu.setLayout(new GridLayout(1,1));
                menu.setBounds(70,4,200,50);
                menu.add(addFav);
            }
        });
        dots.setBounds(20,0,40,40);
        dots.setBorder(null);
        this.add(dots);
        this.add(menu);
        this.setComponentZOrder(menu, 0);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    //DO NOT DELETE THIS ANYMORE!!!!!!!!!
    public void manageFavs() {
        isFaved = !isFaved;
        if (isFaved) {
            currentUser.addToSaved(entry.getEntryID());
            JOptionPane.showMessageDialog(null, "Successfully added to favorites", null, JOptionPane.INFORMATION_MESSAGE);
            addFav.setText("");
            addFav.setText("Remove from Favorites");  
            this.repaint();      
        }
        else {
            currentUser.removeFromSaved(entry.getEntryID());
            JOptionPane.showMessageDialog(null, "Successfully removed from favorites", null, JOptionPane.INFORMATION_MESSAGE);
            addFav.setText("");
            addFav.setText("Add to Favorites"); 
            this.repaint();
        }
    }
    //DO NOT DELETE THIS ANYMORE!!!!!!!!!

    public JButton getAddFavBtn() { return addFav; }
    public JButton getEditPhotoBtn() { return editPhoto; }
    public JButton getDeletePhotoBtn() { return deletePhoto; }
    public JButton getEditTextBtn() { return editText; }
    public JButton getDeleteTextBtn() { return deleteText; }
    public JButton getDeleteEntryBtn() { return deleteEntry; }
    public JLabel getCityNameLabel() { return cityName; }
    public JLabel getDateLabel() { return date; }
    public JLabel getUsernameLabel() { return username; }
    public JTextArea getTitleLabel() { return title; }
    public JTextArea getEntryArea() { return entryArea; }
}
