package mypackage.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mypackage.model.JournalEntry;
import mypackage.model.User;
import mypackage.utils.FirebasePaths;

public class DatabaseService {
    
    private DatabaseReference database;

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
        DatabaseReference ref = FirebasePaths.userpath;

        ref.child(user.getUsername()).setValue(ref, null);

        System.out.println("User added to Firebase!");
    }

    // ---------------- User Management ----------------
    public void createUser(String userId, User user) {
        
    }

    public void getUserById(String userId, ValueEventListener listener) {

    }

    public void updateUserProfile(String userId, Map<String, Object> updates) {
       
    }

    public void deleteUser(String userId) {
        
    }

    // ---------------- Journal Entry Management ----------------
    public void createJournalEntry(String userId, JournalEntry entry) {
      
    }

    public void getJournalEntryById(String entryId, ValueEventListener listener) {
        
    }

    public void updateJournalEntry(String entryId, Map<String, Object> updates) {
      
    }

    public void deleteJournalEntry(String entryId, String userId) {
        
    }

    public void getEntriesByUser(String userId, ValueEventListener listener) {
  
    }

    public void getPublicEntries(ValueEventListener listener) {
        
    }

    // ---------------- Saved Entries ----------------
    public void saveEntryForUser(String userId, String entryId) {
        
    }

    public void unsaveEntryForUser(String userId, String entryId) {
        
    }

    public void getSavedEntriesForUser(String userId, ValueEventListener listener) {
        
    }

    // ---------------- Follow System ----------------
    public void followUser(String followerId, String followeeId) {
       
    }

    public void unfollowUser(String followerId, String followeeId) {

    }

    public void getFollowers(String userId, ValueEventListener listener) {
        
    }

    public void getFollowing(String userId, ValueEventListener listener) {

    }

    // ---------------- Utility Methods ----------------
    public void entryBelongsToUser(String entryId, String userId, ValueEventListener listener) {
        database.child("users").child(userId).child("entries").child(entryId).addListenerForSingleValueEvent(listener);
    }

    public void isEntrySavedByUser(String entryId, String userId, ValueEventListener listener) {
        database.child("users").child(userId).child("savedEntries").child(entryId).addListenerForSingleValueEvent(listener);
    }

    public void getCityDataForEntry(String entryId, ValueEventListener listener) {
        database.child("journalEntries").child(entryId).child("city").addListenerForSingleValueEvent(listener);
    }
}
