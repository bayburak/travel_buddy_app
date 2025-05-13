package mypackage.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import mypackage.service.JournalDatabaseService;
import mypackage.service.StorageService;
import mypackage.service.UserDatabaseService;

import java.io.IOException;


public class User {


    private String userID;
    private String nameSurname;
    private String password;
    private String e_mail;
    private String username;
    private String aboutMe;

    //Stores IDs of the objects
    private List<String> entries = new ArrayList<>();
    private List<String> saved = new ArrayList<>();
    private List<String> following = new ArrayList<>();
    private List <String> followers = new ArrayList<>();

    private String profilePicURL;

    //required for firebase
    public User(){

    }
    //TODO generate id //TODO handle profile pic
    public User(String nameSurname, String password, String e_mail, String username) throws IOException{
        this.nameSurname = nameSurname;
        this.password = password;
        this.e_mail = e_mail;
        this.username = username;
        this.aboutMe = "";
        setDefaultPic();

        UserDatabaseService.incrementNumberOfUsers();
        this.userID = UserDatabaseService.NumberOfUsers; 
    }


    /*                                          */
    /*              STATIC METHODS              */
    /*                                          */

    

    public static User getUserByID(String UserID) throws InterruptedException, ExecutionException {
        return UserDatabaseService.getUserByID(UserID);
        
    }
    //returns null when the user des not exist
    public static User getUserbyUsername( String username) throws InterruptedException, ExecutionException{
        return UserDatabaseService.getUserByUsername(username);
    }

    public static List<User> searchUsersByKeyword(String keyword) throws InterruptedException, ExecutionException{
        return UserDatabaseService.searchUsersByKeyword(keyword);
    }


    /*                                          */
    /*              INTANCE METHODS             */
    /*                                          */

    public void addUsertoDatabase() {
        UserDatabaseService.createUser(this); 
    }

    public void deleteUser(){
        UserDatabaseService.deleteUser(this.getUserID());
    }

    public void updateUserProfile(String nameSurname, String aboutMe, String username, String e_mail) {
        this.setAboutMe(aboutMe);
        this.setE_mail(e_mail);
        this.setNameSurname(nameSurname);
        this.setUsername(username);
        
        UserDatabaseService.updateUserProfile(this);
    }

    public void setProfilePic(String FilePath) throws IOException{
        String storagePath = "profile_photos/user" + this.getUserID();
        this.profilePicURL = StorageService.uploadFile(FilePath, storagePath);
        this.updateUserProfile(nameSurname, aboutMe, username, e_mail); 
    }

    
    public void setDefaultPic(){
        this.profilePicURL = "https://firebasestorage.googleapis.com/v0/b/travelbuddyapp-35c7b.firebasestorage.app/o/profile_photos%2Fdefault.jpeg?alt=media&token=6a937830-968e-4f9a-9d1e-0ed330fbbe91"; 
        
    }

    public void followUser(String targetUserID) {
        UserDatabaseService.followUser(this.getUserID(), targetUserID);
        
    }

    public void unfollowUser(String targetUserID) {
        UserDatabaseService.unfollowUser(this.getUserID(), targetUserID);
        
    }

    public void addToSaved(String EntryID) {
        UserDatabaseService.saveEntryForUser(this.getUserID(), EntryID);
        
    }

    public void removeFromSaved(String EntryID) {
        UserDatabaseService.unsaveEntryForUser(this.getUserID(), EntryID);
        
    }

    public List<User> getFollowersObjectArray() throws InterruptedException, ExecutionException{
        return UserDatabaseService.getFollowers(this.getUserID());
    }
    public List<User> getFollowingsObjextArray() throws InterruptedException, ExecutionException{
        return UserDatabaseService.getFollowing(this.userID);
    }

    

    public List<JournalEntry> getSavedEntries() throws InterruptedException, ExecutionException{
        return JournalDatabaseService.getSavedEntriesForUser(this.getUserID());
    }

    
    public List<JournalEntry> getUserEntries() throws InterruptedException, ExecutionException {
        
        return JournalDatabaseService.getEntriesByUser(this.userID);
    }
  
    public List<JournalEntry> getPublicEntries() throws InterruptedException, ExecutionException {
        
        return JournalDatabaseService.getPublicEntries(this.getUserID());
    }

    
    


    

    
    /*                                          */
    /*          GETTER, SETTER METHODS          */
    /*                                          */

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getNameSurname() { return nameSurname; }
    public void setNameSurname(String nameSurname) { this.nameSurname = nameSurname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getE_mail() { return e_mail; }
    public void setE_mail(String e_mail) { this.e_mail = e_mail; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAboutMe() { return aboutMe; }
    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }

    public List<String> getEntries() { return entries; }
    public void setEntries(List<String> entries) { this.entries = entries; }

    public List<String> getSaved() { return saved; }
    public void setSaved(List<String> saved) { this.saved = saved; }

    public List<String> getFollowing() { return following; }
    public void setFollowing(List<String> following) { this.following = following; }

    public List<String> getFollowers() { return followers; }
    public void setFollowers(List<String> followers) { this.followers = followers; }

    public String getPhotoURL() { return profilePicURL;}
    public void setPhotoURL(String newURL) { this.profilePicURL = newURL;}


    public String toString(){
        return this.getNameSurname() + " "+this.getUsername() + " " + this.getUserID();
    }

}
