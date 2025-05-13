package mypackage.view;

import javax.swing.*;

import mypackage.model.JournalEntry;
import mypackage.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class explore extends JPanel implements ActionListener {

    static Color blue = new Color(34, 86, 153);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    List<JournalEntry> entries;
    User user;

    // ← field to hold the back button
    private JButton backButton;

    public explore(User user) throws InterruptedException, ExecutionException {

        this.setSize(screenSize);
        this.setLayout(new BorderLayout());

        this.user = user;
        
        try {
            entries = JournalEntry.getTopEntries();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Top Blue Panel
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
        backButton.addActionListener(e -> {
            //TODO
        });
        topBlue.add(backButton);

        JLabel returnProfile = new JLabel("Explore");
        returnProfile.setFont(new Font("Arial", Font.BOLD, 24));
        returnProfile.setForeground(Color.WHITE);
        returnProfile.setBounds(130, 0, 500, 70);
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

    /** Expose the back‐button so external code can attach a listener */
    public JButton getBackButton() {
        return backButton;
    }
}
