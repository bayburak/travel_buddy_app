package mypackage.app;

import javax.swing.JFrame;
import javax.swing.JPanel;


import mypackage.service.MapService;

public class Main {

    

    public static void main( String[] args )
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.setSize(900, 900);
        try{
            JPanel mapPanel = MapService.getMapPanel();
            frame.add(mapPanel);
        }catch(Exception e){
            e.printStackTrace();
        }
        frame.setVisible(true);
        
    }




}
