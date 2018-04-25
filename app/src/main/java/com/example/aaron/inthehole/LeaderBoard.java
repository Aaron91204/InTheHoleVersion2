package com.example.aaron.inthehole;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard extends AppCompatActivity {
    private static final String TAG = "LeaderBoard";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Query myRef;
    private  String userID;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // sets orientation to portrait
        setContentView(R.layout.activity_leader_board);
        mListView = (ListView) findViewById(R.id.nlistview); //sets up listview
        mAuth = FirebaseAuth.getInstance(); // Firebase instance
        FirebaseUser user = mAuth.getCurrentUser(); // gets current user
        userID = user.getUid(); // gets current user ID
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Scores").child("Net_and_Gross_Scores_Week_25th_April _2018 ") .orderByChild("Net"); // Database Reference
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    showData(dataSnapshot); // checks to see if scores have been inputted into leader board
                } else {
                    finish();
                    Toast.makeText(LeaderBoard.this, "No Scores Entered", Toast.LENGTH_SHORT).show(); // toast to say that no scores have been entered
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    private void showData(DataSnapshot dataSnapshot) { // this array is used to get the Gross Net Name and Handicap of each user and they are then sorted numerically from lowest to highest
        ArrayList<String> array  = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            LeaderBoardScores uInfo = ds.getValue(LeaderBoardScores.class); // Link to the leaderboardscores class
            array.add("Net: " +uInfo.getNet() ); // Net is the rank of each user
            array.add("Name: " +uInfo.getFullName() + "   Gross:  " + uInfo.getGross()+ "   Handicap:  " + uInfo.getPlayerHandicap()); //rest of details displayed
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter); // the results are then displayed in a ListView
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
