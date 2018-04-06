package com.example.aaron.inthehole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.aaron.inthehole.R.id.addbtn;
import static com.example.aaron.inthehole.R.id.edithandicap;
import static com.example.aaron.inthehole.R.id.editresult;
import static com.example.aaron.inthehole.R.id.netscore;
import static com.example.aaron.inthehole.R.id.profilebtn3;

public class scoreboard1 extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ViewDatabase";

EditText firstNum;
EditText secondNum;
    EditText thirdNum,result1,handicap,net,name;
    EditText hole4,hole5,hole6,hole7,hole8,hole9,hole10,hole11,hole12,hole13,hole14,hole15,hole16,hole17,hole18;
TextView result;
Button btnadd,save;
    private ListView mListView1;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard1);
        findViewById(profilebtn3).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        firstNum =(EditText)findViewById(R.id.hole1);
        firstNum.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        secondNum =(EditText)findViewById(R.id.hole2);
        secondNum.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        secondNum =(EditText)findViewById(R.id.hole2);
        thirdNum =(EditText)findViewById(R.id.hole3);
        thirdNum.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole4=(EditText)findViewById(R.id.hole4);
        hole4.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole5=(EditText)findViewById(R.id.hole5);
        hole5.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole6=(EditText)findViewById(R.id.hole6);
        hole6.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole7=(EditText)findViewById(R.id.hole7);
        hole7.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole8=(EditText)findViewById(R.id.hole8);
        hole8.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole9=(EditText)findViewById(R.id.hole9);
        hole9.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole10=(EditText)findViewById(R.id.hole10);
        hole10.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole11=(EditText)findViewById(R.id.hole11);
        hole11.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole12=(EditText)findViewById(R.id.hole12);
        hole12.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole13=(EditText)findViewById(R.id.hole13);
        hole13.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole14=(EditText)findViewById(R.id.hole14);
        hole14.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole15=(EditText)findViewById(R.id.hole15);
        hole15.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole16=(EditText)findViewById(R.id.hole16);
        hole16.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole17=(EditText)findViewById(R.id.hole17);
        hole17.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        hole18=(EditText)findViewById(R.id.hole18);
        hole18.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        handicap=(EditText)findViewById(R.id.edithandicap);
        handicap.setFilters(new InputFilter[]{new InputFilterMinMax("1", "36")});
        net =(EditText)findViewById(R.id.netscore);
        result =(TextView) findViewById(R.id.textView3);
        result1 = (EditText)findViewById(R.id.editresult);
        btnadd =(Button)findViewById(R.id.addbtn);
        name=(EditText)findViewById(R.id.editname);
        name.setKeyListener(null);
        handicap.setKeyListener(null);
        save = (Button) findViewById(R.id.savedetails);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name2= name.getText().toString().trim();
                if (name2.isEmpty()) {
                    name.setError("Please enter your name");
                    name.requestFocus();
                    return;
                }
                String result = result1.getText().toString();
                String handicap3 = handicap.getText().toString();
                String net3 = net.getText().toString();
                String name13 = name.getText().toString();
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Scores").child("Net_and_Gross_Scores_Week_17th_March _2018 ").child(user_id);
                Map newPost = new HashMap();
                newPost.put("Gross",result);
                newPost.put("PlayerHandicap",handicap3);
                newPost.put("Net",net3);
                newPost.put("FullName",name13);
                current_user_db.setValue(newPost);
                Toast.makeText(scoreboard1.this, "Score Entered Into Competition", Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void showData(DataSnapshot dataSnapshot) {
        ArrayList<String> array  = new ArrayList<>();
        UserInformation uInfo = dataSnapshot.getValue(UserInformation.class);
        name.setText(uInfo.getName());
        handicap.setText(uInfo.getHandicap());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case profilebtn3:
                finish();
                startActivity(new Intent(scoreboard1.this, ProfileActivity.class));
                break;
        }

    }
    public static class InputFilterMinMax implements InputFilter {

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
    public void onAdd(View view)
    {
        String first= firstNum.getText().toString().trim();
        String second= secondNum.getText().toString().trim();
        String third= thirdNum.getText().toString().trim();
        String fourth= hole4.getText().toString().trim();
        String fifth= hole5.getText().toString().trim();
        String sixth= hole6.getText().toString().trim();
        String seventh= hole7.getText().toString().trim();
        String eighth= hole8.getText().toString().trim();
        String ninth= hole9.getText().toString().trim();
        String tenth= hole10.getText().toString().trim();
        String eleventh= hole11.getText().toString().trim();
        String twelth= hole12.getText().toString().trim();
        String thirteen= hole13.getText().toString().trim();
        String fourteen= hole14.getText().toString().trim();
        String fifteen= hole15.getText().toString().trim();
        String sixteen= hole16.getText().toString().trim();
        String seventeen= hole17.getText().toString().trim();
        String eighteen= hole18.getText().toString().trim();
        if (first.isEmpty()) {
            firstNum.setError("Please enter score");
            firstNum.requestFocus();
            return;
        }
        if (second.isEmpty()) {
            secondNum.setError("Please enter score");
            secondNum.requestFocus();
            return;
        }
        if (third.isEmpty()) {
            thirdNum.setError("Please enter score");
            thirdNum.requestFocus();
            return;
        }
        if (fourth.isEmpty()) {
            hole4.setError("Please enter score");
            hole4.requestFocus();
            return;
        }if (fifth.isEmpty()) {
        hole5.setError("Please enter score");
        hole5.requestFocus();
        return;
    }
        if (sixth.isEmpty()) {
            hole6.setError("Please enter score");
            hole6.requestFocus();
            return;
        }
        if (seventh.isEmpty()) {
            hole7.setError("Please enter score");
            hole7.requestFocus();
            return;
        }if (eighth.isEmpty()) {
        hole8.setError("Please enter score");
        hole8.requestFocus();
        return;
    }
        if (ninth.isEmpty()) {
            hole9.setError("Please enter score");
            hole9.requestFocus();
            return;
        }
        if (tenth.isEmpty()) {
            hole10.setError("Please enter score");
            hole10.requestFocus();
            return;
        }
        if (eleventh.isEmpty()) {
            hole11.setError("Please enter score");
            hole11.requestFocus();
            return;
        }
        if (twelth.isEmpty()) {
            hole12.setError("Please enter score");
            hole12.requestFocus();
            return;
        }
        if (thirteen.isEmpty()) {
            hole13.setError("Please enter score");
            hole13.requestFocus();
            return;
        }
        if (fourteen.isEmpty()) {
            hole14.setError("Please enter score");
            hole14.requestFocus();
            return;
        }
        if (fifteen.isEmpty()) {
            hole15.setError("Please enter score");
            hole15.requestFocus();
            return;
        }
        if (sixteen.isEmpty()) {
            hole16.setError("Please enter score");
            hole16.requestFocus();
            return;
        }
        if (seventeen.isEmpty()) {
            hole17.setError("Please enter score");
            hole17.requestFocus();
            return;
        }
        if (eighteen.isEmpty()) {
            hole18.setError("Please enter score");
            hole18.requestFocus();
            return;
        }

        int num1, num2,num3,num4,num5,num6,num7,num8,num9,num10,num11,num12,num13,num14,num15,num16,num17,num18;
        num1 = Integer.parseInt(firstNum.getText().toString());
        num2 = Integer.parseInt(secondNum.getText().toString());
        num3 = Integer.parseInt(thirdNum.getText().toString());
        num4 = Integer.parseInt(hole4.getText().toString());
        num5 = Integer.parseInt(hole5.getText().toString());
        num6 = Integer.parseInt(hole6.getText().toString());
        num7 = Integer.parseInt(hole7.getText().toString());
        num8 = Integer.parseInt(hole8.getText().toString());
        num9 = Integer.parseInt(hole9.getText().toString());
        num10 = Integer.parseInt(hole10.getText().toString());
        num11 = Integer.parseInt(hole11.getText().toString());
        num12 = Integer.parseInt(hole12.getText().toString());
        num13 = Integer.parseInt(hole13.getText().toString());
        num14 = Integer.parseInt(hole14.getText().toString());
        num15 = Integer.parseInt(hole15.getText().toString());
        num16 = Integer.parseInt(hole16.getText().toString());
        num17 = Integer.parseInt(hole17.getText().toString());
        num18 = Integer.parseInt(hole18.getText().toString());
        int sum =num1+num2+num3+num4+num5+num6+num7+num8+num9+num10+num11+num12+num13+num14+num15+num16+num17+num18;
        result1.setText(Integer.toString(sum));
        String result= result1.getText().toString().trim();
        String handicap1= handicap.getText().toString().trim();
        if (result.isEmpty()) {
            result1.setError("Please enter score");
            result1.requestFocus();
            return;
        }
        if (handicap1.isEmpty()) {
            handicap.setError("Please enter your handicap");
            handicap.requestFocus();
            return;
        }

        int sum1,hand1;
        sum1 = Integer.parseInt(result1.getText().toString());
        hand1 = Integer.parseInt(handicap.getText().toString());

        int score =sum1-hand1;
        net.setText(Integer.toString(score));


    }
    public void calculate(View view)
    {
        String first1= firstNum.getText().toString().trim();
        String second1= secondNum.getText().toString().trim();
        String third1= thirdNum.getText().toString().trim();
        String fourth1= hole4.getText().toString().trim();
        String fifth1= hole5.getText().toString().trim();
        String sixth1= hole6.getText().toString().trim();
        String seventh1= hole7.getText().toString().trim();
        String eighth1= hole8.getText().toString().trim();
        String ninth1= hole9.getText().toString().trim();
        String tenth1= hole10.getText().toString().trim();
        String eleventh1= hole11.getText().toString().trim();
        String twelth1= hole12.getText().toString().trim();
        String thirteen1= hole13.getText().toString().trim();
        String fourteen1= hole14.getText().toString().trim();
        String fifteen1= hole15.getText().toString().trim();
        String sixteen1= hole16.getText().toString().trim();
        String seventeen1= hole17.getText().toString().trim();
        String eighteen1= hole18.getText().toString().trim();
        if (first1.isEmpty()) {
            firstNum.setError("Please enter score");
            firstNum.requestFocus();
            return;
        }
        if (second1.isEmpty()) {
            secondNum.setError("Please enter score");
            secondNum.requestFocus();
            return;
        }
        if (third1.isEmpty()) {
            thirdNum.setError("Please enter score");
            thirdNum.requestFocus();
            return;
        }
        if (fourth1.isEmpty()) {
            hole4.setError("Please enter score");
            hole4.requestFocus();
            return;
        }if (fifth1.isEmpty()) {
        hole5.setError("Please enter score");
        hole5.requestFocus();
        return;
    }
        if (sixth1.isEmpty()) {
            hole6.setError("Please enter score");
            hole6.requestFocus();
            return;
        }
        if (seventh1.isEmpty()) {
            hole7.setError("Please enter score");
            hole7.requestFocus();
            return;
        }if (eighth1.isEmpty()) {
        hole8.setError("Please enter score");
        hole8.requestFocus();
        return;
    }
        if (ninth1.isEmpty()) {
            hole9.setError("Please enter score");
            hole9.requestFocus();
            return;
        }
        if (tenth1.isEmpty()) {
            hole10.setError("Please enter score");
            hole10.requestFocus();
            return;
        }
        if (eleventh1.isEmpty()) {
            hole11.setError("Please enter score");
            hole11.requestFocus();
            return;
        }
        if (twelth1.isEmpty()) {
            hole12.setError("Please enter score");
            hole12.requestFocus();
            return;
        }
        if (thirteen1.isEmpty()) {
            hole13.setError("Please enter score");
            hole13.requestFocus();
            return;
        }
        if (fourteen1.isEmpty()) {
            hole14.setError("Please enter score");
            hole14.requestFocus();
            return;
        }
        if (fifteen1.isEmpty()) {
            hole15.setError("Please enter score");
            hole15.requestFocus();
            return;
        }
        if (sixteen1.isEmpty()) {
            hole16.setError("Please enter score");
            hole16.requestFocus();
            return;
        }
        if (seventeen1.isEmpty()) {
            hole17.setError("Please enter score");
            hole17.requestFocus();
            return;
        }
        if (eighteen1.isEmpty()) {
            hole18.setError("Please enter score");
            hole18.requestFocus();
            return;
        }
        String first= firstNum.getText().toString();
        String second= secondNum.getText().toString();
        String third= thirdNum.getText().toString();
        String fourth= hole4.getText().toString();
        String fifth= hole5.getText().toString();
        String sixth= hole6.getText().toString();
        String seventh= hole7.getText().toString();
        String eighth= hole8.getText().toString();
        String ninth= hole9.getText().toString();
        String tenth= hole10.getText().toString();
        String eleventh= hole11.getText().toString();
        String twelth= hole12.getText().toString();
        String thirteen= hole13.getText().toString();
        String fourteen= hole14.getText().toString();
        String fifteen= hole15.getText().toString();
        String sixteen= hole16.getText().toString();
        String seventeen= hole17.getText().toString();
        String eighteen= hole18.getText().toString();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Scores").child("Hole_Scores_17th_March_2018 ").child(user_id);
        Map newPost1 = new HashMap();
        newPost1.put("Hole1",first);
        newPost1.put("Hole2",second);
        newPost1.put("Hole3",third);
        newPost1.put("Hole4",fourth);
        newPost1.put("Hole5",fifth);
        newPost1.put("Hole6",sixth);
        newPost1.put("Hole7",seventh);
        newPost1.put("Hole8",eighth);
        newPost1.put("Hole9",ninth);
        newPost1.put("Hole10",tenth);
        newPost1.put("Hole11",eleventh);
        newPost1.put("Hole12",twelth);
        newPost1.put("Hole13",thirteen);
        newPost1.put("Hole14",fourteen);
        newPost1.put("Hole15",fifteen);
        newPost1.put("Hole16",sixteen);
        newPost1.put("Hole17",seventeen);
        newPost1.put("Hole18",eighteen);
        current_user_db.setValue(newPost1);
        Toast.makeText(scoreboard1.this, "Score Saved", Toast.LENGTH_SHORT).show();
    }
    public void clear(View v){
        firstNum.setText("");
        secondNum.setText("");
        thirdNum.setText("");
        hole4.setText("");
        hole5.setText("");
        hole6.setText("");
        hole7.setText("");
        hole8.setText("");
        hole9.setText("");
        hole10.setText("");
        hole11.setText("");
        hole12.setText("");
        hole13.setText("");
        hole14.setText("");
        hole15.setText("");
        hole16.setText("");
        hole17.setText("");
        hole18.setText("");
        result.setText("");
        result1.setText("");
        net.setText("");
        handicap.setText("");
        name.setText("");
    }
}
