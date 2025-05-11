package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public genericJournalPanels() {
        this.panelsWidth = 350;
        this.panelsHeight = 100;

        JPanel menu = new JPanel();
        menu.setVisible(false);

        this.setLayout(null);
        this.setSize(panelsWidth, panelsHeight);
        this.setBackground(blueBack);

        //Panel for photo
        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(blueFront);
        photoPanel.setBounds(20,100,400,270);
        this.add(photoPanel);

        //Panel for entry
        JPanel entryPanel = new JPanel();
        entryPanel.setBackground(blueFront);
        entryPanel.setBounds(470,35,1350,350);
        this.add(entryPanel);

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
                menu.setBounds(1600,4,200,300);
            }
        });
        dots.setBounds(1815,0,40,40);
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
