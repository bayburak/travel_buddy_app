package mypackage.service;

import java.io.FileInputStream;
import java.io.IOException;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public abstract class DatabaseService {
    
    protected static DatabaseReference database; 



    public static void initialize() throws IOException{
        FileInputStream serviceAccount = new FileInputStream("Travel Buddy App/travel_buddy_app/serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://travelbuddyapp-35c7b-default-rtdb.europe-west1.firebasedatabase.app/") 
            .build();
        

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized");
            database = FirebaseDatabase.getInstance().getReference();
            UserDatabaseService.getNoOfUsersfromDatabase();
            JournalDatabaseService.getNoOfEntriesfromDatabase();
        }
    }
    
}
