package com.example.mazzam.chatproject.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.mazzam.chatproject.Adapter.RoomsRecyclerAdapter;
import com.example.mazzam.chatproject.Base.BaseActivity;
import com.example.mazzam.chatproject.FireBaseUtil.DataHolder;
import com.example.mazzam.chatproject.FireBaseUtil.RoomsDao;
import com.example.mazzam.chatproject.FireBaseUtil.Model.Room;
import com.example.mazzam.chatproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
  RecyclerView recyclerView;
  RecyclerView.LayoutManager layoutManager;
  RoomsRecyclerAdapter adapter;
  SwipeRefreshLayout swipeRefreshLayout;
  TextView logout_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       recyclerView=findViewById(R.id.recyclerview);
       layoutManager=new LinearLayoutManager(activity);
       adapter=new RoomsRecyclerAdapter(null);
       swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
       logout_tv=findViewById(R.id.logout_tv);
       logout_tv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
              startActivity(new Intent(activity,Registeration.class));
              finish();
           }
       });
        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity,AddingRoomActivity.class));

            }
        });
        getDataChanged();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               getDataChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        adapter.setOnRoomClick(new RoomsRecyclerAdapter.OnItemClickListner() {
    @Override
    public void onItemClick(int pos, Room room) {
        DataHolder.currentRoom=room;
        startActivity(new Intent(activity,chatRoomActivity.class));
    }
});
    }
    ValueEventListener valueEventListener=new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            final List<Room>rooms=new ArrayList<>();

            for (DataSnapshot roomsdata:dataSnapshot.getChildren())
            {
                Room room=roomsdata.getValue(Room.class);
                rooms.add(room);
            }
            adapter.changeData(rooms);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            showMessage("Error",databaseError.getMessage(),"ok");
        }
    };
    public void getDataChanged() {
        RoomsDao.getRoomsBranch().addValueEventListener(valueEventListener);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RoomsDao.getRoomsBranch().removeEventListener(valueEventListener);
    }

}
