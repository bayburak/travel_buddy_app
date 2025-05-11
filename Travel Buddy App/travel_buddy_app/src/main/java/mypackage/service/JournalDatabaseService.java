package mypackage.service;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.firebase.database.*;


import mypackage.model.JournalEntry;


public class JournalDatabaseService extends DatabaseService {
    
    public static String NumberOfEntries;

   

    
    public static void createJournalEntry( JournalEntry entry) {
     
        database.child("journalEntries").child(entry.getEntryID()).setValue(entry, null);
        database.child("users").child(entry.getAuthorID()).child("entries").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                List<String> entries = new ArrayList<>();

                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String id = child.getValue(String.class);
                        if (id != null) entries.add(id);
                    }
                }

                entries.add(entry.getEntryID()); 
                database.child("users").child(entry.getAuthorID()).child("entries").setValue(entries,null);    
            }

            @Override
            public void onCancelled(DatabaseError error){
                System.out.println(error.getMessage());
            }

        });
    }


    public static void updateJournalEntry(JournalEntry entry) {
      database.child("journalEntries").child(entry.getEntryID()).setValue(entry, null);
    }

    
    public static void deleteJournalEntry(String entryId, Runnable callback) {
        database.child("journalEntries").child(entryId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    callback.run();
                    return;
                }
                if (dataSnapshot.exists()) {
                    
                    String authorId = dataSnapshot.child("authorID").getValue(String.class);
                    
                    //Deletes from users
                    if (authorId != null) {
                        database.child("users").child(authorId).child("entries").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot){
                                List<String> list = new ArrayList<>();

                                if(snapshot.exists()){
                                    for(DataSnapshot child: snapshot.getChildren()){
                                        String id = child.getValue(String.class);
                                        if(id != null ){ list.add(id);}
                                        
                                    }
                                }
                                

                                list.remove(entryId); 
                                database.child("users").child(authorId).child("entries").setValue(list,null);
                            }

                            @Override
                            public void onCancelled(DatabaseError error){
                                System.out.println(error.getMessage());
                            }
                        });
                    }
                    
                   //Deletes from saved
                    database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot usersSnapshot) {

                            

                            for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                                String userId = userSnapshot.getKey();
                                DataSnapshot savedEntriesSnapshot = userSnapshot.child("saved");

                                List<String> updatedSavedEntries = new ArrayList<>();

                                for (DataSnapshot entrySnapshot : savedEntriesSnapshot.getChildren()) {
                                    String savedEntryId = entrySnapshot.getValue(String.class);
                                    if (savedEntryId != null && !savedEntryId.equals(entryId)) {
                                        updatedSavedEntries.add(savedEntryId);
                                    }
                                }
                    
                                
                                database.child("users").child(userId).child("saved").setValue(updatedSavedEntries, null);
                            }

                            database.child("journalEntries").child(entryId).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError error, DatabaseReference ref) {
                                    if (error == null) {
                                        callback.run();  // success
                                    } else {
                                        System.out.println("Failed to delete journal entry: " + error.getMessage());
                                    }
                                }
                            });
             
                        }
                        
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("Error removing saved entries: " + databaseError.getMessage());
                        }
                    });


                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    public static JournalEntry getJournalEntryById(String entryId) throws InterruptedException, ExecutionException {
        return getentry(entryId).get();
    }
    private static CompletableFuture<JournalEntry> getentry(String entryId){
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
    
    
    
    public static List<JournalEntry> getEntriesByUser(String userId) throws InterruptedException, ExecutionException {
        return getentriesbyuser(userId).get();
    }
    private static CompletableFuture<List<JournalEntry>> getentriesbyuser(String userId){
      
        CompletableFuture<List<JournalEntry>> future = new CompletableFuture<>();

        database.child("users").child(userId).child("entries")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<CompletableFuture<JournalEntry>> futures = new ArrayList<>();

                    for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                        String entryId = entrySnapshot.getValue(String.class);
                        if (entryId != null) {
                            futures.add(getentry(entryId)); // your existing method
                        }
                    }

                    if (futures.isEmpty()) {
                        future.complete(new ArrayList<JournalEntry>());
                        return;
                    }

                    CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(
                            futures.toArray(new CompletableFuture[futures.size()])
                    );

                    allDoneFuture.thenRun(new Runnable() {
                        @Override
                        public void run() {
                            List<JournalEntry> entries = new ArrayList<JournalEntry>();

                            for (CompletableFuture<JournalEntry> f : futures) {
                                try {
                                    JournalEntry entry = f.get(); // Safe here
                                    if (entry != null) {
                                        entries.add(entry);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace(); // Log and continue
                                }
                            }

                            future.complete(entries);
                        }
                    }).exceptionally(new java.util.function.Function<Throwable, Void>() {
                        @Override
                        public Void apply(Throwable throwable) {
                            future.completeExceptionally(throwable);
                            return null;
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    future.completeExceptionally(new RuntimeException(databaseError.getMessage()));
                }
            });

        return future;


    }
    
    public static List<JournalEntry> getSavedEntriesForUser(String userId) throws InterruptedException, ExecutionException {
        return getSavedEntries(userId).get();
    }
    private static CompletableFuture<List<JournalEntry>> getSavedEntries(String userId) {
        CompletableFuture<List<JournalEntry>> future = new CompletableFuture<>();
    
        database.child("users").child(userId).child("saved")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<CompletableFuture<JournalEntry>> futures = new ArrayList<>();
    
                    for (DataSnapshot savedSnapshot : dataSnapshot.getChildren()) {
                        String entryId = savedSnapshot.getValue(String.class);
                        if (entryId != null) {
                            futures.add(getentry(entryId)); // Your method returning CompletableFuture<JournalEntry>
                        }
                    }
    
                    if (futures.isEmpty()) {
                        future.complete(new ArrayList<JournalEntry>());
                        return;
                    }
    
                    CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(
                            futures.toArray(new CompletableFuture[futures.size()])
                    );
    
                    allDoneFuture.thenRun(new Runnable() {
                        @Override
                        public void run() {
                            List<JournalEntry> entries = new ArrayList<JournalEntry>();
    
                            for (CompletableFuture<JournalEntry> f : futures) {
                                try {
                                    JournalEntry entry = f.get();
                                    if (entry != null) {
                                        entries.add(entry);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
    
                            future.complete(entries);
                        }
                    }).exceptionally(new java.util.function.Function<Throwable, Void>() {
                        @Override
                        public Void apply(Throwable throwable) {
                            future.completeExceptionally(throwable);
                            return null;
                        }
                    });
                }
    
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    future.completeExceptionally(new RuntimeException(databaseError.getMessage()));
                }
            });
    
        return future;
    }
    

    
    public static List<JournalEntry> getPublicEntries(String userId) throws InterruptedException, ExecutionException {
        return getPublicEntriesAsync(userId).get();
    }
    
    private static CompletableFuture<List<JournalEntry>> getPublicEntriesAsync(final String userId) {
        final CompletableFuture<List<JournalEntry>> future = new CompletableFuture<>();
    
        database.child("users").child(userId).child("entries")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<CompletableFuture<JournalEntry>> futures = new ArrayList<>();
    
                    for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                        String entryId = entrySnapshot.getValue(String.class);
                        if (entryId != null) {
                            futures.add(getentry(entryId));  
                        }
                    }
    
                    if (futures.isEmpty()) {
                        future.complete(new ArrayList<JournalEntry>());
                        return;
                    }
    
                    CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(
                            futures.toArray(new CompletableFuture[futures.size()])
                    );
    
                    allDoneFuture.thenRun(new Runnable() {
                        @Override
                        public void run() {
                            List<JournalEntry> publicEntries = new ArrayList<JournalEntry>();
    
                            for (CompletableFuture<JournalEntry> f : futures) {
                                try {
                                    JournalEntry entry = f.get();
                                    if (entry != null && entry.isPublicEntry()) {
                                        publicEntries.add(entry);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace(); // log and skip failed entries
                                }
                            }
    
                            future.complete(publicEntries);
                        }
                    }).exceptionally(new java.util.function.Function<Throwable, Void>() {
                        @Override
                        public Void apply(Throwable throwable) {
                            future.completeExceptionally(throwable);
                            return null;
                        }
                    });
                }
    
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    future.completeExceptionally(new RuntimeException(databaseError.getMessage()));
                }
            });
    
        return future;
    }

    
    public static List<JournalEntry> getEntriesByCityID(String cityID) throws InterruptedException, ExecutionException {
        return getEntriesByCityIDAsync(cityID).get();
    }
    
    private static CompletableFuture<List<JournalEntry>> getEntriesByCityIDAsync(final String cityID) {
        final CompletableFuture<List<JournalEntry>> future = new CompletableFuture<>();
    
        database.child("journalEntries").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<CompletableFuture<JournalEntry>> futures = new ArrayList<>();
    
                for (DataSnapshot entrySnapshot : snapshot.getChildren()) {
                    final String entryID = entrySnapshot.getKey();
                    if (entryID != null) {
                        futures.add(getentry(entryID));
                    }
                }
    
                if (futures.isEmpty()) {
                    future.complete(new ArrayList<JournalEntry>());
                    return;
                }
    
                CompletableFuture<Void> allDone = CompletableFuture.allOf(
                        futures.toArray(new CompletableFuture[futures.size()])
                );
    
                allDone.thenRun(new Runnable() {
                    @Override
                    public void run() {
                        List<JournalEntry> result = new ArrayList<JournalEntry>();
    
                        for (CompletableFuture<JournalEntry> f : futures) {
                            try {
                                JournalEntry entry = f.get();
                                if (entry != null && cityID.equals(entry.getCityID())) {
                                    result.add(entry);
                                }
                            } catch (Exception e) {
                                e.printStackTrace(); // log and skip problematic entry
                            }
                        }
    
                        future.complete(result);
                    }
                }).exceptionally(new java.util.function.Function<Throwable, Void>() {
                    @Override
                    public Void apply(Throwable throwable) {
                        future.completeExceptionally(throwable);
                        return null;
                    }
                });
            }
    
            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(new RuntimeException(error.getMessage()));
            }
        });
    
        return future;
    } 

    

    public static void incrementNoOfFavorites(String entryId) {
        database.child("journalEntries").child(entryId).child("noOfFavorites")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Integer currentValue = snapshot.getValue(Integer.class);
                    if (currentValue == null) currentValue = 0;
                    database.child("journalEntries").child(entryId).child("noOfFavorites").setValue(currentValue + 1, null);
                }
    
                @Override
                public void onCancelled(DatabaseError error) {
                    System.out.println(error.getMessage());
                }
            });
    }

    public static void decrementNoOfFavorites(String entryId) {
        database.child("journalEntries").child(entryId).child("noOfFavorites")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Integer currentValue = snapshot.getValue(Integer.class);
                    if (currentValue == null || currentValue <= 0) {
                        database.child("journalEntries").child(entryId).child("noOfFavorites").setValue(0, null);
                    } else {
                        database.child("journalEntries").child(entryId).child("noOfFavorites").setValue(currentValue - 1, null);
                    }
                }
    
                @Override
                public void onCancelled(DatabaseError error) {
                    System.out.println(error.getMessage());
                }
            });
    }


    public static void getNoOfEntriesfromDatabase(){
        database.child("NumberOfEntries").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                NumberOfEntries = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error){
                System.out.println(error.getMessage());
            }
        });
    }

    public static void incrementNumberOfEntries(){
        
        
        String newValue = String.valueOf(Integer.parseInt(NumberOfEntries) + 1);
        NumberOfEntries = newValue;
        database.child("NumberOfEntries").setValue(newValue, null);
    } 
    
    public static void decrementnumberOfEntries(){
        String newValue = String.valueOf(Integer.parseInt(NumberOfEntries)-1);
        database.child("NumberOfEntries").setValue(newValue, null);
    } 
}
