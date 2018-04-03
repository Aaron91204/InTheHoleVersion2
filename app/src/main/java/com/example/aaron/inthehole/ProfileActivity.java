package com.example.aaron.inthehole;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Button mSignOut;
    private Button save,viewdetails,findus;
    private EditText name,handicap,gender,age;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private TextView txtview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById(R.id.coursemanagement).setOnClickListener(this);
        findViewById(R.id.scorecardbtn).setOnClickListener(this);
        findViewById(R.id.discussionboardbtn).setOnClickListener(this);
        findViewById(R.id.savebtn).setOnClickListener(this);
        findViewById(R.id.logbtn).setOnClickListener(this);
        findViewById(R.id.previousscore).setOnClickListener(this);
        findViewById(R.id.leaderboard).setOnClickListener(this);
        findViewById(R.id.bookingbtn).setOnClickListener(this);
        findViewById(R.id.handicapbutton).setOnClickListener(this);
        mSignOut = (Button) findViewById(R.id.logbtn);
        mAuth = FirebaseAuth.getInstance();
        setupFireBaseListener();
        name = (EditText) findViewById(R.id.name);
        handicap = (EditText) findViewById(R.id.handicap);
        handicap.setFilters(new InputFilter[]{new scoreboard1.InputFilterMinMax("1", "36")});
        gender = (EditText) findViewById(R.id.gender);
        age = (EditText) findViewById(R.id.age);
        age.setFilters(new InputFilter[]{new scoreboard1.InputFilterMinMax("1", "100")});
        save = (Button) findViewById(R.id.savebtn);
        findus = (Button)findViewById(R.id.findus);
        findus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MapsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        final String userid=user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                }
                else
                    {
                        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.welcomemessage, null);
                        final EditText mPlayer1 = (EditText) mView.findViewById(R.id.namepopup);
                        final EditText mPlayer2 = (EditText) mView.findViewById(R.id.handicappopup);
                        final EditText mPlayer3 = (EditText) mView.findViewById(R.id.agepopup);
                        final EditText mPlayer4 = (EditText) mView.findViewById(R.id.genderpopup);
                        final Button mBookingbtn = (Button) mView.findViewById(R.id.savepopup);
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
                                    mPlayer3.setError("Please enter player 2");
                                    mPlayer3.requestFocus();
                                    return;
                                }
                                if (player4.isEmpty()) {
                                    mPlayer4.setError("Please enter player 2");
                                    mPlayer4.requestFocus();
                                    return;
                                }
                                String playerone = mPlayer1.getText().toString();
                                String playertwo = mPlayer2.getText().toString();
                                String playerthree = mPlayer3.getText().toString();
                                String playerfour = mPlayer4.getText().toString();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                                Map newPost = new HashMap();
                                newPost.put("name", playerone);
                                newPost.put("handicap", playertwo);
                                newPost.put("age", playerthree);
                                newPost.put("gender", playerfour);
                                current_user_db.setValue(newPost);
                                Toast.makeText(ProfileActivity.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();



                            }


                        });
                        mBuilder.setNeutralButton("Close ", new DialogInterface.OnClickListener() { // define the 'Cancel' button
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();
                    }

                    }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewdetails = (Button) findViewById(R.id.viewdetails);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name2= name.getText().toString().trim();
                String handicap2= handicap.getText().toString().trim();
                String age2= age.getText().toString().trim();
                String gender2= gender.getText().toString().trim();
                if (name2.isEmpty()) {
                    name.setError("Please enter your name");
                    name.requestFocus();
                    return;
                }
                if (handicap2.isEmpty()) {
                    handicap.setError("Please enter your handicap");
                    handicap.requestFocus();
                    return;
                }
                if (age2.isEmpty()) {
                    age.setError("Please enter your age");
                    age.requestFocus();
                    return;
                }
                if (gender2.isEmpty()) {
                    gender.setError("Please enter your gender");
                    gender.requestFocus();
                    return;
                }
                String name1 = name.getText().toString();
                String handicap1 = handicap.getText().toString();
                String age1 = age.getText().toString();
                String gender1 = gender.getText().toString();
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                Map newPost = new HashMap();
                newPost.put("name",name1);
                newPost.put("handicap",handicap1);
                newPost.put("age",age1);
                newPost.put("gender",gender1);
                current_user_db.setValue(newPost);
                Toast.makeText(ProfileActivity.this, "Details Saved", Toast.LENGTH_SHORT).show();
            }
        });
        viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, view_database.class);
                startActivity(intent);

            }
        });


    }
    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coursemanagement:
                finish();
                startActivity(new Intent(ProfileActivity.this, CourseManagement.class));
                break;
            case R.id.scorecardbtn:
                finish();
                startActivity(new Intent(ProfileActivity.this, scoreboard1.class));
                break;
            case R.id.logbtn:
                FirebaseAuth.getInstance().signOut();
                break;
            case R.id.discussionboardbtn:
                startActivity(new Intent(ProfileActivity.this, discussionboard.class));
                break;
            case R.id.previousscore:
                startActivity(new Intent(ProfileActivity.this, PreviousScores.class));
                break;
            case R.id.leaderboard:
                startActivity(new Intent(ProfileActivity.this, LeaderBoard.class));
                break;
            case R.id.bookingbtn:
                startActivity(new Intent(ProfileActivity.this, Booking.class));
                break;
            case R.id.handicapbutton:
                startActivity(new Intent(ProfileActivity.this, HandicapAdjustment.class));
                break;
        }
    }
private void setupFireBaseListener()
{
    mAuthStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user !=null)
            {

            }else
            {
                Toast.makeText(ProfileActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        }
    };
}
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener !=null)
        {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }




}