package mypackage.model;



import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

import mypackage.service.JournalDatabaseService;
import mypackage.service.StorageService;

public class JournalEntry {
    
    private String entryID;
    private String title;
    private String content;

    
    private boolean publicEntry;

    private int noOfFavorites;

    private String cityID;
    private String creationDate;
    private String authorID;

    private String photoURL;

    public JournalEntry(){

    }
    public JournalEntry(String title, String content, boolean isPublic, String CityID, String authorID){
        this.title = title;
        this.content = content;
        this.publicEntry = isPublic;
        this.cityID = CityID;
        this.creationDate = CreationDate();
        this.authorID = authorID;

        JournalDatabaseService.incrementNumberOfEntries();
        this.entryID = JournalDatabaseService.NumberOfEntries;

        this.photoURL = null;
    }

 
    

    /*                                          */
    /*              STATIC METHODS              */
    /*                                          */
    

    public static JournalEntry getJournalEntrybyID(String entryID) throws InterruptedException, ExecutionException{
    
        return JournalDatabaseService.getJournalEntryById(entryID);
       
        
    }

    public static void deleteEntry(String entryID) {
        JournalDatabaseService.deleteJournalEntry(entryID, null);
        
    }

    public static String CreationDate(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);

        return formattedDate;
    }
    public static List<JournalEntry> getEntriesByCityID(String cityID) throws InterruptedException, ExecutionException{
        return JournalDatabaseService.getEntriesByCityID(cityID);
    }

    public static List<JournalEntry> getTopEntries() throws InterruptedException, ExecutionException{
        return JournalDatabaseService.getTopFavoritedEntries();
    }

    

    /*                                          */
    /*              INTANCE METHODS             */
    /*                                          */
    
    public void addEntrytoDatabase() {
        JournalDatabaseService.createJournalEntry(this);
    }


    
    public void setPhoto(String filePath) throws IOException {
        String storagePath = "entry_photos/entry" + this.getEntryID();
        this.photoURL = StorageService.uploadFile(filePath, storagePath);
        this.updateEntry(title,content, publicEntry);
        
    }
    
    public void removePhoto(String fileName) {
        String storagePath = "entry_photos/entry" + this.getEntryID();
        StorageService.deleteFile(storagePath);
        this.photoURL = null;
    }

    public void updateEntry(String title, String content, boolean isPublic) {
        this.setTitle(title);
        this.setContent(content);
        this.setPublicEntry(isPublic);

        JournalDatabaseService.updateJournalEntry(this);
    }



    

    

    

    /*                                          */
    /*          GETTER, SETTER METHODS          */
    /*                                          */

    public String getEntryID() { return entryID; }
    public void setEntryID(String entryID) { this.entryID = entryID; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    
    public boolean isPublicEntry() { return publicEntry; }
    public void setPublicEntry(boolean publicEntry) { this.publicEntry = publicEntry; }
    
    
    public int getNoOfFavorites() { return noOfFavorites; }
    public void setNoOfFavorites(int noOfFavorites) { this.noOfFavorites = noOfFavorites; }
    
    public String getCityID() { return cityID; }
    public void setCityID(String cityID) { this.cityID = cityID; }
    
    public String getCreationDate() { return creationDate; }
    public void setCreationDate(String creationDate) { this.creationDate = creationDate.toString(); }
    
    public String getAuthorID() { return authorID; }
    public void setAuthorID(String authorID) { this.authorID = authorID; }

    public String getPhotoURL(){ return photoURL;}
    public void setPhotoURL(String photoURL) {this.photoURL = photoURL; }


    public String toString(){
        return this.title + " " + this.getContent(); 
    }

    public boolean equal(Object obj){
        User user = (User) obg;
        return user.getUserID().equals(this.getUserID());
    }
}
