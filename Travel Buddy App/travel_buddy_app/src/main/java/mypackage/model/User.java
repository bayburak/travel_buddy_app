package mypackage.model;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class User {


    private String nameSurname;
    private String password;
    private String e_mail;
    private String username;
    private String aboutMe_Text;

    private ArrayList<JournalEntry> entries;
    private ArrayList<JournalEntry> favorites;
    private ArrayList<User> folloing;
    private ArrayList <User> followers;

    private BufferedImage profilePicture;

    public User(String nameSurname, String password, String e_mail, String username) throws IOException{
        this.nameSurname = nameSurname;
        this.password = password;
        this.e_mail = e_mail;
        this.username = username;
        this.aboutMe_Text = "";

        this.entries = new ArrayList<>();
        this.favorites = new ArrayList<>();
        this.folloing = new ArrayList<>();
        this.followers = new ArrayList<>();

        
        this.profilePicture = ImageIO.read(new File("")); //TODO default image ekle
        
    }
    
    

    public String getNameSurname(){ return nameSurname;}
    public String gwtPassword(){ return password;}
    public String getE_mail(){ return e_mail;}
    public String getUsername(){ return username;}
    public String getAboutMe(){ return aboutMe_Text;}

}
