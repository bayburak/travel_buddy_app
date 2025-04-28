package mypackage.model;


import java.io.File;
import java.time.*;
import java.util.List;

public class JournalEntry {
    
    private int entryID;
    private String title;
    private String content;
    private boolean isPublic;
    private int noOfFavorites;

    private City city;
    private LocalDate creationDate;
    private User author;

    private List<String> photos;
 


    /*                                          */
    /*              STATIC METHODS              */
    /*                                          */
    public static JournalEntry createEntry(String title, String content, boolean isPublic, User author, String city, File photo) {
        // Implementation here
        return null;
    }


    /*                                          */
    /*              INTANCE METHODS             */
    /*                                          */

    public boolean addPhoto(File file, String fileName) {
        // Implementation here
        return false;
    }

    public boolean removePhoto(String fileName) {
        // Implementation here
        return false;
    }

    public boolean updateContent(String title, String content) {
        // Implementation here
        return false;
    }

    public void toggleVisibility() {
        // Implementation here
    }

    

    public boolean updateEntry(String title, String content, boolean isPublic) {
        // Implementation here
        return false;
    }

    public boolean deleteEntry() {
        // Implementation here
        return false;
    }

}
