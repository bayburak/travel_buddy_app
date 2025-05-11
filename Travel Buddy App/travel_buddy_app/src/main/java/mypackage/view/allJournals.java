package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class allJournals extends JPanel implements ActionListener{

    static Color blue = new Color(34, 86, 153);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    public allJournals() {

        this.setSize(screenSize);
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        //Top Blue
        JPanel topBlue = new JPanel();
        topBlue.setLayout(null);
        topBlue.setBackground(blue);
        topBlue.setBounds(0,0,screenWidth,70);

        //Back button 
        JButton backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(blue);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //TODO
            }
        });
        backButton.setBounds(10,0,100,60);
        topBlue.add(backButton);

        //Return to Profile Label
        JLabel returnProfile = new JLabel("Return to Profile");
        returnProfile.setFont(new Font("Arial",Font.BOLD,24));
        returnProfile.setForeground(Color.WHITE);
        returnProfile.setBounds(170,0,500,70);
        topBlue.add(returnProfile);

        this.add(topBlue);

        //All Journals Label
        JLabel allLabel = new JLabel("All Journals");
        allLabel.setFont(new Font("Arial",Font.BOLD,24));
        allLabel.setForeground(Color.DARK_GRAY);
        allLabel.setBounds(30,80,200,50);
        this.add(allLabel);

        //Line
        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.DARK_GRAY);
                g2.setStroke(new BasicStroke(5));
                g2.drawLine(0, 25, 1600, 25);
            }
        };
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.setBounds(250,80,1600,50);
        this.add(drawingPanel);

        //Journal Entries
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        //The height of the content panel will be determined based on the size of the list containing the entries
        //Height == ArrayList.size() * panel's height
        //TODO
        contentPanel.setPreferredSize(new Dimension(200, 200));
        contentPanel.setBackground(Color.WHITE);

        //Adding the entries using the list of entries
        //TODO
        contentPanel.add(this.entryPanel());
        contentPanel.add(Box.createVerticalStrut(50));
        contentPanel.add(this.entryPanel());
        contentPanel.add(Box.createVerticalStrut(50));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 180, screenWidth-50, screenHeight-200);
        scrollPane.setBorder(null);
        this.add(scrollPane);

    }

    private JPanel entryPanel() {
        return new genericJournalPanels();
        //You can change the code to get the city name, pp, date, etc.
        //TODO
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
