package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class findTravelbuddy extends JPanel implements ActionListener{

    static Color blue = new Color(34, 86, 153);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    private JTextField searchField;
    private JList<String> resultList;
    private DefaultListModel<String> listModel;
    private List<String> data; //Original data

    public findTravelbuddy(List<String> items) {

        this.setSize(screenSize);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.data = new ArrayList<>(items); // Copy to preserve original list

        //Search bar
        searchField = new JTextField(20);
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        searchBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchBarPanel.add(new JLabel("Find Users:  "), BorderLayout.WEST);
        searchBarPanel.add(searchField, BorderLayout.CENTER);

        //Result list
        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);

        //Enter key listener
        searchField.addActionListener(e -> {
            String query = searchField.getText().trim().toLowerCase();
            updateList(query);
        });

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

        //Explore Label
        JLabel returnProfile = new JLabel("Explore");
        returnProfile.setFont(new Font("Arial",Font.BOLD,24));
        returnProfile.setForeground(Color.WHITE);
        returnProfile.setBounds(130,0,100,70);
        topBlue.add(returnProfile);

        searchBarPanel.setBounds(500,10,800,50);
        topBlue.add(searchBarPanel);

        this.add(topBlue);
        JScrollPane pane = new JScrollPane(resultList);
        pane.setBounds(0,80,screenWidth,screenHeight-200);
        pane.setBorder(null);
        this.add(pane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    private void updateList(String filter) {
        listModel.clear();
        if (filter.isEmpty()) {
            //Do not show anything if search is empty
            return;
        }

        for (String item : data) {
            if (item.toLowerCase().contains(filter)) {
                listModel.addElement(item);
            }
        }
    }

    public void setData(List<String> newData) {
        this.data = new ArrayList<>(newData);
        listModel.clear(); //Reset view
    }
    
}
