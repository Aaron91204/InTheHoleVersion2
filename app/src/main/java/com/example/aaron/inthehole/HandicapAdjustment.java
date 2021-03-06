package com.example.aaron.inthehole;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import pl.droidsonroids.gif.GifImageView;

public class HandicapAdjustment extends AppCompatActivity implements View.OnClickListener {
    EditText Handicap, Net,adjust, retrievehandicap , retrievenet;
    GifImageView photo1,photo2,photo3,photo4,photo5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //setting the orientation to portrait
        setContentView(R.layout.activity_handicap_adjustment);
        findViewById(R.id.calculatehandicap).setOnClickListener(this); //onclick listeners
        findViewById(R.id.handicap2profile).setOnClickListener(this);
        photo1 = (GifImageView)findViewById(R.id.gifImageView); // gif images which are used
        photo2 = (GifImageView)findViewById(R.id.gifImageView2);
        photo3 = (GifImageView)findViewById(R.id.gifImageView3);
        photo4 = (GifImageView)findViewById(R.id.gifImageView4);
        photo5 = (GifImageView)findViewById(R.id.gifImageView5);
        adjust = (EditText) findViewById(R.id.adjust);
        retrievehandicap = (EditText)findViewById(R.id.retreivehandicap); //finding specific parts of the application
        retrievenet = (EditText) findViewById(R.id.retrievenet);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser(); // get current details
        String userid=user.getUid(); // gets current ID
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Scores").child("Net_and_Gross_Scores_Week_25th_April _2018 "); //Database Reference
        ref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) { // Checks to see if a data snapshot exists and if it doesnt then it wont open the activity
                    showData(dataSnapshot);
                } else {
                   finish();
                    Toast.makeText(HandicapAdjustment.this, "You need to submit a score into the competition", Toast.LENGTH_SHORT).show(); // toast message if there is an error
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void showData(DataSnapshot dataSnapshot) { // array to find the details of the member
        ArrayList<String> array  = new ArrayList<>();
        LeaderBoardScores uInfo = dataSnapshot.getValue(LeaderBoardScores.class); // uses the Leaderboard Scores to get and set the information
        array.add(" Net Score : " +uInfo.getNet());
        array.add(" Handicap : " + uInfo.getPlayerHandicap());
        array.add(" Full Name: " + uInfo.getFullName());
        retrievehandicap.setText(uInfo.getPlayerHandicap()); // they are then displayed in these Edit Texts
        retrievenet.setText(uInfo.getNet());
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.handicap2profile: // case to open up the Profile Activity
                finish();
                startActivity(new Intent(HandicapAdjustment.this, ProfileActivity.class));
                break;
            case R.id.calculatehandicap: // calculate handicap
                 Handicap =(EditText)findViewById(R.id.retreivehandicap); //retrieves the two edittexts
                 Net =(EditText)findViewById(R.id.retrievenet);
                String name2= Handicap.getText().toString().trim(); //validation to ensure that the two edit texts are not empty
                String net2= Net.getText().toString().trim();
                if (name2.isEmpty()) { // entry validation
                    Handicap.setError("Please enter your Handicap");
                    Handicap.requestFocus();
                    return;
                }
                if (net2.isEmpty()) {
                    Net.setError("Please enter your Net Score");
                    Net.requestFocus();
                    return;
                }
                double num1, num2; // hanicap calculation
                num1 = Double.parseDouble(Handicap.getText().toString());//set the values of input to be double
                num2 = Double.parseDouble(Net.getText().toString());
                if(num2 >76 && num1>=36) //net above 76 and handicap = 36
                {
                    double sum;
                    sum = num1;
                    adjust.setText(Double.toString(sum));
                    photo1.setVisibility(View.GONE); // postive gifs hidden
                    photo2.setVisibility(View.GONE); // postive gifs hidden
                    photo3.setVisibility(View.GONE); // postive gifs hidden
                    photo4.setVisibility(View.GONE); // postive gifs hidden
                    photo5.setVisibility(View.GONE); // negative gifs shown
                    Toast.makeText(this, "No Change", Toast.LENGTH_SHORT).show();
                }
                else if(num2 >76 && num1<=36) //net above 76 and handicap is then less 36
                {
                double sum;
                sum = num1 +0.1;
                adjust.setText(Double.toString(sum));
                    photo1.setVisibility(View.GONE); // postive gifs hidden
                    photo2.setVisibility(View.GONE); // postive gifs hidden
                    photo3.setVisibility(View.GONE); // postive gifs hidden
                    photo4.setVisibility(View.GONE); // postive gifs hidden
                    photo5.setVisibility(View.VISIBLE); // negative gifs shown
                    Toast.makeText(this, "Bad Score = Increase In Handicap", Toast.LENGTH_SHORT).show();
                }
                else if(num2<72 && num1>=18 && num1<=36 ) //net below 72 and handicap between 36 and 18
                {
                double sum;
                double Net;
                double Par;
                Net = 72 -num2;
                Par = Net*0.4;
                sum = num1 -Par;
                adjust.setText(Double.toString(sum));
                    photo1.setVisibility(View.VISIBLE); // postive gifs shown
                    photo2.setVisibility(View.VISIBLE); // postive gifs shown
                    photo3.setVisibility(View.VISIBLE); // postive gifs shown
                    photo4.setVisibility(View.VISIBLE); // postive gifs shown
                    photo5.setVisibility(View.GONE); // negative gifs shown
                    Toast.makeText(this, "Congrats On Your New Handicap", Toast.LENGTH_SHORT).show();

                }
                else if(num2<72 && num1>=5 && num1<=18 ) //net below 72 and handicap between 18 and 5
                {
                    double sum;
                    double Net;
                    double Par;
                    Net = 72 -num2;
                    Par = Net*0.2;
                    sum = num1 -Par;
                    adjust.setText(Double.toString(sum));
                    Toast.makeText(this, "Congrats On Your New Handicap", Toast.LENGTH_SHORT).show();
                    photo1.setVisibility(View.VISIBLE);// postive gifs shown
                    photo2.setVisibility(View.VISIBLE);// postive gifs shown
                    photo3.setVisibility(View.VISIBLE);// postive gifs shown
                    photo4.setVisibility(View.VISIBLE);// postive gifs shown
                    photo5.setVisibility(View.GONE); // negative gifs shown
                }
                else if(num2<72 && num1>=0 && num1<=5 ) //net below 72 and handicap between 0 and 5
                {
                    double sum;
                    double Net;
                    double Par;
                    Net = 72 -num2;
                    Par = Net*0.1;
                    sum = num1 -Par;
                    adjust.setText(Double.toString(sum));
                    Toast.makeText(this, "Congrats On Your New Handicap", Toast.LENGTH_SHORT).show();
                    photo1.setVisibility(View.VISIBLE);// postive gifs shown
                    photo2.setVisibility(View.VISIBLE);// postive gifs shown
                    photo3.setVisibility(View.VISIBLE);// postive gifs shown
                    photo4.setVisibility(View.VISIBLE);// postive gifs shown
                    photo5.setVisibility(View.GONE); // negative gifs shown
                }
                else if(num2>72 && num2<=76 ) //net above 72 but less than 76
                {
                    double sum;
                    sum = num1 +0; //no change to handicap
                    adjust.setText(Double.toString(sum));
                    Toast.makeText(this, "No Change", Toast.LENGTH_SHORT).show();
                    photo1.setVisibility(View.GONE);// postive gifs hidden
                    photo2.setVisibility(View.GONE);// postive gifs hidden
                    photo3.setVisibility(View.GONE);// postive gifs hidden
                    photo4.setVisibility(View.GONE);// postive gifs hidden
                    photo5.setVisibility(View.GONE);// negative gifs hidden
                }
                break;
        }

    }
    public class InputFilterMinMax implements InputFilter { // input validation which is set up

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
}
