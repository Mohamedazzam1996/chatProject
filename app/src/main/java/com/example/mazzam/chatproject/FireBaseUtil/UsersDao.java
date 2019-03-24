package com.example.mazzam.chatproject.FireBaseUtil;

import com.example.mazzam.chatproject.FireBaseUtil.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UsersDao {
    public static final String users="users";
    public static DatabaseReference getUsersBranch(){
     return   FirebaseDatabase.getInstance()
                .getReference(users);
    }

    public static void addNewUser(User user, OnSuccessListener onSuccessListener,
                                  OnFailureListener onFailureListener)
    {
        DatabaseReference newNode=getUsersBranch().push();
        getUsersBranch().push();
        user.setId(newNode.getKey());
        newNode.setValue(user)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public static Query getUserByEmial(String email)
    {
        Query query=getUsersBranch().orderByChild("email")
             .equalTo(email);
       return query;
    }

}
