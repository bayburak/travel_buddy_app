package mypackage.model;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class User {

    private int userID;
    private String nameSurname;
    private String password;
    private String e_mail;
    private String username;
    private String aboutMe;

    private ArrayList<JournalEntry> entries = new ArrayList<>();
    private ArrayList<JournalEntry> favorites = new ArrayList<>();
    private ArrayList<User> following = new ArrayList<>();
    private ArrayList <User> followers = new ArrayList<>();

    //private BufferedImage profilePic;


    public User(){

    }

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

    public static User getUserByUsername(String username) {
        // Implementation here
        return null;
    }



    /*                                          */
    /*              INTANCE METHODS             */
    /*                                          */

    public boolean updateUserProfile(String nameSurname, String aboutMe, String username, String profilePic) {
        // Implementation here
        return false;
    }

    public boolean followUser(User targetUser) {
        // Implementation here
        return false;
    }

    public boolean unfollowUser(User targetUser) {
        // Implementation here
        return false;
    }

    public void updatePassword(String newPassword) {
        // Implementation here
    }

    public boolean addToFavorites(JournalEntry journalEntry) {
        // Implementation here
        return false;
    }

    public boolean removeFromFavorites(JournalEntry journalEntry) {
        // Implementation here
        return false;
    }

    public List<JournalEntry> getUserEntries() {
        // Implementation here
        return null;
    }

    public List<JournalEntry> getPublicEntries() {
        // Implementation here
        return null;
    }


    

    
    /*                                          */
    /*              GETTER METHODS              */
    /*                                          */

    public String getNameSurname(){ return nameSurname;}
    public String getPassword(){ return password;}
    public String getE_mail(){ return e_mail;}
    public String getUsername(){ return username;}
    public String getAboutMe(){ return aboutMe;}
    public ArrayList<JournalEntry> getEntries() { return entries;}
    public ArrayList<JournalEntry> getFavorites() { return favorites;}
    public ArrayList<User> getFollowing() { return following;}
    public ArrayList<User> getFollowers() { return followers;}

}
