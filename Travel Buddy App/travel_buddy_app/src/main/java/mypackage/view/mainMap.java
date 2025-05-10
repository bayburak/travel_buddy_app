package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainMap extends JPanel implements ActionListener {

    static Color darkBlue = new Color(19, 49, 88);
    static Color lightBlue = new Color(34, 86, 153);
    static Font buttonFont = new Font("Arial",Font.BOLD,12);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    public MainMap() {
        this.setSize(screenSize);
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        //Top Blue
        JPanel topBlue = new JPanel();
        topBlue.setLayout(null);
        topBlue.setBackground(lightBlue);
        topBlue.setBounds(0,0,screenWidth,70);


        //Explore Button
        RoundedButton explore = new RoundedButton("Explore", 20);
        explore.setFont(buttonFont);
        explore.setBackground(darkBlue);
        explore.setForeground(Color.WHITE);
        explore.setBounds(400,15,400,40);
        explore.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
            
        });
        topBlue.add(explore);

        //Find Travel Buddies Button
        RoundedButton findBuddies = new RoundedButton("Find Travel Buddies", 20);
        findBuddies.setFont(buttonFont);
        findBuddies.setBackground(darkBlue);
        findBuddies.setForeground(Color.WHITE);
        findBuddies.setBounds(screenWidth-800,15,400,40);
        findBuddies.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
            
        });
        topBlue.add(findBuddies);
        
        this.add(topBlue);

        //Profile Picture
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }    
}
