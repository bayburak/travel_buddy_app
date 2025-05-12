package mypackage.controller;

import mypackage.model.City;
import mypackage.model.JournalEntry;
import mypackage.view.PhotoUploader;
import mypackage.view.journalEntry;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Shows the journal-entry form, handles validation/persistence,
 * and restores the previous view.
 */
public final class JournalController {
    private JournalController() {}

    public static void open(JFrame host, City presetCity)
    {
        Container previous = host.getContentPane();

        JournalEntry currentEntry = new JournalEntry("", "", true, presetCity.getCityID(), Session.getCurrentUserID());


        // 1) create form with only back callback
        journalEntry form = new journalEntry(presetCity, () -> {
            host.setContentPane(previous);
            host.revalidate(); host.repaint();
            JournalEntry.deleteEntry(currentEntry.getEntryID());
        }, null, null);

        // 2) wire attach-photo 
        form.attachBtn.addActionListener(e ->
            new PhotoUploader(url -> form.setPhotoURL(url))
        );

        // 3) wire submit
        form.submitBtn.addActionListener(e -> {
            if (validateAndSave(host, form,currentEntry)) {
                host.setContentPane(previous);
                host.revalidate();
                host.repaint();
            }
        });

        // 4) show form
        host.setContentPane(form);
        host.revalidate(); host.repaint();
    }

    private static boolean validateAndSave(JFrame parent, journalEntry ui, JournalEntry currenEntry)
    {
        String title = ui.getTitle();
        String cityName = ui.getCityName();
        String body = ui.getBody();
        boolean isPriv = ui.isPrivate();
        String photoURL = ui.getPhotoURL();

        if (title.isEmpty())
        { 
            showMessage(parent, "Please enter a title.");
            return false; 
        }
        if (body.isEmpty())
        { 
            showMessage(parent, "Please write your entry.");   
            return false;
        }
        currenEntry.updateEntry(title, body, isPriv);
        try 
        {
            currenEntry.setPhoto(photoURL);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        showMessage(parent, "Entry saved!");
        return true;
    }

    private static void showMessage(Component parent, String msg) 
    {
        JOptionPane.showMessageDialog(parent, msg,
            "Journal Entry", JOptionPane.INFORMATION_MESSAGE);
    }
}
