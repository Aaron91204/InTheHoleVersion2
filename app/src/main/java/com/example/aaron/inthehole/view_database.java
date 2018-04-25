package com.example.aaron.inthehole;


import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class view_database extends AppCompatActivity {
    private static final String TAG = "ViewDatabase";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private  String userID;
    private FirebaseUser mCurrentUser;
    private DatabaseReference myRef;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//setting the orientation to be portrait
        setContentView(R.layout.activity_view_database);
        mListView = (ListView) findViewById(R.id.listview);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser(); //gets current user's details
        String userid=user.getUid();//gets the current user's UID
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users"); //Database Reference for the Users table
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
            public void onDataChange(DataSnapshot dataSnapshot) { //if data snapshot exists open the view details page
                if (dataSnapshot.exists()) {
                    showData(dataSnapshot);
                } else { //else close the activity and post the below message
                    finish();
                    Toast.makeText(view_database.this, "Please input your details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void showData(DataSnapshot dataSnapshot) { //array for displying the details in the listview
        ArrayList<String> array  = new ArrayList<>();
        UserInformation uInfo = dataSnapshot.getValue(UserInformation.class); //uses the User Information Java class to obtain the details
        array.add(" Full Name : " +uInfo.getName()); //gets each value and buts it into the array
        array.add(" Age : " + uInfo.getAge());
        array.add(" Handicap: " + uInfo.getHandicap());
        array.add(" Gender: " + uInfo.getGender());
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array); //the array is then set up
        mListView.setAdapter(adapter); //details are then displayed into the listview
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
