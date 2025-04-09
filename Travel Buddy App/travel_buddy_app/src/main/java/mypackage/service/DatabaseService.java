package mypackage.service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mypackage.model.User;

public class DatabaseService {
    
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
