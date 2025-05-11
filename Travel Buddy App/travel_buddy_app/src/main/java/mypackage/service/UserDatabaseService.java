package mypackage.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.firebase.database.*;

import java.util.function.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import mypackage.model.User;


public class UserDatabaseService extends DatabaseService {

    

    public static String NumberOfUsers;


    public static void createUser(User user) {
        
        database.child("users").child(user.getUserID()).setValue(user,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference){
                if(databaseError == null){ }
                else{ System.out.println("Error: " + databaseError.getMessage()); }
            }
        });
        
        
        
    }
    
    public static void updateUserProfile(User user) {
        database.child("users").child(user.getUserID()).setValue(user, null);
    }

   

    public static void deleteUser(String userID) {

        

        database.child("users").child(userID).child("entries").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                deleteEntries(snapshot,new Runnable() {
                    @Override
                    public void run(){
                        removeUserFromFollowLists(userID);
                        database.child("users").child(userID).removeValue(null);
                    }
                });
            }
            @Override 
            public void onCancelled(DatabaseError error){
                System.out.println(error.getMessage());
            }
        });

        
            
       
        
    }
    //Helper for deleteUser
    private static void deleteEntries(DataSnapshot snapshot, Runnable callback){

        final int totalEntries = (int) snapshot.getChildrenCount();  
        final AtomicInteger deletedEntriesCount = new AtomicInteger(0);

        for(DataSnapshot entrySnapshot: snapshot.getChildren()){
            String entryID = entrySnapshot.getValue(String.class);
            System.out.println(entryID);
            JournalDatabaseService.deleteJournalEntry(entryID, new Runnable() {
                @Override
                public void run(){
                    if (deletedEntriesCount.incrementAndGet() == totalEntries) {
                    
                        callback.run();
                    }
                }
            });
        }


    }
    //Helper method for deleteUser
    private static void removeUserFromFollowLists(String userID){
        DatabaseReference userRef = database.child("users").child(userID);

        // Handle Following list
        userRef.child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot followingSnapshot) {
                List<String> followingList = new ArrayList<>();
                for (DataSnapshot child : followingSnapshot.getChildren()) {
                    String followedUserId = child.getValue(String.class);
                    if (followedUserId != null) {
                        followingList.add(followedUserId);
                    }
                }
    
                for (String followedUserId : followingList) {
                    database.child("users").child(followedUserId).child("followers")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot followersSnapshot) {
                                List<String> updatedFollowers = new ArrayList<>();
                                for (DataSnapshot follower : followersSnapshot.getChildren()) {
                                    String id = follower.getValue(String.class);
                                    if (id != null && !id.equals(userID)) {
                                        updatedFollowers.add(id);
                                    }
                                }
                                database.child("users").child(followedUserId).child("followers")
                                       .setValue(updatedFollowers, null);
                            }
    
                            @Override
                            public void onCancelled(DatabaseError error) {
                                System.err.println("Error updating followers: " + error.getMessage());
                            }
                        });
                }
            }
    
            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error accessing following list: " + error.getMessage());
            }
        });
    
        // Handle Followers list
        userRef.child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot followersSnapshot) {
                List<String> followersList = new ArrayList<>();
                for (DataSnapshot child : followersSnapshot.getChildren()) {
                    String followerUserId = child.getValue(String.class);
                    if (followerUserId != null) {
                        followersList.add(followerUserId);
                    }
                }
    
                for (String followerUserId : followersList) {
                    database.child("users").child(followerUserId).child("following")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot followingSnapshot) {
                                List<String> updatedFollowing = new ArrayList<>();
                                for (DataSnapshot follow : followingSnapshot.getChildren()) {
                                    String id = follow.getValue(String.class);
                                    if (id != null && !id.equals(userID)) {
                                        updatedFollowing.add(id);
                                    }
                                }
                                database.child("users").child(followerUserId).child("following")
                                       .setValue(updatedFollowing, null);
                            }
    
                            @Override
                            public void onCancelled(DatabaseError error) {
                                System.err.println("Error updating following: " + error.getMessage());
                            }
                        });
                }
            }
    
            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error accessing followers list: " + error.getMessage());
            }
        });

    }
    
    public static User getUserByID(String userID) throws InterruptedException, ExecutionException{
        
        return getUser(userID).get();
    }
    //Helper method for getUserByID
    private static CompletableFuture<User> getUser(String userID){
        
        CompletableFuture<User> future = new CompletableFuture<>();

        database.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
            
                if(snapshot.exists()){
            
                    User user = snapshot.getValue(User.class);
                   
                    future.complete(user);
                    
                }
                else{
                    
                    future.completeExceptionally(new NoSuchElementException("User with ID " + userID + " not found"));
                    future.complete(null);
                }
            }
            @Override
            public void onCancelled(DatabaseError error){
                future.completeExceptionally(new RuntimeException(error.getMessage()));
            }
        });
        return future;
    }
    
    public static User getUserByUsername(String username) throws InterruptedException, ExecutionException{
        return getuserbyusername(username).get();
    }
    private static CompletableFuture<User> getuserbyusername(String username){
        
        CompletableFuture<User> future = new CompletableFuture<>();
    
        database.child("users").orderByChild("username").equalTo(username).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    
                    User user = snapshot.getChildren().iterator().next().getValue(User.class);
                    future.complete(user); 
                } else {
                    future.complete(null); 
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(new RuntimeException(error.getMessage())); 
            }
        });

        return future; 
    }

    
    public static void followUser(String userId ,String otherUserId) {
  
        database.child("users").child(userId).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> following = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String id = child.getValue(String.class);
                    if (id != null) following.add(id);
                }

                if (!following.contains(otherUserId)) {
                    following.add(otherUserId);
                    database.child("users").child(userId).child("following").setValue(following, null);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error updating following list: " + error.getMessage());
            }
        });

        database.child("users").child(otherUserId).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> followers = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String id = child.getValue(String.class);
                    if (id != null) followers.add(id);
                }

                if (!followers.contains(userId)) {
                    followers.add(userId);
                    database.child("users").child(otherUserId).child("followers").setValue(followers, null);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error updating followers list: " + error.getMessage());
            }
        });
    }

    public static void unfollowUser(String userId, String otherUserId) {

    
    database.child("users").child(userId).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            List<String> following = new ArrayList<>();
            for (DataSnapshot child : snapshot.getChildren()) {
                String id = child.getValue(String.class);
                if (id != null && !id.equals(otherUserId)) {
                    following.add(id);
                }
            }

            database.child("users").child(userId).child("following").setValue(following, null);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            System.err.println("Error updating following list: " + error.getMessage());
        }
    });

    database.child("users").child(otherUserId).child("followers").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            List<String> followers = new ArrayList<>();
            for (DataSnapshot child : snapshot.getChildren()) {
                String id = child.getValue(String.class);
                if (id != null && !id.equals(userId)) {
                    followers.add(id);
                }
            }

            database.child("users").child(otherUserId).child("followers").setValue(followers, null);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            System.err.println("Error updating followers list: " + error.getMessage());
        }
    });
    }

    
    public static void saveEntryForUser(String userId, String entryId) {
        database.child("users").child(userId).child("saved").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> savedList = new ArrayList<>();
    
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String id = child.getValue(String.class);
                        if (id != null) savedList.add(id);
                    }
                }
    
                if (!savedList.contains(entryId)) {
                    savedList.add(entryId);
                    database.child("users").child(userId).child("saved").setValue(savedList, null);
                    JournalDatabaseService.incrementNoOfFavorites(entryId);
                }
            }
    
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println( error.getMessage());
            }
        });
    }

    public static void unsaveEntryForUser(String userId, String entryId) {
        database.child("users").child(userId).child("saved").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> savedList = new ArrayList<>();
    
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String id = child.getValue(String.class);
                        if (id != null && !id.equals(entryId)) {
                            savedList.add(id);
                        }
                    }
                }
    
                database.child("users").child(userId).child("saved").setValue(savedList, null);
                JournalDatabaseService.decrementNoOfFavorites(entryId);
            }
    
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });
    }

    
    public static List<User> getFollowers(String userId) throws InterruptedException, ExecutionException {
        return getfollowers(userId).get();
    }
    //Helper methodd for getFollowers
    private static CompletableFuture<List<User>> getfollowers(String userId){
        CompletableFuture<List<User>> future = new CompletableFuture<>();

        database.child("users").child(userId).child("followers")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<CompletableFuture<User>> futures = new ArrayList<>();
    
                    for (DataSnapshot followerSnapshot : dataSnapshot.getChildren()) {
                        String followerID = followerSnapshot.getValue(String.class);
                        if (followerID != null) {
                            futures.add(getUser(followerID));  
                        }
                    }
    
                    if (futures.isEmpty()) {
                        future.complete(new ArrayList<User>());
                        return;
                    }
    
                    CompletableFuture<Void> Doneall = CompletableFuture.allOf(
                            futures.toArray(new CompletableFuture[futures.size()])
                    );
    
                    Doneall.thenRun(new Runnable() {
                        @Override
                        public void run() {
                            List<User> users = new ArrayList<User>();
    
                            for (CompletableFuture<User> f : futures) {
                                try {
                                    User user = f.get();  
                                    if (user != null) {
                                        users.add(user);
                                    }
                                } catch (Exception e) {
                                   
                                    e.printStackTrace();
                                }
                            }
    
                            future.complete(users);
                        }
                    }).exceptionally(new Function<Throwable, Void>() {
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

    public static List<User> getFollowing(String userId) throws InterruptedException, ExecutionException {
        return getfollowing(userId).get();
    }
    //Helper fr getFollowing
    private static CompletableFuture<List<User>> getfollowing(String userId){
        CompletableFuture<List<User>> future = new CompletableFuture<>();

        database.child("users").child(userId).child("following")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    List<CompletableFuture<User>> futures = new ArrayList<>();

                    for (DataSnapshot followingSnapshot : snapshot.getChildren()) {
                        String followingID = followingSnapshot.getValue(String.class);
                        if (followingID != null) {
                            futures.add(getUser(followingID)); 
                        }
                    }

                    if (futures.isEmpty()) {
                        future.complete(new ArrayList<User>());
                        return;
                    }

                    CompletableFuture<Void> doneAll = CompletableFuture.allOf(
                            futures.toArray(new CompletableFuture[futures.size()])
                    );

                    doneAll.thenRun(new Runnable() {
                        @Override
                        public void run() {
                            List<User> users = new ArrayList<User>();
                            for (CompletableFuture<User> f : futures) {
                                try {
                                    User user = f.get(); 
                                    if (user != null) {
                                        users.add(user);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace(); 
                                }
                            }
                            future.complete(users);
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

    public static List<User> searchUsersByKeyword(String keyword) throws InterruptedException, ExecutionException {
        return searchUsersByKeywordAsync(keyword).get();
    }
    
    private static CompletableFuture<List<User>> searchUsersByKeywordAsync(String keyword) {
        CompletableFuture<List<User>> future = new CompletableFuture<>();
    
        database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<User> matchingUsers = new ArrayList<User>();
    
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
    
                    if (user != null) {
                        String name;
                        if (user.getNameSurname() != null) { name = user.getNameSurname().toLowerCase();} 
                        else {name = "";}
                        
                        String uname;
                        if (user.getUsername() != null) {uname = user.getUsername().toLowerCase();} 
                        else {uname = "";}
                        String lowerKeyword = keyword.toLowerCase();
    
                        if (name.contains(lowerKeyword) || uname.contains(lowerKeyword)) {
                            matchingUsers.add(user);
                        }
                    }
                }
    
                future.complete(matchingUsers);
            }
    
            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(new RuntimeException(error.getMessage()));
            }
        });
    
        return future;
    }

    public static void getNoOfUsersfromDatabase(){
        database.child("NumberOfUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                NumberOfUsers = snapshot.getValue(String.class);
                
            }

            @Override
            public void onCancelled(DatabaseError error){
                System.out.println(error.getMessage());
            }
        });
    }

    public static void incrementNumberOfUsers(){
        
        
        String newValue = String.valueOf(Integer.parseInt(NumberOfUsers) + 1);
        NumberOfUsers = newValue;
        database.child("NumberOfUsers").setValue(newValue, null);
    } 

    public static void decrementNumberOfUsers(){
        String newValue = String.valueOf(Integer.parseInt(NumberOfUsers)-1);
        NumberOfUsers = newValue;
        database.child("NumberOfUsers").setValue(newValue,null);
    }
    
}
