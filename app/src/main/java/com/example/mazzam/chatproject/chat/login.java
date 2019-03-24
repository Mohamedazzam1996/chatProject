package com.example.mazzam.chatproject.chat;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.mazzam.chatproject.Base.BaseActivity;
import com.example.mazzam.chatproject.FireBaseUtil.DataHolder;
import com.example.mazzam.chatproject.FireBaseUtil.MessagesDao;
import com.example.mazzam.chatproject.FireBaseUtil.Model.User;
import com.example.mazzam.chatproject.FireBaseUtil.UsersDao;
import com.example.mazzam.chatproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends BaseActivity  {
    TextInputLayout email, pass;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getEditText().toString();
                final String spass = pass.getEditText().toString();
                showprogressBar();
                final Query query = UsersDao.getUserByEmial(semail);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        hidePrgressBar();
                        if (!dataSnapshot.hasChildren()) {
                            showMessage("Error", "Invalid email or password", "ok")
                                    .setCancelable(false);
                        } else
                            for (DataSnapshot object : dataSnapshot.getChildren()) {
                                User user = object.getValue(User.class);
                                if (user.getPass().equals(spass)) {
                                    startActivity(new Intent(activity, HomeActivity.class));
                                    DataHolder.currentUser = user;
                                    finish();
                                }
                            }
                        showMessage("Error", "Invalid email or password", "ok");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }




}
