package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.RoomInfo;
import com.github.library.bubbleview.BubbleTextView;

public class ChatRoomActivity extends AppCompatActivity {
    EditText chatroomactivity_edittext_message;
    FloatingActionButton chatroomactivity_floatingbutton_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }
        Bundle bundle = getIntent().getExtras();
        RoomInfo roomInfo = bundle.getParcelable("roominfo");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(roomInfo.getRoomName());


        chatroomactivity_edittext_message = findViewById(R.id.chatroomactivity_edittext_message);
        chatroomactivity_floatingbutton_send = findViewById(R.id.chatroomactivity_floatingbutton_send);


        RecyclerView recyclerView = findViewById(R.id.chatroomactivity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChatRoomActivityRecyclerViewAdapter chatRoomActivityRecyclerViewAdapter = new ChatRoomActivityRecyclerViewAdapter();
        recyclerView.setAdapter(chatRoomActivityRecyclerViewAdapter);

        chatroomactivity_floatingbutton_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // id 본인일 경우 화면


                //id 본인 아닐경우 화면


            }
        });


    }

    public class ChatRoomActivityRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message,viewGroup,false);
            return new CustomViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {

            public LinearLayout chatroomitem_linearlayout_recivemessage;
            public ImageView chatroomitem_recive_imageview_profile;
            public TextView chatroomitem_recive_textview_id;
            public TextView chatroomitem_recive_textview_message_lasttime;
            public BubbleTextView chatroomitem_recive_textview_message;


            public LinearLayout chatroomitem_linearlayout_sendmessage;
            public TextView chatroomitem_send_textview_message_lasttime;
            public BubbleTextView chatroomitem_send_textview_message;

            public CustomViewHolder(View view) {
                super(view);
                chatroomitem_linearlayout_recivemessage = view.findViewById(R.id.chatroomitem_linearlayout_recivemessage);
                chatroomitem_recive_imageview_profile = view.findViewById(R.id.chatroomitem_recive_imageview_profile);
                chatroomitem_recive_textview_id = view.findViewById(R.id.chatroomitem_recive_textview_id);
                chatroomitem_recive_textview_message_lasttime = view.findViewById(R.id.chatroomitem_recive_textview_message_lasttime);
                chatroomitem_recive_textview_message = view.findViewById(R.id.chatroomitem_recive_textview_message);

                chatroomitem_linearlayout_sendmessage = view.findViewById(R.id.chatroomitem_linearlayout_sendmessage);
                chatroomitem_send_textview_message_lasttime = view.findViewById(R.id.chatroomtiem_send_textview_message_lasttime);
                chatroomitem_send_textview_message = view.findViewById(R.id.chatroomitem_send_textview_message);


            }
        }
    }
}
