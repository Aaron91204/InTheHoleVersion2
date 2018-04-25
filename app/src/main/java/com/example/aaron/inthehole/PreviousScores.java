package com.example.aaron.inthehole;

import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aaron.inthehole.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PreviousScores extends AppCompatActivity {
    private static final String TAG = "ViewDatabase";
    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // orientation for the application to the portrait
        setContentView(com.example.aaron.inthehole.R.layout.activity_previous_scores);
        mListView = (ListView) findViewById(R.id.mlistview); //listview set up
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser(); //gets current user
        String userid=user.getUid(); // gets current user's UID
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Scores").child("Hole_Scores_25th_April_2018 "); //Database Reference


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
            }
        };

        ref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // Checks to see if a data snapshot exists and if it doesnt then it wont open the activity
                if (dataSnapshot.exists()) {
                    showData(dataSnapshot);
                } else {
                    finish(); // closes the activity if it doesnt
                    Toast.makeText(PreviousScores.this, "You need to submit a score", Toast.LENGTH_SHORT).show();


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }
    private void showData(DataSnapshot dataSnapshot) {
        ArrayList<String> array  = new ArrayList<>();
            Scores uInfo = dataSnapshot.getValue(Scores.class); // displays all hole scores
            array.add("Week 1 Score");
            array.add(" Hole 1 Par 5 : " +uInfo.getHole1());
            array.add(" Hole 2 Par 3 : " + uInfo.getHole2());
            array.add(" Hole 3 Par 4 : " + uInfo.getHole3());
            array.add(" Hole 4 Par 4 : " + uInfo.getHole4());
            array.add(" Hole 5 Par 4 : " + uInfo.getHole5());
            array.add(" Hole 6 Par 5 : " + uInfo.getHole6());
            array.add(" Hole 7 Par 4 : " + uInfo.getHole7());
            array.add(" Hole 8 Par 4 : " + uInfo.getHole8());
            array.add(" Hole 9 Par 3 : " + uInfo.getHole9());
            array.add(" Hole 10 Par 5 : " + uInfo.getHole10());
            array.add(" Hole 11 Par 3 : " + uInfo.getHole11());
            array.add(" Hole 12 Par 4 : " + uInfo.getHole12());
            array.add(" Hole 13 Par 4 : " + uInfo.getHole13());
            array.add(" Hole 14 Par 4 : " + uInfo.getHole14());
            array.add(" Hole 15 Par 5 : " + uInfo.getHole15());
            array.add(" Hole 16 Par 4 : " + uInfo.getHole16());
            array.add(" Hole 17 Par 4 : " + uInfo.getHole17());
            array.add(" Hole 18 Par 5 : " + uInfo.getHole18());

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter); // shown in a listview
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}

