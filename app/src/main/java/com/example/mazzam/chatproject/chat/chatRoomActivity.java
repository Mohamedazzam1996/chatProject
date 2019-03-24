package com.example.mazzam.chatproject.chat;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mazzam.chatproject.Adapter.RoomsChatRecyclerAdapter;
import com.example.mazzam.chatproject.Base.BaseActivity;
import com.example.mazzam.chatproject.FireBaseUtil.DataHolder;
import com.example.mazzam.chatproject.FireBaseUtil.MessagesDao;
import com.example.mazzam.chatproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chatRoomActivity extends BaseActivity {
    EditText message_et;
    ImageButton send;
    RecyclerView recyclerView;
    RoomsChatRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        message_et=findViewById(R.id.message);
        send=findViewById(R.id.send);
        recyclerView=findViewById(R.id.chat_recyclerview);
        adapter=new RoomsChatRecyclerAdapter(null,DataHolder.currentUser.getId());
        layoutManager=new LinearLayoutManager(activity);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
      query= MessagesDao.getMessageByRoomId(DataHolder.currentRoom.getRoom_id());
             query.addChildEventListener(childEventListener);
    }
    Query query;
    com.example.mazzam.chatproject.FireBaseUtil.Model.Message message=new com.example.mazzam.chatproject.FireBaseUtil.Model.Message();
    public void onclick(View view) {
        if (view.getId()==R.id.send)
        {
            String smessage=message_et.getText().toString();
            message.setContent(smessage);
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/YYYY");
            String date=dateFormat.format(new Date());
            message.setDate(date);
            message.setSender_id(DataHolder.currentUser.getId());
            message.setSender_name(DataHolder.currentUser.getUser_name());
            message.setRoom_id(DataHolder.currentRoom.getRoom_id());
            MessagesDao.addMessage(message,onSuccessListener,onFailureListener);
        }
    }
    OnSuccessListener onSuccessListener=new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
          message_et.setText(" ");
        }
    };
    OnFailureListener onFailureListener=new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            showMessage("Error","the message is not sent","ok");
        }
    };
    ChildEventListener childEventListener=new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            com.example.mazzam.chatproject.FireBaseUtil.Model.Message message=dataSnapshot.getValue(com.example.mazzam.chatproject.FireBaseUtil.Model.Message.class);
            adapter.addMessageTolist(message);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        query.removeEventListener(childEventListener);
    }
}
