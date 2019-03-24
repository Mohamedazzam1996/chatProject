package com.example.mazzam.chatproject.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mazzam.chatproject.Base.BaseActivity;
import com.example.mazzam.chatproject.FireBaseUtil.DataHolder;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registeration extends BaseActivity implements View.OnClickListener {
    TextInputLayout user_name, email, pass;
    TextView login_tv;
    Button reg_btn;
    GoogleSignInClient mGoogleSignInClient;
    int SIGN_IN_CODE = 500;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        user_name = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        reg_btn = findViewById(R.id.reg_btn);
        login_tv = findViewById(R.id.text_login);
        login_tv.setOnClickListener(this);
        reg_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

    }



    private boolean validate(TextInputLayout email, TextInputLayout pass) {
        String semail = email.getEditText().getText().toString();
        String spass = pass.getEditText().getText().toString();
        email.setError(null);
        pass.setError(null);
        if (!isEmailValid(semail)) {
            email.setError("Email is invalid");
            return false;
        } else if (!isPasswordValid(spass)) {
            pass.setError(" password must contain at least 6 numbers");
            return false;
        } else if (!isEmpty(semail)) {
            email.setError("Email is Required");
            return false;
        } else if (!isEmpty(spass)) {
            pass.setError("Pass is reqired");
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        if (password.length() < 6)
            return false;
        return true;
    }

    public static boolean isEmpty(String word) {

        if (word.toString().trim() == " ")
            return false;
        return true;
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser account) {
        if (account != null) {
            startActivity(new Intent(activity, HomeActivity.class));
            Gson gson=new Gson();
            String userGson=gson.toJson(account);
            DataHolder.currentUser=gson.fromJson(userGson,User.class);
            finish();
        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SIGN_IN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("tag", "Google sign in failed", e);
                // ...
            }
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("tag", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("tag", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.reg_btn){
            String suser_name = user_name.getEditText().getText().toString();
            String semail = email.getEditText().getText().toString();
            String spass = pass.getEditText().getText().toString();
            if (validate(email, pass)) {
                final User user = new User(suser_name, semail, spass);
                showprogressBar();
                UsersDao.addNewUser(user, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        hidePrgressBar();
                        showConfirmationMessage(R.string.Message, R.string.the_user_has_been_added, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                DataHolder.currentUser = user;
                                Gson gson=new Gson();
                               String userJson=gson.toJson(user);
                                saveString("user",userJson);
                                startActivity(new Intent(activity, HomeActivity.class));
                                finish();
                            }
                        }).setCancelable(false);
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("Massage", "error", "ok");
                    }
                });
            }

        }
        else if (v.getId()==R.id.sign_in_button){
            signIn();
        }
        else if (v.getId()==R.id.text_login){
            startActivity(new Intent(activity,login.class));
            finish();
        }
    }

}
