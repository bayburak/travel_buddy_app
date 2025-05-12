/*  src/main/java/mypackage/view/MainViewHande.java  */
package mypackage.view;

import mypackage.controller.MapController;

import javax.swing.*;
import java.io.IOException;


public class ActualMap extends JFrame {

    public ActualMap() 
    {
        super("Map menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        try 
        {
            new MapController(this);
        } 
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(this,
                    "Failed to load map data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }

}
