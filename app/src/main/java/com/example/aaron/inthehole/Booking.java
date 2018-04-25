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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // sets the orientation of the application to always be portrait
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        findViewById(R.id.profilebtn).setOnClickListener(this); // OnClickListeners for buttons in the applications
        findViewById(R.id.booking9am).setOnClickListener(this);
        book9am = (Button)findViewById(R.id.booking9am);//sets name to specific button
        findViewById(R.id.booking915am).setOnClickListener(this);
        book915am = (Button)findViewById(R.id.booking915am);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Booking").child("9am"); // DatabaseReference where the information will then be stored
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // Checks to see if there is a data snapshot already exists
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
    public void onClick(View view) { // onclick method
        switch (view.getId()) {
            case R.id.profilebtn: // case to return to the Profile Activity
                finish();
                startActivity(new Intent(Booking.this, ProfileActivity.class)); //intent to open Profile Activity
                break;
            case R.id.booking9am: // case to start booking
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Booking.this); // Alert Dialog which opens when the 9am button is clicked
                final View mView = getLayoutInflater().inflate(R.layout.dialog_booking, null); // find the dialog booking xml, this is the layout for the alert dialog
                final EditText mPlayer1 = (EditText) mView.findViewById(R.id.player1); // finds all part of the dialog_booking which you are using
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
                        newPost.put("playerone", playerone); // represents each player playing at that time, these will be inputted into the database
                        newPost.put("playertwo", playertwo);
                        newPost.put("playerthree", playerthree);
                        newPost.put("playerfour", playerfour);
                        newPost.put("teetime", teetime);
                        current_user_db.setValue(newPost); //posted to the database
                        Toast.makeText(Booking.this, "Are you happy with your details? ", Toast.LENGTH_SHORT).show();
                    }

                });
                mBuilder.setPositiveButton("Confirm Booking", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // Confirms booking and then closes the alert
                        dialog.dismiss();
                        book9am.setClickable(false);
                        book9am.setBackgroundColor(Color.RED);
                        Toast.makeText(Booking.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setView(mView); // builds the alert dialog
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                break;
        }
    }
}
