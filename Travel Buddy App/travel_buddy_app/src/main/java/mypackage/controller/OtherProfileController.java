package mypackage.controller;

import javax.swing.*;

//delete otherprofilecontroller
//delete otherprofile
//update profile
//update profile controller
//update findtravelbuddy

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import mypackage.model.JournalEntry;
import mypackage.model.User;
import mypackage.view.allJournals;
import mypackage.view.favorites;
import mypackage.view.genericJournalPanels;
import mypackage.view.otherProfile;
import mypackage.view.profile;

public class OtherProfileController {

    
    // JFrame host = new JFrame();
    // public void open(JFrame host) 
    // {
    //     SwingUtilities.invokeLater(() -> {
    //         this.host = host;
    //     });

    // }
    // public void manageOtherProfile( otherProfile profilePanel, ActionEvent e) throws InterruptedException, ExecutionException {
    //     User visitor = Session.getCurrentUser();
    //     User owner = profilePanel.getOwner();
    //     JFrame currentFrame = new JFrame();
    //     currentFrame.add(profilePanel);

    //     profilePanel.getFavButton().addActionListener(a -> {
    //             favorites panelForFavs = null;
    //             try {
    //                 panelForFavs = new favorites(owner);
    //             } catch (InterruptedException | ExecutionException e1) {
    //                 e1.printStackTrace();
    //             }
    //             currentFrame.add(panelForFavs);
    //             currentFrame.repaint();
    //             currentFrame.requestFocus();
    //             currentFrame.setExtendedState( JFrame.MAXIMIZED_BOTH);
    //             currentFrame.setVisible( true);
    //             Container prev = SwingUtilities.getWindowAncestor((Component) (e.getSource()));
    //             prev.setVisible(false);
    //         });

    //     profilePanel.getJournalsButton().addActionListener(new ActionListener() {

    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             try {
    //                 new allJournals(owner,true);
    //             } catch (InterruptedException | ExecutionException e1) {
    //                 e1.printStackTrace();
    //             }
    //         }
            
    //     });

    // }

            //JFrame otherotherProfileFrame = new JFrame( .getName() + "'s Profile");
            //otherProfileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //otherProfileFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            
            //User owner = otherProfile.getOtherUser();
            //otherProfile otherProfilePanel = new otherProfile(owner, visitor);

    //         profilePanel.getBackButton().addActionListener(e -> {
    //             otherProfileFrame.dispose();
    //             host.setVisible(true);
    //         });

    //         profilePanel.getEditProfileButton().addActionListener(e -> {
    //             new editProfileController(otherProfileFrame, currentUser, host).open();
    //         });

    //         JFrame displayJournalEntries = new JFrame();
    //         profilePanel.getJournalEntriesButton().addActionListener(e -> {
    //             try {
    //                 allJournals entries = new allJournals(currentUser, false);
    //                 entries.getBackButton().addActionListener(a -> {
    //                     displayJournalEntries.dispose();
    //                     otherProfileFrame.setVisible( true);
    //                 });
    //                 displayJournalEntries.add( entries);
    //                 displayJournalEntries.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //                 displayJournalEntries.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
    //                 displayJournalEntries.setVisible(true);
    //                 host.setVisible(false);
    //                 otherProfileFrame.setVisible( false);
    //             } catch (InterruptedException | ExecutionException e1) {
    //                 e1.printStackTrace();
    //             }
    //         });
    //         //FavouritesButton 
    //         JFrame displayFavorites = new JFrame();
    //         profilePanel.getFavouritesButton().addActionListener(e -> {
    //             try {
    //                 favorites favEntries = new favorites(currentUser);
    //                 favEntries.getBackButton().addActionListener(a -> {
    //                     displayFavorites.dispose();
    //                     otherProfileFrame.setVisible( true);
    //                 });
    //                 displayFavorites.add( favEntries);
    //                 displayFavorites.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //                 displayFavorites.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
    //                 displayFavorites.setVisible(true);
    //                 host.setVisible(false);
    //                 otherProfileFrame.setVisible( false);
    //             } catch (InterruptedException | ExecutionException e1) {
    //                 e1.printStackTrace();
    //             }
    //         });
            
    //         otherProfileFrame.setContentPane(profilePanel);
    //         otherProfileFrame.invalidate();
    //         otherProfileFrame.pack();
    //         otherProfileFrame.setLocationRelativeTo(null);
    //         otherProfileFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //         otherProfileFrame.validate();
    //         otherProfileFrame.toFront();
    //         otherProfileFrame.setVisible(true);
    //         host.setVisible(false);

    // public JFrame getHost()
    // {
    //     return host;
    // }
}
