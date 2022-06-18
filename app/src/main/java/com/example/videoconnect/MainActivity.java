package com.example.videoconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText meetingId;

    Button logout,share,join;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout=findViewById(R.id.logout);
        meetingId=findViewById(R.id.meetingId);
        share=findViewById(R.id.share);
        join=findViewById(R.id.join);

        auth=FirebaseAuth.getInstance();

        URL serverUrl;
        try {
            serverUrl=new URL("https://meet.jit.si");

            JitsiMeetConferenceOptions defaultoption=
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverUrl).build();

            JitsiMeet.setDefaultConferenceOptions(defaultoption);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions options=new JitsiMeetConferenceOptions.Builder()
                        .setRoom(meetingId.getText().toString()).build();

                JitsiMeetActivity.launch(MainActivity.this,options);

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent=new Intent(MainActivity.this,LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}