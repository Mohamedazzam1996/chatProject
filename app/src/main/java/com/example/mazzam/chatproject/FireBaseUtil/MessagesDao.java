package com.example.mazzam.chatproject.FireBaseUtil;

import com.example.mazzam.chatproject.FireBaseUtil.Model.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MessagesDao {

    public final static String messages="Messages";
    public static DatabaseReference getMessagesBranch(){


        return FirebaseDatabase.getInstance().getReference(messages);
    }

    public static void addMessage(Message message, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener){

        getMessagesBranch().push()
                .setValue(message)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
    public static Query getMessageByRoomId(String roomId){

    Query query=getMessagesBranch()
                .orderByChild("room_id")
                .equalTo(roomId);
    return query;
    }
}
