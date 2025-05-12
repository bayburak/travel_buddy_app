// src/main/java/mypackage/view/cityPanel.java
package mypackage.view;

import mypackage.model.City;
import mypackage.model.JournalEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

/**
 * Pop‑up showing a city’s name, entry count, and Add/Show buttons.
 */
public class cityPanel extends JPanel {
    private static final Color BLUE = new Color(63,114,149);
    private final JLabel cntLabel;
    private final JButton addEntryBtn;
    private final JButton showEntriesBtn;

    public cityPanel(City city) throws InterruptedException, ExecutionException {
        setLayout(null);
        setPreferredSize(new Dimension(300, 100));
        setBackground(Color.WHITE);

        // Close button
        JButton close = new JButton("X");
        close.setBounds(275, 5, 20, 20);
        close.setFont(new Font("Arial", Font.BOLD, 12));
        close.setForeground(BLUE);
        close.setBorder(null);
        close.setBackground(Color.WHITE);
        close.addActionListener(e -> {
            Container p = getParent();
            if (p != null) {
                p.remove(this);
                p.revalidate();
                p.repaint();
            }
        });
        add(close);

        // City name label
        JLabel nameLbl = new JLabel(city.getName());
        nameLbl.setBounds(10, 10, 200, 25);
        nameLbl.setFont(new Font("Arial", Font.BOLD, 20));
        nameLbl.setForeground(BLUE);
        add(nameLbl);

        // Count label
        cntLabel = new JLabel("Journal Entries: " +JournalEntry.getEntriesByCityID(city.getCityID()).size());
        cntLabel.setBounds(10, 35, 200, 20);
        cntLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        cntLabel.setForeground(BLUE);
        add(cntLabel);

        // Button row
        addEntryBtn = makeButton("Add Entry");
        showEntriesBtn = makeButton("Show Entries");
        addEntryBtn.setBounds(10, 60, 130, 25);
        showEntriesBtn.setBounds(150, 60, 130, 25);
        add(addEntryBtn);
        add(showEntriesBtn);
    }

    private JButton makeButton(String text) {
        JButton b = new RoundedButton(text, 20);
        b.setBackground(BLUE);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    /** Hook for "Add Entry" */
    public void addEntryListener(ActionListener l)
    {
        addEntryBtn.addActionListener(l);
    }

    /** Hook for "Show Entries" */
    public void addShowEntriesListener(ActionListener l)
    {
        showEntriesBtn.addActionListener(l);
    }

    /** Update the displayed count */
    public void setEntryCount(int cnt) 
    {
        cntLabel.setText("Journal Entries: " + cnt);
    }
}