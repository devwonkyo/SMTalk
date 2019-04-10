package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {
    TextView myprofileactivity_textview_mycomment;
    CircleImageView myprofileactivity_imageview_myprofileimage;
    TextView myprofileactivity_textview_username;
    ImageView myprofileactivity_imageview_myaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        myprofileactivity_textview_mycomment = findViewById(R.id.myprofileactivity_textview_mycomment);
        myprofileactivity_imageview_myprofileimage = findViewById(R.id.myprofileactivity_imageview_myprofileimage);
        myprofileactivity_textview_username = findViewById(R.id.myprofileactivity_textview_username);
        myprofileactivity_imageview_myaccount = findViewById(R.id.myprofileactivity_imageview_myaccount);

        myprofileactivity_textview_mycomment.setText(CurrentUserInfo.getUserInfo().getComment());
        myprofileactivity_imageview_myprofileimage.setImageBitmap(CurrentUserInfo.getUserInfo().getProfileImg());
        myprofileactivity_textview_username.setText(CurrentUserInfo.getUserInfo().getUserName());

        myprofileactivity_imageview_myprofileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProfilepictureActivity.class);
                startActivity(intent);
            }
        });

        myprofileactivity_imageview_myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
