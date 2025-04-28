package mypackage.service;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mypackage.model.User;

public class DatabaseService {
    

    public static void initialize() throws IOException{
        FileInputStream serviceAccount = new FileInputStream("travel_buddy_app/serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://travel-buddy-cc986-default-rtdb.firebaseio.com/") 
            .build();
        

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized");
        }
    }
    

    public void addUser(User user){
        DatabaseReference ref = FirebaseDatabase
            .getInstance()
            .getReference("users")
            .child(user.getUsername());

        ref.setValueAsync(user);
        System.out.println("User added to Firebase!");
    }

    public void removeUser(User user){

    }

    public void updateUser(User user){

    }
}
