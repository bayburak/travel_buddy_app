package mypackage.controller;

import mypackage.model.City;
import mypackage.model.JournalEntry;
import mypackage.view.PhotoUploader;
import mypackage.view.journalEntry;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public final class JournalController
{
    private JournalController() {}

    public static void createForm(JFrame mainWindow, City preselectedCity, MapController myClass)
    {
        Container previousView = mainWindow.getContentPane();

        JournalEntry tempEntry = new JournalEntry("", "", true, preselectedCity.getCityID(), Session.getCurrentUserID());
        tempEntry.addEntrytoDatabase(); 

        journalEntry entryForm = new journalEntry(preselectedCity, () -> {
            mainWindow.setContentPane(previousView);
            mainWindow.revalidate();
            mainWindow.repaint();

            try 
            {
                JournalEntry.deleteEntry(tempEntry.getEntryID());
            } catch (Exception ex) {
                System.out.println("Failed to delete entry: " + ex.getMessage());
            }
        }, null, null);

        // 2) Wire attach-photo 
        entryForm.attachBtn.addActionListener(e -> {
            new PhotoUploader(filePath -> {
                if (filePath != null) {
                    try {
                        tempEntry.setPhoto(filePath);
                    } catch (IOException e1) {
                        
                        e1.printStackTrace();
                    }
                } else {
                    System.out.println("Photo upload returned a null URL.");
                }
            });
        });

        // 3) Wire submit
        entryForm.submitBtn.addActionListener(e -> {
            if (validate(mainWindow, entryForm, tempEntry)) {
                mainWindow.setContentPane(previousView);
                mainWindow.revalidate();
                mainWindow.repaint();
                myClass.closePopup();
            } else {
                System.out.println("Validation failed; entry not saved.");
            }
        });

        // 4) Display form
        mainWindow.setContentPane(entryForm);
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    private static boolean validate(JFrame parent, journalEntry form, JournalEntry tempEntry) {
        String title = form.getTitle();
        String body = form.getBody();
        boolean isPrivate = !form.isPrivate();

        if (title.isEmpty())
        {
            popMessage(parent, "Title is missing. Please enter a title.");
            return false; 
        }
        if (body.isEmpty()) 
        {
            popMessage(parent, "You forgot to write your entry!");
            return false;
        }

        tempEntry.updateEntry(title, body, isPrivate);


        popMessage(parent, "Your entry has been saved!");
        return true;
    }

    private static void popMessage(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg,
            "Journal Entry Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}
