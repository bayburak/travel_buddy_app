package mypackage.service;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.firebase.database.*;

import mypackage.model.JournalEntry;
import mypackage.view.journalEntry;

public class JournalDatabaseService extends DatabaseService {
    

    public JournalDatabaseService() throws IOException{
        super();
    }


    private final static DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public void createJournalEntry(String userId, JournalEntry entry) {
     
        database.child("journalEntries").child(entry.getID()).setValue(entry, null);
        database.child("users").child(userId).child("entries").child(entry.getID()).setValue(true, null);
    }


    public void updateJournalEntry(JournalEntry entry) {
      database.child("journalEntries").child(entry.getID()).setValue(entry, null);
    }
    //TODO favorites
    public void deleteJournalEntry(String entryId) {
        database.child("journalEntries").child(entryId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get authorID
                    String authorId = dataSnapshot.child("author").getValue(String.class);
                    
                    // Remove entry reference from author's entries
                    if (authorId != null) {
                        database.child("users").child(authorId).child("entries").child(entryId).removeValue(null);
                    }
                    
                    // Find all users who saved this entry and remove it
                    database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot usersSnapshot) {
                            for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                                String userId = userSnapshot.getKey();
                                if (userSnapshot.child("savedEntries").hasChild(entryId)) {
                                    database.child("users").child(userId).child("savedEntries").child(entryId).removeValue(null);
                                }
                            }
                            
                            // Finally, delete the entry
                            database.child("journalEntries").child(entryId).removeValue(null);
                        }
                        
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public JournalEntry getJournalEntryById(String entryId) throws InterruptedException, ExecutionException {
        return getentry(entryId).get();
    }
    private CompletableFuture<JournalEntry> getentry(String entryId){
        CompletableFuture<JournalEntry> future = new CompletableFuture<>();
        database.child("journalEntries").child(entryId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    JournalEntry entry = dataSnapshot.getValue(JournalEntry.class);
                    future.complete(entry);
                } else {
                    future.complete(null);
                }
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException(databaseError.getMessage()));
            }
        });

        return future;
    }
    
    
    //TODO eksik ekss, also might put into UserService class
    public List<JournalEntry> getEntriesByUser(String userId) throws InterruptedException, ExecutionException {
        return getentriesbyuser(userId).get();
    }
    private CompletableFuture<List<JournalEntry>> getentriesbyuser(String userId){
      
        CompletableFuture<List<JournalEntry>> future = new CompletableFuture<>();

        database.child("users").child(userId).child("entries").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> entryIds = new ArrayList<>();
                    for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                        entryIds.add(entrySnapshot.getKey());
                    }

                    if (entryIds.isEmpty()) {
                        future.complete(new ArrayList<>());
                        return;
                    }

                    // Create a list of futures for fetching each journal entry
                    List<JournalEntry> userEntries = new ArrayList<>();
                    for (String entryId : entryIds) {
                        JournalEntry entry;
                        try {
                            entry = getJournalEntryById(entryId);
                            if (entry != null) userEntries.add(entry);
                        } catch (InterruptedException | ExecutionException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        
                    }


                    
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    future.completeExceptionally(new RuntimeException(databaseError.getMessage()));
                }
            });

        return future;


    }
    
    //TODO might put into to userService Class
    public void getPublicEntries(String userID ) {
        
    }
    //TODO Finish this
    public void getJournalEntriesByCity(String cityName) {
        database.child("journalEntries").orderByChild("city/name").equalTo(cityName)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<JournalEntry> cityEntries = new ArrayList<>();
                    for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                        JournalEntry entry = entrySnapshot.getValue(JournalEntry.class);
                        // Only add public entries
                        if (entry != null && entry.isPublic()) {
                            cityEntries.add(entry);
                        }
                    }
                    //listener.onEntriesRetrieved(cityEntries);
                }
                
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //listener.onError(databaseError.getMessage());
                }
            });
    }       
}
