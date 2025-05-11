package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class genericUserPanel extends JPanel{
    
    static Color blueBack = new Color(204,228,252);
    static Color blueFront = new Color(180,204,244);
    //variables to keep track of the users entries' feautures
    //date, city name, pp, etc.
    //JournalEntry object
    //TODO

    public genericUserPanel() {
        this.setBackground(blueBack);
        //this.setSize(new Dimension(200,200));
        this.setLayout(null);

        //TODO Profile Picture Button

        //Name Panel
        JPanel namePanel = new JPanel();
        namePanel.setBackground(blueFront);
        namePanel.setSize(300,300);
        namePanel.setBounds(20,120,260,150);
        this.add(namePanel);
        this.setVisible(true);

    }
}
