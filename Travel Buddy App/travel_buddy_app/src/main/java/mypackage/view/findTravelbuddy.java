package mypackage.view;

import javax.swing.*;

import mypackage.model.User;
import mypackage.service.UserDatabaseService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class findTravelbuddy extends JPanel implements ActionListener {

    static Color blue = new Color(34, 86, 153);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    private JTextField searchField;
    private List<User> users;

    // ← declare the back button here
    private JButton backButton;

    public findTravelbuddy() {

        this.setSize(screenSize);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);

        // Top Blue
        JPanel topBlue = new JPanel();
        topBlue.setLayout(null);
        topBlue.setBackground(blue);
        topBlue.setPreferredSize(new Dimension(screenWidth, 70));

        // Back button 
        backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(blue);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setFocusPainted(false);
        backButton.setBounds(10, 0, 100, 60);
        backButton.addActionListener(e -> {
            // TODO
        });
        topBlue.add(backButton);

        // Find Travel Buddies Label
        JLabel returnProfile = new JLabel("Find Travel Buddies");
        returnProfile.setFont(new Font("Arial", Font.BOLD, 24));
        returnProfile.setForeground(Color.WHITE);
        returnProfile.setBounds(130, 0, 250, 70);
        topBlue.add(returnProfile);

        // Search bar
        searchField = new JTextField(20);
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        searchBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchBarPanel.add(new JLabel("Find Users:  "), BorderLayout.WEST);
        searchBarPanel.add(searchField, BorderLayout.CENTER);
        searchBarPanel.setBounds(400, 7, 500, 50);
        topBlue.add(searchBarPanel);

        // Enter key listener
        searchField.addActionListener(e -> {
            String query = searchField.getText().trim().toLowerCase();

            // Always clear old components
            contentPanel.removeAll();

            if (!query.isEmpty()) {
                try {
                    users = UserDatabaseService.searchUsersByKeyword(query);
                } catch (InterruptedException | ExecutionException e1) {
                    e1.printStackTrace();
                }

                contentPanel.setLayout(new GridLayout(0, 3, 10, 10));
                contentPanel.setBackground(Color.WHITE);

                int panelHeight = users.size() * 200;
                contentPanel.setPreferredSize(new Dimension(screenWidth, panelHeight));

                for (User user : users) {
                    contentPanel.add(new genericUserPanel(user));
                }
            }

            // Always refresh display
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        this.add(topBlue, BorderLayout.NORTH);

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

    /** Expose the back button so callers can attach their own listener */
    public JButton getBackButton() {
        return backButton;
    }
}
