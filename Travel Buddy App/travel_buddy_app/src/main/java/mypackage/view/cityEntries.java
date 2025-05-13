package mypackage.view;

import javax.swing.*;

import mypackage.model.City;
import mypackage.model.JournalEntry;
import mypackage.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class cityEntries extends JPanel implements ActionListener{

    static Color blue = new Color(34, 86, 153);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    List<JournalEntry> entriesAll;
    List<JournalEntry> entries;

    public cityEntries(User user, City city) throws InterruptedException, ExecutionException {

        this.setSize(screenSize);
        this.setLayout(new BorderLayout());

        entriesAll = JournalEntry.getEntriesByCityID(city.getCityID());
        for (JournalEntry entry : entriesAll) {
            if (entry.isPublicEntry()) {
                entries.add(entry);
            }
        }
        entriesAll = null;
    
        //Top Blue Panel
        JPanel topBlue = new JPanel();
        topBlue.setLayout(null);
        topBlue.setBackground(blue);
        topBlue.setPreferredSize(new Dimension(screenWidth, 70));
    
        //Back Button
        JButton backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(blue);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setFocusPainted(false);
        backButton.setBounds(10, 0, 100, 60);
        backButton.addActionListener(e -> {
            //TODO
        });
        topBlue.add(backButton);
    
        
        JLabel returnProfile = new JLabel(city.getName() + "'s Entries");
        returnProfile.setFont(new Font("Arial", Font.BOLD, 24));
        returnProfile.setForeground(Color.WHITE);
        returnProfile.setBounds(130, 0, 600, 70); 
        topBlue.add(returnProfile);
    
        this.add(topBlue, BorderLayout.NORTH); 
    
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
    
        int panelHeight = entries.size() * 300;
        contentPanel.setPreferredSize(new Dimension(screenWidth, panelHeight));
    
        for (JournalEntry entry : entries) {
            contentPanel.add(new genericJournalPanels(entry,user));
            contentPanel.add(Box.createVerticalStrut(20));
        }
    
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
    
        
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
    
        this.add(scrollPane, BorderLayout.CENTER); 
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
