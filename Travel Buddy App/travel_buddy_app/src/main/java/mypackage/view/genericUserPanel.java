package mypackage.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import mypackage.model.JournalEntry;
import mypackage.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class genericUserPanel extends JPanel{
    
    static Color blueBack = new Color(204,228,252);
    static Color blueFront = new Color(180,204,244);
    private BufferedImage image;
    //variables to keep track of the users entries' feautures
    //date, city name, pp, etc.
    //JournalEntry object
    //TODO

    public genericUserPanel(User user) {


        
        this.setBackground(blueBack);
        this.setSize(new Dimension(200,200));
        this.setLayout(null);

        //TODO Profile Picture Button

        try {
            URL imageUrl = new URL(user.getPhotoURL());
            image = ImageIO.read(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BufferedImage roundedImage = (RoundImage.makePerfectCircle(image, 200,Color.GRAY,1));
        JLabel imageLabel = new JLabel(new ImageIcon(roundedImage));
        imageLabel.setBounds(10,10,20,20);
        this.add(imageLabel);
        


        //Name Panel
        JPanel namePanel = new JPanel();
        namePanel.setBackground(blueFront);
        namePanel.setSize(300,300);
        namePanel.setBounds(80,150,260,150);
        namePanel.setLayout(null);

        JLabel name = new JLabel(user.getNameSurname());
        JLabel username = new JLabel(user.getUsername());
        name.setForeground(Color.BLACK); name.setFont(new Font("Arial",Font.BOLD,18));
        username.setForeground(Color.BLACK); username.setFont(new Font("Arial",Font.PLAIN,12));
        name.setBounds(10,20,400,100);
        username.setBounds(10,80,400,100);
        namePanel.add(name);
        namePanel.add(username);
        this.add(namePanel);
    }
}
