package mypackage.service;

import java.io.FileInputStream;
import java.io.IOException;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public abstract class DatabaseService {
    
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    public DatabaseService() throws IOException{
        initialize();
    }

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
    
}
