package com.example.mazzam.chatproject.chat;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mazzam.chatproject.Base.BaseActivity;
import com.example.mazzam.chatproject.FireBaseUtil.RoomsDao;
import com.example.mazzam.chatproject.FireBaseUtil.Model.Room;
import com.example.mazzam.chatproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddingRoomActivity extends BaseActivity {
    TextInputLayout room_name,room_desc;
    Button add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_room);
        room_name=findViewById(R.id.room_name);
        room_desc=findViewById(R.id.room_desc);
        add_btn=findViewById(R.id.add_btn);
    }

    public void onclick(View view) {
        if (view.getId()==R.id.add_btn)
        {
         String sroom_name=room_name.getEditText().getText().toString();
         String sroom_desc=room_desc.getEditText().getText().toString();
         int current_users=0;
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
            String format = s.format(new Date());
         String room_stamp=format;
         Room room=new Room();
         room.setCurrent_users(0);
         room.setRoom_name(sroom_name);
         room.setRoom_desc(sroom_desc);
         room.setRoom_stamp(room_stamp);
         showprogressBar();
         RoomsDao.addNewRoom(room, new OnSuccessListener() {
             @Override
             public void onSuccess(Object o) {
                 hidePrgressBar();
                 showConfirmationMessage(R.string.Message, R.string.the_room_added, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                     @Override
                     public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                         finish();
                     }
                 });
             }
         }, new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
              hidePrgressBar();
                 showMessage("Massage","error","ok");
             }
         });
        }
    }
}
