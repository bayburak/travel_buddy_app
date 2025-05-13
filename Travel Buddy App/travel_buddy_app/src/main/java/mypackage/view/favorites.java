package mypackage.view;

import javax.swing.*;

import mypackage.model.JournalEntry;
import mypackage.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class favorites extends JPanel implements ActionListener{

    static Color blue = new Color(34, 86, 153);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    List<JournalEntry> favorites;
    JButton backButton;
    JPanel contentPanel;
    User user;

    public favorites(User user) throws InterruptedException, ExecutionException {

        this.user =user;
        this.setSize(screenSize);
        this.setLayout(new BorderLayout());
    
        try {
            this.favorites = user.SavedEntries();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    
        //Top Blue Panel
        JPanel topBlue = new JPanel();
        topBlue.setLayout(null);
        topBlue.setBackground(blue);
        topBlue.setPreferredSize(new Dimension(screenWidth, 70));
    
        //Back Button
        backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(blue);
        backButton.setContentAreaFilled(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setFocusPainted(false);
        backButton.setBounds(10, 0, 100, 60);
        topBlue.add(backButton);
    
        
        JLabel returnProfile = new JLabel("Favorites");
        returnProfile.setFont(new Font("Arial", Font.BOLD, 24));
        returnProfile.setForeground(Color.WHITE);
        returnProfile.setBounds(130, 0, 500, 70); 
        topBlue.add(returnProfile);
    
        this.add(topBlue, BorderLayout.NORTH); 
    
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
    
        int panelHeight = favorites.size() * 300;
        contentPanel.setPreferredSize(new Dimension(screenWidth, panelHeight));
    
        for (JournalEntry entry : favorites) {
            contentPanel.add(new genericJournalPanels(entry,user, this));
            contentPanel.add(Box.createVerticalStrut(20));
        }
    
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
    
        
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
    
        this.add(scrollPane, BorderLayout.CENTER);

    }

    public JButton getBackButton() {
        return backButton;
    }

    public JPanel getContentJPanel() {
        return contentPanel;
    }

    public void setContentPanel(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    public void refreshEntries() 
    {
    try {
        favorites = user.SavedEntries();
        contentPanel.removeAll();

        int panelHeight = favorites.size() * 300;
        contentPanel.setPreferredSize(new Dimension(screenWidth, panelHeight));

        for (JournalEntry entry : favorites) {
            contentPanel.add(new genericJournalPanels(entry, user, this));
            contentPanel.add(Box.createVerticalStrut(20));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to refresh entries.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
