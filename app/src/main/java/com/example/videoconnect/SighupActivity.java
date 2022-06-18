package com.example.videoconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SighupActivity extends AppCompatActivity {

    EditText Username,emailBox,Password;
    Button signup, loginactivity;

    FirebaseAuth auth;
    FirebaseFirestore database;

    ProgressDialog dialog=new ProgressDialog(SighupActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighup);

        Username=findViewById(R.id.Username);
        emailBox=findViewById(R.id.meetingId);
        Password=findViewById(R.id.Password);
        signup=findViewById(R.id.btn_signup);
        loginactivity=findViewById(R.id.btn_alreadyhave);

        dialog.setTitle("Creating Account");
        dialog.setTitle("Please Wait...");

        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                String name,email,password;
                email=emailBox.getText().toString();
                name=Username.getText().toString();
                password=Password.getText().toString();

                Users users=new Users();
                users.setEmail(email);
                users.setName(name);
                users.setPassword(password);

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            database.collection("Users")
                                    .document().set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(SighupActivity.this,MainActivity.class));
                                }
                            });
                            Toast.makeText(SighupActivity.this, "Account Created", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(SighupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private long pressedTime;
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            this.finishAffinity();
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}