package mypackage.view;

import mypackage.model.City;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * A blue/grey styled journal-entry form with:
 *  • optional preset City (locked if non-null)
 *  • Attach Photo
 *  • Submit Privately checkbox
 *  • Return to Menu back arrow
 */
public class journalEntry extends JPanel {
    private final Color lightGray = new Color(217, 217, 217);
    private final Color darkBlue  = new Color(34, 86, 153);

    private final JTextField titleField = new JTextField();
    private final JTextField cityField  = new JTextField();
    private final JTextArea  bodyArea   = new JTextArea();
    private final JCheckBox  privateBox = new JCheckBox("Submit Privately");
    public final JButton    attachBtn  = new RoundedButton("Attach Photo", 20);
    public final JButton    submitBtn  = new RoundedButton("Submit",      20);


    /**
     * @param presetCity      if non-null, city is prefilled & locked
     * @param onBack          invoked when back arrow is clicked
     * @param onAttachPhoto   invoked when Attach Photo clicked
     * @param onSubmit        invoked when Submit clicked
     */
    public journalEntry(
        City presetCity,
        Runnable onBack,
        Runnable onAttachPhoto,
        Runnable onSubmit
    ) {
        super(new BorderLayout(0, 30));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // Top section with back arrow
        JPanel topSection = createTopSection(onBack);
        add(topSection, BorderLayout.NORTH);

        // Main form section
        add(createFormSection(presetCity, onAttachPhoto, onSubmit), BorderLayout.CENTER);
    }

    private JPanel createTopSection(Runnable onBack)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(darkBlue);
        panel.setPreferredSize(new Dimension(0, 70));

        JButton backBtn = new JButton("←");
        backBtn.setFont(new Font("Arial", Font.BOLD, 50));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        backBtn.setContentAreaFilled(false);
        backBtn.addActionListener(e -> onBack.run());
        panel.add(backBtn);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel label = new JLabel("Return to Menu");
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setForeground(Color.WHITE);
        panel.add(label);

        return panel;
    }

    private JPanel createFormSection(
        City presetCity,
        Runnable onAttachPhoto,
        Runnable onSubmit
    ) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        panel.add(createLabel("Title:"));
        panel.add(Box.createVerticalStrut(5));
        styleField(titleField);
        panel.add(titleField);
        panel.add(Box.createVerticalStrut(20));

        // City
        panel.add(createLabel("City Name:"));
        panel.add(Box.createVerticalStrut(5));
        styleField(cityField);
        if (presetCity != null) {
            cityField.setText(presetCity.getName());
            cityField.setEditable(false);
        }
        panel.add(cityField);
        panel.add(Box.createVerticalStrut(20));

        // Body
        panel.add(createLabel("Journal Entry:"));
        panel.add(Box.createVerticalStrut(5));
        bodyArea.setFont(new Font("Arial", Font.PLAIN, 18));
        bodyArea.setBackground(lightGray);
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(bodyArea);
        scroll.setPreferredSize(new Dimension(0, 200));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        scroll.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(scroll);
        panel.add(Box.createVerticalStrut(20));

        // Bottom controls
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        bottom.setBackground(Color.WHITE);
        privateBox.setFont(new Font("Arial", Font.PLAIN, 18));
        bottom.add(privateBox);

        styleButton(attachBtn);
        attachBtn.addActionListener(e -> onAttachPhoto.run());
        bottom.add(attachBtn);

        styleButton(submitBtn);
        submitBtn.addActionListener(e -> onSubmit.run());
        bottom.add(submitBtn);

        bottom.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(bottom);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 18));
        field.setBackground(lightGray);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        field.setAlignmentX(LEFT_ALIGNMENT);
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 18));
        b.setBackground(darkBlue);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setAlignmentY(TOP_ALIGNMENT);
    }


    /** Title entered */
    public String getTitle()    { return titleField.getText().trim(); }
    /** City entered or preset */
    public String getCityName() { return cityField.getText().trim();  }
    /** Body text */
    public String getBody()     { return bodyArea.getText().trim(); }
    /** Private checkbox */
    public boolean isPrivate()  { return privateBox.isSelected(); }
}
