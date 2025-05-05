package mypackage.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * TODO1:Return button
 * TODO2:Attach photo button->attach photo frame
 * TODO3:Submit button
 */

public class journalEntry extends JPanel {
    Color lightGray = new Color(217, 217, 217);
    Color darkBlue = new Color(34, 86, 153);
    
    public journalEntryPanel() {
        setLayout(new BorderLayout(0, 30));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // top blue section which contains return button and return menu label
        JPanel topSection = createTopSection();
        add(topSection, BorderLayout.NORTH);

        add(Box.createHorizontalStrut(20), BorderLayout.WEST);

        JPanel journalPanel = createJournalJPanel();
        add(journalPanel, BorderLayout.CENTER);
    }

    private JPanel createTopSection() {
        // blue panel
        JPanel panel = new JPanel();
        panel.setBackground(darkBlue);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new java.awt.Dimension(this.getWidth(), 70));
        
        // back button
        JButton backButton = new JButton(" ←"); 
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO <3
                 */
            }
        });

        // white return menu label
        JLabel profileLabel = new JLabel("Return to Menu");
        profileLabel.setFont(new Font("Arial", Font.BOLD, 25));
        profileLabel.setForeground(Color.WHITE);
        panel.add(backButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(profileLabel);

        return panel; 
    }

    private JPanel createJournalJPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 

        // Title Label
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));

        // Title Text Field
        JTextField titleField = new JTextField();
        titleField.setBackground(lightGray);
        titleField.setFont(new Font("Arial", Font.PLAIN, 18));
        titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        titleField.setAlignmentX(LEFT_ALIGNMENT); 
        panel.add(titleField);
        panel.add(Box.createVerticalStrut(20));

        // City Label
        JLabel cityLabel = new JLabel("City Name:");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 20));
        cityLabel.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(cityLabel);
        panel.add(Box.createVerticalStrut(5));

        // City Text Field
        JTextField cityField = new JTextField();
        cityField.setBackground(lightGray);
        cityField.setFont(new Font("Arial", Font.PLAIN, 18));
        cityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        cityField.setAlignmentX(LEFT_ALIGNMENT); 
        panel.add(cityField);
        panel.add(Box.createVerticalStrut(20));

        // Journal Entry Label
        JLabel journalLabel = new JLabel("Journal Entry:");
        journalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        journalLabel.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(journalLabel);
        panel.add(Box.createVerticalStrut(5));

        // Journal Entry Text Area
        JTextArea journalEntry = new JTextArea();
        journalEntry.setBackground(lightGray);
        journalEntry.setFont(new Font("Arial", Font.PLAIN, 18));
        journalEntry.setLineWrap(true);
        journalEntry.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(journalEntry);
        scrollPane.setPreferredSize(new Dimension(panel.getWidth(), 300));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        scrollPane.setAlignmentX(LEFT_ALIGNMENT); 
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);

        // Privacy Combo Box
        JCheckBox submitPrivately=new JCheckBox("Submit Privately");
        submitPrivately.setFont(new Font("Arial", Font.BOLD, 20));
        buttonPanel.add(submitPrivately);

        // Attach Photo Button
        JButton attachPhotoButton = new RoundedButton("Attach Photo",20);
        attachPhotoButton.setBackground(darkBlue);
        attachPhotoButton.setForeground(Color.WHITE);
        attachPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                /*
                 * TODO <3
                 */
            }
        });
        buttonPanel.add(attachPhotoButton);

        // Submit Button
        JButton submitButton = new RoundedButton("Submit",20);
        submitButton.setBackground(darkBlue);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                /*
                 * TODO !!Dont forget checking submit private comboBox and if city is valid or not <3
                 */
            }
        });
        buttonPanel.add(submitButton);

        panel.add(buttonPanel);

        return panel;
    }
}
