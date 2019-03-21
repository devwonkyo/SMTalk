package com.example.dnjsr.smtalk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dnjsr.smtalk.fragment.ChatFragment;
import com.example.dnjsr.smtalk.fragment.PeopleFragment;
import com.example.dnjsr.smtalk.fragment.SettingFragment;
import com.example.dnjsr.smtalk.info.RoomInfo;
import com.example.dnjsr.smtalk.info.UserInfo;

public class MainActivity extends AppCompatActivity {
    ActionBar actionBar;
    MenuInflater inflater;
    MenuItem item_newfriend;
    MenuItem item_newroom;
    static View dialog_newroom;
    static View dialog_newfriend;
    static String roomname;
    static String membercount;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu,menu);
        item_newfriend = menu.findItem(R.id.item_newfriend);
        item_newroom = menu.findItem(R.id.item_newroom);
        item_newroom.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_newfriend:
                DialogFragment friendDialogFragmnet = new FriendDialogFragment();
                friendDialogFragmnet.setCancelable(false);
                friendDialogFragmnet.show(getSupportFragmentManager(),"frienddialogfragment");
                return true;
            case R.id.item_newroom:
                DialogFragment roomDialogFragmnet = new RoomDialogFragment();
                roomDialogFragmnet.setCancelable(false);
                roomDialogFragmnet.show(getSupportFragmentManager(),"roomdialogfragment");
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = getLayoutInflater();
        dialog_newfriend = inflater.inflate(R.layout.dialog_newfriend,null);
        dialog_newroom = inflater.inflate(R.layout.dialog_newroom,null);                      //dialog layout inflate

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        Bundle bundle = getIntent().getExtras();
        UserInfo userinfo = bundle.getParcelable("userinfo");


        /*Intent intent = getIntent();
        UserInfo userInfo = intent.getParcelableExtra("userinfo");*/

        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,new PeopleFragment()).commit();  //people fragment로 초기화

        BottomNavigationView mainactivity_bottomnavigationview = findViewById(R.id.mainactivity_bottomnavigationview);

        mainactivity_bottomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_people :
                        actionBar = getSupportActionBar();
                        actionBar.setTitle("친구");
                        item_newfriend.setVisible(true);
                        item_newroom.setVisible(false);

                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,new PeopleFragment()).commit();
                        return true;
                    case R.id.action_chat:
                        item_newfriend.setVisible(false);
                        item_newroom.setVisible(true);
                        actionBar = getSupportActionBar();
                        actionBar.setTitle("채팅");
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,new ChatFragment()).commit();
                        return true;
                    case R.id.action_setting:
                        actionBar = getSupportActionBar();
                        actionBar.setTitle("설정");
                        item_newfriend.setVisible(false);
                        item_newroom.setVisible(false);
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,new SettingFragment()).commit();
                        return true;
                }
                return false;
            }
        });

    }

    public static class FriendDialogFragment extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("친구추가").setView(R.layout.dialog_newfriend).setPositiveButton("추가", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return builder.create();
        }
    }


    public static class RoomDialogFragment extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("방 생성").setView(R.layout.dialog_newroom).setPositiveButton("생성", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText dialog_edittext_roomname = dialog_newroom.findViewById(R.id.dialog_edittext_roomname);
                    EditText dialog_edittext_membercount = dialog_newroom.findViewById(R.id.dialog_edittext_membercount);
                    roomname = dialog_edittext_roomname.getText().toString();
                    membercount = dialog_edittext_membercount.getText().toString();
                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return builder.create();
        }
    }
}
