package com.example.videocallingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videocallingapp.databinding.ActivityDashBoardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DashBoardActivity extends AppCompatActivity {

    EditText secretCodeBox;
    Button joinBtn, shareBtn;
    ActivityDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        getSupportActionBar().hide();

        secretCodeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinBtn);
        shareBtn = findViewById(R.id.shareBtn);


        URL serverURL;


        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretCodeBox.getText().toString())
                        .build();

                JitsiMeetActivity.launch(DashBoardActivity.this, options);
            }
        });


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Now share text only function will be called
                // here we  will be passing the text to share
                shareTextOnly(secretCodeBox.getText().toString());
            }
        });


        /*<-----------------------------Logout worl in bottom navigation drawer------------------------->*/


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.Home);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.LogOut:
                        startActivity(new Intent(getApplicationContext(),DialogActivity.class));
                        overridePendingTransition(0,0);
                        return true;



                }
                return false;
            }
        });

    }
    ///////////////////////////////////////////////////////////////////////



     ////// / / ////////////////////////////////////////////////////////////////////////






    private void shareTextOnly(String titlee) {
        String sharebody = titlee;

        // The value which we will sending through data via
        // other applications is defined
        // via the Intent.ACTION_SEND
        Intent intentt = new Intent(Intent.ACTION_SEND);

        // setting type of data shared as text
        intentt.setType("text/plain");
        intentt.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

        // Adding the text to share using putExtra
        intentt.putExtra(Intent.EXTRA_TEXT, sharebody);
        startActivity(Intent.createChooser(intentt, "Share Via"));
    }
}