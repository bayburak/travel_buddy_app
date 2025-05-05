package mypackage.model;

import java.util.ArrayList;
import java.util.List;


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

    //required gor firebase
    public User(){

    }
    //TODO generate id
    public User(String nameSurname, String password, String e_mail, String username) throws IOException{
        this.nameSurname = nameSurname;
        this.password = password;
        this.e_mail = e_mail;
        this.username = username;
        this.aboutMe = "";

        
        //this.profilePic = ImageIO.read(new File("")); //TODO default image ekle
        
    }

    /*                                          */
    /*              STATIC METHODS              */
    /*                                          */

    public static User createUser(String username, String nameSurname, String email, String password, String aboutMe, String profilePic) {
        // Implementation here
        return null;
    }

    public static User getUserByID(String UserID) {
        // Implementation here
        return null;
    }



    /*                                          */
    /*              INTANCE METHODS             */
    /*                                          */

    public boolean deleteUser(String userID){
        return false;
    }

    public boolean updateUserProfile(String nameSurname, String aboutMe, String username, String profilePic) {
        // Implementation here
        return false;
    }

    public boolean followUser(User targetUser) {
        
        return false;
    }

    public boolean unfollowUser(User targetUser) {
        
        return false;
    }

    public List<String> getFollowersIDs(){
        return null;
    }
    public List<String> getFollowingIDs(){
        return null;
    }

    public boolean addToSaved(JournalEntry journalEntry) {
        
        return false;
    }

    public boolean removeFromSaved(JournalEntry journalEntry) {
        
        return false;
    }

    public List<JournalEntry> getSavedEntries(){
        return null;
    }

    //JournalEntryservice
    public List<JournalEntry> getUserEntries() {
        
        return null;
    }
    //JournalEntryService
    public List<JournalEntry> getPublicEntries() {
        
        return null;
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

}
