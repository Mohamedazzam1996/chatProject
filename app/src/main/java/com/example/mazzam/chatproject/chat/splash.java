package com.example.mazzam.chatproject.chat;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mazzam.chatproject.Base.BaseActivity;
import com.example.mazzam.chatproject.FireBaseUtil.DataHolder;
import com.example.mazzam.chatproject.FireBaseUtil.Model.User;
import com.example.mazzam.chatproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.internal.zzl;
import com.google.gson.Gson;

public class splash extends BaseActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       String userJson=getString("user");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (userJson!=null&user==null){
            Gson gson=new Gson();
            DataHolder.currentUser=gson.fromJson(userJson,User.class);
            startActivity(new Intent(activity,HomeActivity.class));
            finish();

        }
        else if (userJson==null&user!=null){
            Gson gson=new Gson();
            String userGson=gson.toJson(user);
            DataHolder.currentUser=gson.fromJson(userGson,User.class);
            startActivity(new Intent(activity,HomeActivity.class));
            finish();

          }
        else
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(activity,Registeration.class));
                    finish();
                }
            },2000);


    }
}
