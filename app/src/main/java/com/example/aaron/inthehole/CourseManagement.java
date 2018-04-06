package com.example.aaron.inthehole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CourseManagement extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_management);
        findViewById(R.id.profilebutton).setOnClickListener(this);
        findViewById(R.id.hole1btn).setOnClickListener(this);
        findViewById(R.id.hole2btn).setOnClickListener(this);
        findViewById(R.id.hole3btn).setOnClickListener(this);
        findViewById(R.id.hole4btn).setOnClickListener(this);
        findViewById(R.id.hole5btn).setOnClickListener(this);
        findViewById(R.id.hole6btn).setOnClickListener(this);
        findViewById(R.id.hole7btn).setOnClickListener(this);
        findViewById(R.id.hole8btn).setOnClickListener(this);
        findViewById(R.id.hole9btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profilebutton: // case to return to the profile
                finish();
                startActivity(new Intent(CourseManagement.this, ProfileActivity.class));
                break;
            case R.id.hole1btn: // each of these next cases create an alert dialog to show the images of each hole. This shows the holes and fairways
                ImageView image =new ImageView(this);
                image.setImageResource(R.drawable.hole1); // the image used
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(this).
                                setMessage("Hole 1"). // Positive Messages
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image);
                builder.create().show();
                break;
            case R.id.hole2btn:
                ImageView image1 =new ImageView(this);
                image1.setImageResource(R.drawable.hole2);
                AlertDialog.Builder builder1 =
                        new AlertDialog.Builder(this).
                                setMessage("Hole 2").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image1);
                builder1.create().show();
                break;
            case R.id.hole3btn:
                ImageView image3 =new ImageView(this);
                image3.setImageResource(R.drawable.hole3);
                AlertDialog.Builder builder3 =
                        new AlertDialog.Builder(this).
                                setMessage("Hole 3").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image3);
                builder3.create().show();
                break;
            case R.id.hole4btn:
                ImageView image4 =new ImageView(this);
                image4.setImageResource(R.drawable.hole4);
                AlertDialog.Builder builder4 =
                        new AlertDialog.Builder(this).
                                setMessage("Hole 4").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image4);
                builder4.create().show();
                break;
            case R.id.hole5btn:
                ImageView image5 =new ImageView(this);
                image5.setImageResource(R.drawable.hole5);
                AlertDialog.Builder builder5 =
                        new AlertDialog.Builder(this).
                                setMessage("Hole 5").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image5);
                builder5.create().show();
                break;
            case R.id.hole6btn:
                ImageView image6 =new ImageView(this);
                image6.setImageResource(R.drawable.hole6);
                AlertDialog.Builder builder6 =
                        new AlertDialog.Builder(this).
                                setMessage("Hole 6").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image6);
                builder6.create().show();
                break;
            case R.id.hole7btn:
                ImageView image7 =new ImageView(this);
                image7.setImageResource(R.drawable.hole7);
                AlertDialog.Builder builder7 =
                        new AlertDialog.Builder(this).
                                setMessage("Hole 7").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image7);
                builder7.create().show();
                break;
            case R.id.hole8btn:
                ImageView image8 =new ImageView(this);
                image8.setImageResource(R.drawable.hole8);
                AlertDialog.Builder builder8 =
                        new AlertDialog.Builder(this).
                                setMessage("Hole 8").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image8);
                builder8.create().show();
                break;
            case R.id.hole9btn:
                ImageView image9 =new ImageView(this);
                image9.setImageResource(R.drawable.hole9);
                AlertDialog.Builder builder9 =
                        new AlertDialog.Builder(this).
                                setMessage("Hole 9").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image9);
                builder9.create().show();
                break;

        }
    }
}

