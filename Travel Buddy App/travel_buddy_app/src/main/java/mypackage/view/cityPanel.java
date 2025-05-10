package mypackage.view;
import mypackage.model.*;

import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * TODO1: addEntryButton
 * TODO2: showEntryButton
 */

public class cityPanel extends JPanel {
    private Color color = new Color(63, 114, 149);
    private City city;

    public cityPanel(City Acity) {
        this.city=Acity;
        // Set layout to null for absolute positioning
        setLayout(null);
        
        // Set fixed size
        setPreferredSize(new Dimension(300, 100));
        setMinimumSize(new Dimension(300, 100));
        setMaximumSize(new Dimension(300, 100));
        
        setBackground(Color.WHITE);
        
        // Create and position the X button
        JButton x = new JButton("X");
        x.setBackground(Color.WHITE);
        x.setForeground(color);
        x.setFont(new Font("Arial", Font.BOLD, 12)); 
        x.setBorder(null);
        x.addActionListener(e-> {
            Container parent = this.getParent();
            if(parent!=null){
                parent.remove(this);
                parent.revalidate();
                parent.repaint();
            }
            
        });
        x.setBounds(275, 5, 20, 20); 
        add(x);
        
        // Create city name label
        JLabel cityName = new JLabel(city.getName());
        cityName.setForeground(color);
        cityName.setFont(new Font("Arial", Font.PLAIN, 20));
        cityName.setBounds(10, 10, 180, 30); 
        add(cityName);

        JLabel journalEntries = new JLabel("Journal Entries: "+city.getEntryCount());
        journalEntries.setForeground(color);
        journalEntries.setFont(new Font("Arial", Font.PLAIN, 20));
        journalEntries.setBounds(10, 30, 180, 30); 
        add(journalEntries);
        
        // Create buttons panel
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setBounds(10, 60, 280, 30);
        
        // Create buttons
        JButton addEntries = new RoundedButton("Add Entry", 20);
        addEntries.setBackground(color);
        addEntries.setForeground(Color.WHITE);
        addEntries.setPreferredSize(new Dimension(130, 25));
        addEntries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

            }
        });
        
        JButton showEntries = new RoundedButton("Show Entries", 20);
        showEntries.setBackground(color);
        showEntries.setForeground(Color.WHITE);
        showEntries.setPreferredSize(new Dimension(130, 25));
        showEntries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

            }
        });
        
        buttons.add(addEntries);
        buttons.add(Box.createHorizontalStrut(5));
        buttons.add(showEntries);
        
        add(buttons);
    }
}
