package mypackage.controller;

import javax.swing.*;
import java.util.concurrent.ExecutionException;
import mypackage.model.User;
import mypackage.view.allJournals;
import mypackage.view.favorites;
import mypackage.view.profile;

public class ProfileController {

    JFrame host = new JFrame();
    public void open(JFrame host) 
    {
        SwingUtilities.invokeLater(() -> {
            this.host = host;
            JFrame profileFrame = new JFrame("Your Profile");
            profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            profileFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            User currentUser = Session.getCurrentUser();
            profile profilePanel = null;
            try {
                profilePanel = new profile(currentUser);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            profilePanel.getBackButton().addActionListener(e -> {
                profileFrame.dispose();
                host.setVisible(true);
            });

            profilePanel.getEditProfileButton().addActionListener(e -> {
                new editProfileController(profileFrame, currentUser, host).open();
            });

            User user = profilePanel.getUser();

            JFrame displayJournalEntries = new JFrame();
            profilePanel.getJournalEntriesButton().addActionListener(e -> {
                try {
                    allJournals entries;
                    if (Session.getCurrentUserID().equals(user.getUserID())) {
                        entries = new allJournals(currentUser, false);
                    }
                    else {
                        entries = new allJournals(currentUser, true);
                    }
                    entries.getBackButton().addActionListener(a -> {
                        displayJournalEntries.dispose();
                        profileFrame.setVisible( true);
                    });
                    displayJournalEntries.add( entries);
                    displayJournalEntries.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    displayJournalEntries.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
                    displayJournalEntries.setVisible(true);
                    host.setVisible(false);
                    profileFrame.setVisible( false);
                } catch (InterruptedException | ExecutionException e1) {
                    e1.printStackTrace();
                }
            });
            //FavouritesButton 
            JFrame displayFavorites = new JFrame();
            profilePanel.getFavouritesButton().addActionListener(e -> {
                try {
                    favorites favEntries = new favorites(currentUser);
                    favEntries.getBackButton().addActionListener(a -> {
                        displayFavorites.dispose();
                        profileFrame.setVisible( true);
                    });
                    displayFavorites.add( favEntries);
                    displayFavorites.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    displayFavorites.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
                    displayFavorites.setVisible(true);
                    host.setVisible(false);
                    profileFrame.setVisible( false);
                } catch (InterruptedException | ExecutionException e1) {
                    e1.printStackTrace();
                }
            });
            
            profileFrame.setContentPane(profilePanel);
            profileFrame.invalidate();
            profileFrame.pack();
            profileFrame.setLocationRelativeTo(null);
            profileFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            profileFrame.validate();
            profileFrame.toFront();
            profileFrame.setVisible(true);
            host.setVisible(false);
        });
    }

    public JFrame getHost()
    {
        return host;
    }
}
