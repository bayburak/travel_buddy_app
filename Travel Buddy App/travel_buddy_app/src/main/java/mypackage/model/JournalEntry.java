package mypackage.model;


import java.io.File;
import java.time.*;
import java.util.List;

public class JournalEntry {
    
    private String entryID;
    private String title;
    private String content;
    private boolean isPublic;
    private int noOfFavorites;

    private String cityID;
    private String creationDate;
    private String authorID;

    private List<String> photos;
 
    //TODO generate ID

    /*                                          */
    /*              STATIC METHODS              */
    /*                                          */
    public static JournalEntry createEntry(String title, String content, boolean isPublic, User author, String city, File photo) {
        // Implementation here
        return null;
    }

    public static JournalEntry getJournalEntrybyID(String entryID){
        return null;
    }

    

    /*                                          */
    /*              INTANCE METHODS             */
    /*                                          */
    
    //TODO StorageService class
    public boolean addPhoto(File file, String fileName) {
        // Implementation here
        return false;
    }
    //TODO Storage Service class
    public boolean removePhoto(String fileName) {
        // Implementation here
        return false;
    }

    public boolean updateEntry(String title, String content, boolean isPublic) {
        // Implementation here
        return false;
    }

    public boolean deleteEntry() {
        // Implementation here
        return false;
    }

    

    

    /*                                          */
    /*          GETTER, SETTER METHODS          */
    /*                                          */

    public String getID() { return entryID; }
    public void setEntryID(String entryID) { this.entryID = entryID; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    
    public int getNoOfFavorites() { return noOfFavorites; }
    public void setNoOfFavorites(int noOfFavorites) { this.noOfFavorites = noOfFavorites; }
    
    public String getCityID() { return cityID; }
    public void setCity(String cityID) { this.cityID = cityID; }
    
    public String getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate.toString(); }
    
    public String getAuthorID() { return authorID; }
    public void setAuthor(String authorID) { this.authorID = authorID; }
}
