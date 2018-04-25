package com.example.aaron.inthehole;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.text.format.DateFormat;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;

import java.util.Date;


public class discussionboard extends AppCompatActivity {
    private EditText editMessage;
    private DatabaseReference mDatabase;
    private RecyclerView mMessageList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//sets orientation of app to portrait
        setContentView(R.layout.activity_discussionboard);
        editMessage=(EditText)findViewById(R.id.editMessageE);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages"); // Database Reference
        mMessageList = (RecyclerView)findViewById(R.id.messageRec); // Listview to output images
        mMessageList.setHasFixedSize(true); //sets the size
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this); // Linear Layout Manager
        linearLayoutManager.setStackFromEnd(true);
        mMessageList.setLayoutManager(linearLayoutManager); // set the listview to be the Linear Layout
        mAuth = FirebaseAuth.getInstance(); // Firebase Instance
    }
    public void sendButtonClicked(View view) {
        mCurrentUser = mAuth.getCurrentUser(); // finds current user details
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid()); // finds the details of the current user using their UID
        final String messageValue = editMessage.getText().toString().trim(); //trims the message to make it a string
        if (!TextUtils.isEmpty(messageValue)) {
            final DatabaseReference newPost = mDatabase.push(); // pushes the message to the database
            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) { // message content
                    Date currentTime = Calendar.getInstance().getTime(); //timestamp for each message
                    String currentTimeStamp = currentTime.toString();
                    newPost.child("timeStamp").setValue(currentTimeStamp); // timestamp
                    newPost.child("content").setValue(messageValue); //content
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() { //name
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            mMessageList.scrollToPosition(mMessageList.getAdapter().getItemCount());//allows the listview to be scrollable
            editMessage.setText(""); //the edit message will always be blank
        }
    }
    public void home(View view) // intent to open the profile activity
    {
        finish();
        startActivity(new Intent(discussionboard.this, ProfileActivity.class));
    }
    @Override
    protected void onStart()
    {
      super.onStart();
        FirebaseRecyclerAdapter<Message,MessageViewHolder> FBRA = new FirebaseRecyclerAdapter<Message, MessageViewHolder>( //recycles the listview and finding the format of singlemessagelayout
                Message.class,
                R.layout.singlemessagelayout,
                MessageViewHolder.class,
                mDatabase) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) { //set the details to be outputted
                viewHolder.setContent(model.getContent());
                viewHolder.setUsername(model.getUsername());
                viewHolder.setTime(model.getTime());
            }
        };
        mMessageList.setAdapter(FBRA);
    }
    public static class MessageViewHolder extends RecyclerView.ViewHolder // setting the information and displaying it in the format
    {
        View mView;
        public MessageViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setContent(String content)
        {
            TextView messag_content = (TextView) mView.findViewById(R.id.messageText);
            messag_content.setText(content);
        }
        public void setUsername(String username)
        {
            TextView username_content = (TextView)mView.findViewById(R.id.usernametext);
            username_content.setText(username);
        }
        public void setTime(String timeStamp)
        {
            Date currentTime = Calendar.getInstance().getTime();
            String currentTimeStamp = currentTime.toString();
            TextView time_content = (TextView)mView.findViewById(R.id.messageTime);
            time_content.setText(currentTimeStamp);
        }
    }
}
