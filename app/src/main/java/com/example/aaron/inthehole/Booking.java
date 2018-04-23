package com.example.aaron.inthehole;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Booking extends AppCompatActivity implements View.OnClickListener {
    public Button book9am, book915am;
    public ImageView photo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        findViewById(R.id.profilebtn).setOnClickListener(this); // OnClickListeners
        findViewById(R.id.booking9am).setOnClickListener(this);
        book9am = (Button)findViewById(R.id.booking9am);
        findViewById(R.id.booking915am).setOnClickListener(this);
        book915am = (Button)findViewById(R.id.booking915am);
        photo1 =(ImageView)findViewById(R.id.gifImageView);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Booking").child("9am"); // DatabaseReference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // Checks to see if there is an data snapshot already exists
                if (dataSnapshot.exists())
                {
                    book9am.setClickable(false); // if true the button becomes unclickable
                    book9am.setBackgroundColor(Color.RED); // the button changes to red to show that it cannot be clicked
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profilebtn: // case to return to the Profile Activity
                finish();
                startActivity(new Intent(Booking.this, ProfileActivity.class));
                break;
            case R.id.booking9am: // case to start booking
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Booking.this); // Alert Dialog which is made
                View mView = getLayoutInflater().inflate(R.layout.dialog_booking, null); // find the dialog booking xml
                final EditText mPlayer1 = (EditText) mView.findViewById(R.id.player1);
                final EditText mPlayer2 = (EditText) mView.findViewById(R.id.player2);
                final EditText mPlayer3 = (EditText) mView.findViewById(R.id.player3);
                final EditText mPlayer4 = (EditText) mView.findViewById(R.id.player4);
                final EditText mTime = (EditText) mView.findViewById(R.id.timeedit);
                final Button mBookingbtn = (Button) mView.findViewById(R.id.bookingbtn);
                mBookingbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { // sets the bookingbtn to be clicked
                        String player1 = mPlayer1.getText().toString().trim(); // the next lines is to do with the validation for each edittext
                        String player2 = mPlayer2.getText().toString().trim();
                        String player4 = mPlayer4.getText().toString().trim();
                        String player3 = mPlayer3.getText().toString().trim();
                        if (player1.isEmpty()) { // each of these check to see that the edit texts has been entered and brings up a message if they havent
                            mPlayer1.setError("Please enter player 1");
                            mPlayer1.requestFocus();
                            return;
                        }
                        if (player2.isEmpty()) {
                            mPlayer2.setError("Please enter player 2");
                            mPlayer2.requestFocus();
                            return;
                        }
                        if (player3.isEmpty()) {
                            mPlayer3.setError("Please enter player 3");
                            mPlayer3.requestFocus();
                            return;
                        }
                        if (player4.isEmpty()) {
                            mPlayer4.setError("Please enter player 4");
                            mPlayer4.requestFocus();
                            return;
                        }
                        String playerone = mPlayer1.getText().toString();
                        String playertwo = mPlayer2.getText().toString();
                        String playerthree = mPlayer3.getText().toString();
                        String playerfour = mPlayer4.getText().toString();
                        String teetime = mTime.getText().toString().trim();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Booking").child("9am"); // sets up the link to the Firebase
                        Map newPost = new HashMap();
                        newPost.put("playerone", playerone); // each child which is added to the tables
                        newPost.put("playertwo", playertwo);
                        newPost.put("playerthree", playerthree);
                        newPost.put("playerfour", playerfour);
                        newPost.put("teetime", teetime);
                        current_user_db.setValue(newPost);
                        Toast.makeText(Booking.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                        book9am.setClickable(false);
                        book9am.setBackgroundColor(Color.RED);
                    }


                });
                mBuilder.setPositiveButton("Close Dialog", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // Close the alert
                        dialog.dismiss();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                break;
        }
    }
}
