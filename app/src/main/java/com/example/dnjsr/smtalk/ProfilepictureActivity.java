package com.example.dnjsr.smtalk;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.dnjsr.smtalk.info.UserInfo;

public class ProfilepictureActivity extends AppCompatActivity {
    private ImageView profilepictureactivity_imageview_profileimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepicture);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        Bundle bundle = getIntent().getExtras();
        String userimageurl = bundle.getParcelable("userimageurl");


        profilepictureactivity_imageview_profileimage = findViewById(R.id.profilepictureactivity_imageview_profileimage);

        //profilepictureactivity_imageview_profileimage.setImageURI();

    }
}
