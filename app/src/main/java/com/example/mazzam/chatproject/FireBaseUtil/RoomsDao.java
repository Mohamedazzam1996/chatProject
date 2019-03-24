package com.example.mazzam.chatproject.FireBaseUtil;

import com.example.mazzam.chatproject.FireBaseUtil.Model.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoomsDao {
    public static final String rooms="rooms";

    public static DatabaseReference getRoomsBranch (){

        return FirebaseDatabase.getInstance()
                .getReference(rooms);
    }


    public static void addNewRoom(Room room, OnSuccessListener onSuccessListener,
                                  OnFailureListener onFailureListener){
        DatabaseReference newnode=getRoomsBranch().push();
        room.setRoom_id(newnode.getKey());
                newnode.setValue(room)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);

    }
}
