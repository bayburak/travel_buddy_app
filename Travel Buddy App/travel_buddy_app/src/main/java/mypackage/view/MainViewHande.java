package view;

import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainViewHande {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new emailCode();
            JFrame frame = new JFrame("A");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Search Panel for FIND TRAVEL BUDDY
            //Creating genericUserPanels objects with user lists
            //TODO
            FindTravelBuddy buddySearchPanel = new FindTravelBuddy(
                Arrays.asList("Apple", "Banana", "Orange", "Pineapple", "Mango", "Grapes")
            );

            frame.getContentPane().add(buddySearchPanel);

            //Search Panel for EXPLORE
            //Creating genericEntryPanels objects with user lists
            //TODO
            FindTravelBuddy exploreSearchPanel = new FindTravelBuddy(
                Arrays.asList("Apple", "Banana", "Orange", "Pineapple", "Mango", "Grapes")
            );

            frame.getContentPane().add(exploreSearchPanel);

            allJournals mainPanel = new allJournals();
            frame.add(mainPanel);
            frame.setContentPane(mainPanel);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }
}
