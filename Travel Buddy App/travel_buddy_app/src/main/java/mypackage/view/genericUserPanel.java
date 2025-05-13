package mypackage.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import mypackage.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
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
            URI uri = URI.create("https://firebasestorage.googleapis.com/v0/b/travelbuddyapp-35c7b.firebasestorage.app/o/profile_photos%2Fdefault.jpeg?alt=media&token=6a937830-968e-4f9a-9d1e-0ed330fbbe91");
            URL url = uri.toURL();  // Preferred over new URL(String)
            
            try (InputStream in = url.openStream()) {
                
                image = ImageIO.read(in);

                if (image != null) {
                    System.out.println("Image successfully read!");
                } else {
                    System.out.println("Failed to decode the image.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        BufferedImage roundedImage = (RoundImage.makePerfectCircle(image, 80,Color.WHITE,2));
        JLabel imageLabel = new JLabel(new ImageIcon(roundedImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBounds(150,30,100,100);
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

        //Visit Profile Button
        RoundedButton visit = new RoundedButton("Visit Profile",20);
        visit.setForeground(Color.WHITE);
        visit.setBackground(new Color(55,127,188));
        visit.setBorder(null);
        visit.setFont(new Font("Arial",Font.BOLD,12));
        visit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
            
        });
        visit.setBounds(100,320,200,50);
        this.add(visit);
    }
}
