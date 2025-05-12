package mypackage.view;

import javax.swing.*;

import mypackage.model.City;
import mypackage.model.JournalEntry;
import mypackage.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class genericJournalPanels extends JPanel implements ActionListener{
    
    static Color blue = new Color(34, 86, 153);
    static Color blueBack = new Color(204,228,252);
    static Color blueFront = new Color(180,204,244);
    static Color blueMenu = new Color(55,127,188);
    static Font menuText = new Font("Arial",Font.BOLD,19);
    int panelsWidth;
    int panelsHeight;
    //variables to keep track of the users entries' feautures
    //date, city name, pp, etc.
    //JournalEntry object
    //TODO

    public genericJournalPanels(JournalEntry entry) throws InterruptedException, ExecutionException {
        this.panelsWidth = 350;
        this.panelsHeight = 100;

        JPanel menu = new JPanel();
        menu.setVisible(false);

        this.setLayout(null);
        this.setSize(panelsWidth, panelsHeight);
        this.setBackground(blueBack);

        //Labels
        JLabel cityName = new JLabel(City.getCitybyID(entry.getCityID()).getName());
        JLabel date = new JLabel(entry.getCreationDate());
        JLabel username = new JLabel(User.getUserByID(entry.getAuthorID()).getUsername());
        JLabel title = new JLabel(entry.getTitle());

        cityName.setForeground(Color.BLACK); cityName.setFont(new Font("Arial",Font.PLAIN,12));
        date.setForeground(Color.BLACK); date.setFont(new Font("Arial",Font.PLAIN,12));
        username.setForeground(Color.BLACK); username.setFont(new Font("Arial",Font.PLAIN,12));
        title.setForeground(Color.BLACK); title.setFont(new Font("Arial",Font.BOLD,24));

        cityName.setBounds(250,20,100,20);
        date.setBounds(250,50,100,20);
        username.setBounds(50,50,100,20);
        this.add(cityName); this.add(date); this.add(username);

        title.setBounds(350,8,500,50);
        this.add(title);

        //Panel for photo
        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(blueFront);
        photoPanel.setBounds(30,90,300,150);
        this.add(photoPanel);

        //Panel for entry
        JPanel entryPanel = new JPanel();
        entryPanel.setBackground(blueFront);
        entryPanel.setBounds(350,40,900,200);
        entryPanel.setLayout(null);
        this.add(entryPanel);

        JTextArea entryArea = new JTextArea(entry.getContent());
        entryArea.setForeground(Color.BLACK);
        entryArea.setFont(new Font("Arial",Font.PLAIN,24));
        entryArea.setBackground(null);
        entryArea.setBounds(10,10,900,200);
        entryPanel.add(entryArea);

        //Menu Button
        RoundedButton dots = new RoundedButton("...", 10);
        dots.setBackground(blue);
        dots.setForeground(Color.WHITE);
        dots.setFont(new Font("Arial", Font.BOLD,30));
        dots.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(!menu.isVisible());
                if (menu.isVisible()) {
                    menu.removeAll();
                }
                menu.setLayout(new GridLayout(6,1));
                //Add to favorites
                //Edit photo
                JButton addFav = new JButton("Add/Remove Favorites");
                addFav.setBackground(blueMenu);
                addFav.setBorder(null);
                addFav.setFocusPainted(false);
                addFav.setForeground(Color.WHITE);
                addFav.setFont(new Font("Arial",Font.BOLD,15));
                addFav.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO
                    }
                    
                });
                menu.add(addFav);
                //Edit photo
                JButton editPhoto = new JButton("Edit Photo");
                editPhoto.setBackground(blueMenu);
                editPhoto.setBorder(null);
                editPhoto.setFocusPainted(false);
                editPhoto.setForeground(Color.WHITE);
                editPhoto.setFont(menuText);
                editPhoto.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO
                    }
                    
                });
                menu.add(editPhoto);
                //Delete photo
                JButton deletePhoto = new JButton("Delete Photo");
                deletePhoto.setBackground(blueMenu);
                deletePhoto.setBorder(null);
                deletePhoto.setFocusPainted(false);
                deletePhoto.setForeground(Color.WHITE);
                deletePhoto.setFont(menuText);
                deletePhoto.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO
                    }
                    
                });
                menu.add(deletePhoto);
                //Edit text
                JButton editText = new JButton("Edit Text");
                editText.setBackground(blueMenu);
                editText.setBorder(null);
                editText.setFocusPainted(false);
                editText.setForeground(Color.WHITE);
                editText.setFont(menuText);
                editText.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO
                    }
                    
                });
                menu.add(editText);
                //Delete text
                JButton deleteText = new JButton("Delete Text");
                deleteText.setBackground(blueMenu);
                deleteText.setBorder(null);
                deleteText.setFocusPainted(false);
                deleteText.setForeground(Color.WHITE);
                deleteText.setFont(menuText);
                deleteText.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO
                    }
                    
                });
                menu.add(deleteText);
                //Delete Entry
                JButton deleteEntry = new JButton("Delete Entry");
                deleteEntry.setBackground(blueMenu);
                deleteEntry.setBorder(null);
                deleteEntry.setFocusPainted(false);
                deleteEntry.setForeground(Color.WHITE);
                deleteEntry.setFont(menuText);
                deleteEntry.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO
                    }
                    
                });
                menu.add(deleteEntry);
                menu.setBounds(70,4,200,250);
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

    
}
