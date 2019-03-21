package com.example.dnjsr.smtalk.fragment;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnjsr.smtalk.R;
import com.example.dnjsr.smtalk.info.RoomInfo;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ChatFragment extends android.support.v4.app.Fragment {
    private ChatFragmentRecyclerViewAdapter chatFragmentRecyclerViewAdapter;
    private RecyclerView fragment_chat_recyclerview;
  /*  Bundle recive_bundle = getArguments();
    RoomInfo roominfo = recive_bundle.getParcelable("roominfo");
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        Log.d("fragmentId",this.toString());
        fragment_chat_recyclerview = view.findViewById(R.id.chatfragment_recyclerview);
        fragment_chat_recyclerview.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        chatFragmentRecyclerViewAdapter = new ChatFragmentRecyclerViewAdapter();
        fragment_chat_recyclerview.setAdapter(chatFragmentRecyclerViewAdapter);
        return view;
    }


    class ChatFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        ArrayList<RoomInfo> roomAdapterList;
        public ChatFragmentRecyclerViewAdapter() {
            roomAdapterList = new ArrayList<>();
            roomAdapterList.add(new RoomInfo("프젝","3"));
            roomAdapterList.add(new RoomInfo("1학년과톡","15"));
            roomAdapterList.add(new RoomInfo("ㅂㄹㅊㄱ","6"));
            //roomAdapterList.add(roominfo);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chatroom,viewGroup,false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ((CustomViewHolder)viewHolder).chatroomitem_imageview.setImageResource(R.drawable.icon_account);
            ((CustomViewHolder)viewHolder).chatroomitem_textview_chatroomname.setText(roomAdapterList.get(i).getRoomName());
            ((CustomViewHolder)viewHolder).chatroomitem_textviewoomnumber.setText(roomAdapterList.get(i).getMemberNumber());

        }

        @Override
        public int getItemCount() {
            return roomAdapterList.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            private ImageView chatroomitem_imageview;
            private TextView chatroomitem_textview_chatroomname;
            private TextView chatroomitem_textviewoomnumber;
            private TextView chatroomitem_textview_chatroomlastmessage;
            private TextView chatroomitem_textview_chatroomlastmessagetime;
            public CustomViewHolder(View view) {
                super(view);
                chatroomitem_imageview = view.findViewById(R.id.chatroomitem_imageview);
                chatroomitem_textview_chatroomname = view.findViewById(R.id.chatroomitem_textview_chatroomname);
                chatroomitem_textviewoomnumber = view.findViewById(R.id.chatroomitem_textview_chatroomnumber);
                chatroomitem_textview_chatroomlastmessage = view.findViewById(R.id.chatroomitem_textview_chatroomlastmessage);
                chatroomitem_textview_chatroomlastmessagetime = view.findViewById(R.id.chatroomitem_textview_chatroomlastmessagetime);
            }
        }
    }
}
