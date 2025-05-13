package mypackage.view;

import javax.swing.*;

import mypackage.model.City;
import mypackage.model.JournalEntry;
import mypackage.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class cityEntries extends JPanel implements ActionListener {

    static Color blue = new Color(34, 86, 153);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    List<JournalEntry> entriesAll;
    List<JournalEntry> entries;
    User user;
    City city;
    JButton backButton;
    JLabel returnProfile;
    JPanel contentPanel;
    JScrollPane scrollPane;

    public cityEntries(User user, City city) throws InterruptedException, ExecutionException {
        this.user = user;
        this.setSize(screenSize);
        this.setLayout(new BorderLayout());
        this.city = city;

        // Initialize entries
        entriesAll = JournalEntry.getEntriesByCityID(city.getCityID());
        entries = new ArrayList<>();
        for (JournalEntry entry : entriesAll) {
            if (entry.isPublicEntry()) {
                entries.add(entry);
            }
        }
        entriesAll = null;

        //        Top Blue Panel
        JPanel topBlue = new JPanel();
        topBlue.setLayout(null);
        topBlue.setBackground(blue);
        topBlue.setPreferredSize(new Dimension(screenWidth, 70));
    
        // Back Button
        backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(blue);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setFocusPainted(false);
        backButton.setBounds(10, 0, 100, 60);
        topBlue.add(backButton);
    
        // Title Label
        returnProfile = new JLabel(city.getName() + "'s Entries");
        returnProfile.setFont(new Font("Arial", Font.BOLD, 24));
        returnProfile.setForeground(Color.WHITE);
        returnProfile.setBounds(130, 0, 600, 70); 
        topBlue.add(returnProfile);
    
        this.add(topBlue, BorderLayout.NORTH); 
    
        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
    
        int panelHeight = entries.size() * 300;
        contentPanel.setPreferredSize(new Dimension(screenWidth, panelHeight));
    
        // Adding generic journal panels
        for (JournalEntry entry : entries) {
            contentPanel.add(new genericJournalPanels(entry, user,this));
            contentPanel.add(Box.createVerticalStrut(20));
        }
    
        // Scroll Pane Setup
        scrollPane = new JScrollPane(contentPanel);
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

    public void refreshEntries() 
    {
        try {
            entriesAll = JournalEntry.getEntriesByCityID(city.getCityID());
            
            entries.clear();
            for (JournalEntry entry : entriesAll) {
                if (entry.isPublicEntry()) {
                    entries.add(entry);
                }
            }

            contentPanel.removeAll();

            int panelHeight = entries.size() * 300;
            contentPanel.setPreferredSize(new Dimension(screenWidth, panelHeight));

            for (JournalEntry entry : entries) {
                contentPanel.add(new genericJournalPanels(entry, user, this));
                contentPanel.add(Box.createVerticalStrut(20));
            }

            contentPanel.revalidate();
            contentPanel.repaint();
            scrollPane.revalidate();
            scrollPane.repaint();

            SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to refresh city entries.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        

    public City getCity() 
    {
        return city;
    }
    public JButton getBackButton() 
    {
        return backButton;
    }

    public JLabel getReturnProfileLabel() 
    {
        return returnProfile;
    }

    public JPanel getContentPanel() 
    {
        return contentPanel;
    }

    public JScrollPane getScrollPane() 
    {
        return scrollPane;
    }
}
