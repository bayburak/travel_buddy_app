package mypackage.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.firebase.database.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mypackage.model.JournalEntry;
import mypackage.model.User;

public class UserDatabaseService extends DatabaseService {

    private final static DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    public UserDatabaseService() throws IOException{
        super();
    }

    public void createUser(User user) {
        database.child("users").child(user.getUserID()).setValue(user,null);
        
        
    }
    public void updateUserProfile(User user ) {
        database.child("users").child(user.getUserID()).setValueAsync(user);
    }
    //TODO when a user is deleted delete its journal entries from the favorites as well
    public void deleteUser(String userID) {

        database.child("users").child(userID).child("entries").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                for(DataSnapshot entrySnapshot: snapshot.getChildren()){
                    String entryID = entrySnapshot.getKey();
                    database.child("journalEntries").child(entryID).removeValue(null);
                }
            }
            @Override 
            public void onCancelled(DatabaseError error){

            }
        });

        removeUserFromFollowLists(userID);
        database.child("users").child(userID).removeValue(null);
        
    }
    //Helper method for deleteUser
    private void removeUserFromFollowLists(String userID){
        database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                for(DataSnapshot userSnapshot : snapshot.getChildren()){
                    String otherUserID = userSnapshot.getKey();

                    database.child("users").child(otherUserID).child("following").child(userID).removeValue(null);
                
                    database.child("users").child(otherUserID).child("followers").child(userID).removeValue(null);   
                }
            }

            @Override
            public void onCancelled(DatabaseError error){

            }
        });

    }
    
    public User getUserByID(String userID) throws InterruptedException, ExecutionException{
        return getUser(userID).get();
    }
    //Helper method for getUserByID
    private CompletableFuture<User> getUser(String userID){
        
        CompletableFuture<User> future = new CompletableFuture<>();

        database.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    future.complete(user);
                }
                else{
                    
                }
            }
            @Override
            public void onCancelled(DatabaseError error){
                future.completeExceptionally(new RuntimeException(error.getMessage()));
            }
        });
        return future;
    }
    

    public void followUser(String followerId, String followeeId) {
        database.child("users").child(followerId).child("following").child(followeeId).setValue(true, null);

        database.child("users").child(followeeId).child("followers").child(followerId).setValue(true, null);
    }

    public void unfollowUser(String followerId, String followeeId) {
        database.child("users").child(followerId).child("following").child(followeeId).removeValue(null);

        database.child("users").child(followeeId).child("followers").child(followerId).removeValue(null);
    }

    //TODO I dont know what is happening
    public List<String> getFollowers(String userId) throws InterruptedException, ExecutionException {
        return getfollowers(userId).get();
    }
    //Helper methodd for getFollowers
    private CompletableFuture<List<String>> getfollowers(String userId){

        CompletableFuture<List<String>> future = new CompletableFuture<>();

        database.child("users").child(userId).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> followerIds = new ArrayList<>();
                for (DataSnapshot followerSnapshot : dataSnapshot.getChildren()) {
                    followerIds.add(followerSnapshot.getKey());
                }
                future.complete(followerIds);
                
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException(databaseError.getMessage()));
            }
            
        });

        return future;
    }

    public List<String> getFollowing(String userId) throws InterruptedException, ExecutionException {
        return getfollowing(userId).get();
    }
    private CompletableFuture<List<String>> getfollowing(String userId){
        CompletableFuture<List<String>> future = new CompletableFuture<>();

        database.child("users").child(userId).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> followingIds = new ArrayList<>();
                for (DataSnapshot followingSnapshot : dataSnapshot.getChildren()) {
                    followingIds.add(followingSnapshot.getKey());
                }
                future.complete(followingIds);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException(databaseError.getMessage()));
            }
        });

        return future;

    }

    //TODO favorites
    public void saveEntryForUser(String userId, String entryId) {
        database.child("users").child(userId).child("savedEntries").child(entryId).setValue(true, null);
    }

    public void unsaveEntryForUser(String userId, String entryId) {
        database.child("users").child(userId).child("savedEntries").child(entryId).removeValue(null);
    }

    //TODO favvorites
    public void getSavedEntriesForUser(String userId) {

    }
    
}
