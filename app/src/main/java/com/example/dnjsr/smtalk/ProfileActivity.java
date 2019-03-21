package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnjsr.smtalk.info.UserInfo;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileactivity_imageview_profileimage;
    private TextView profileactivity_textview_username;
    private TextView profileactivity_textview_usermessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        Bundle bundle = getIntent().getExtras();                                                                //선택된 userInfo객체 받아옴
        final UserInfo userInfo = bundle.getParcelable("selectedinfo");

        profileactivity_imageview_profileimage = findViewById(R.id.profileactivity_imageview_profileimage);
        profileactivity_textview_username = findViewById(R.id.profileactivity_textview_username);
        profileactivity_textview_usermessage =findViewById(R.id.profileactivity_textview_usermessage);

        profileactivity_textview_username.setText(userInfo.getUserName());
        profileactivity_textview_usermessage.setText(userInfo.getContents());

        profileactivity_imageview_profileimage.setOnClickListener(new View.OnClickListener() {               //image눌러 화면이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProfilepictureActivity.class);
                intent.putExtra("userimageurl",userInfo.getProfileImg());
                startActivity(intent);
            }
        });


    }
}
