package mypackage.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebasePaths {
    
    final static public DatabaseReference userpath = FirebaseDatabase.getInstance().getReference("users");


}
