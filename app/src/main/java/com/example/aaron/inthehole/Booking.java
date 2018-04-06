package com.example.aaron.inthehole;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        findViewById(R.id.profilebtn).setOnClickListener(this);
        findViewById(R.id.booking9am).setOnClickListener(this);
        book9am = (Button)findViewById(R.id.booking9am);
        findViewById(R.id.booking915am).setOnClickListener(this);
        book915am = (Button)findViewById(R.id.booking915am);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Booking").child("9am");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    book9am.setClickable(false);
                    book9am.setBackgroundColor(Color.RED);

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
            case R.id.profilebtn:
                finish();
                startActivity(new Intent(Booking.this, ProfileActivity.class));
                break;
            case R.id.booking9am:
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Booking.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_booking, null);
                final EditText mPlayer1 = (EditText) mView.findViewById(R.id.player1);
                final EditText mPlayer2 = (EditText) mView.findViewById(R.id.player2);
                final EditText mPlayer3 = (EditText) mView.findViewById(R.id.player3);
                final EditText mPlayer4 = (EditText) mView.findViewById(R.id.player4);
                final EditText mTime = (EditText) mView.findViewById(R.id.timeedit);
                final Button mBookingbtn = (Button) mView.findViewById(R.id.bookingbtn);
                mBookingbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String player1 = mPlayer1.getText().toString().trim();
                        String player2 = mPlayer2.getText().toString().trim();
                        String player4 = mPlayer4.getText().toString().trim();
                        String player3 = mPlayer3.getText().toString().trim();
                        if (player1.isEmpty()) {
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
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Booking").child("9am");
                        Map newPost = new HashMap();
                        newPost.put("playerone", playerone);
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
                mBuilder.setNeutralButton("Booking Confirmed ", new DialogInterface.OnClickListener() { // define the 'Cancel' button
                    public void onClick(DialogInterface dialog, int which) {
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
