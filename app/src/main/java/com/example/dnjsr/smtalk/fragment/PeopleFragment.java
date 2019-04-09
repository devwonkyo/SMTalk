package com.example.dnjsr.smtalk.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnjsr.smtalk.ProfileActivity;
import com.example.dnjsr.smtalk.R;
import com.example.dnjsr.smtalk.globalVariables.SelectedUserInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.github.nkzawa.socketio.client.Url;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment {


    List<UserInfo> userInfos = new ArrayList<>();


    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    private EditText peoplefragment_edittext_search;
    private ImageView peoplefragment_imageview_search;
    private TextView peoplefragment_textview_friendlist;
    private PeopleFragmentRecyclerViewAdapter peopleFragmentRecyclerViewAdapter;

    public void notifyDataSetChangeed(ArrayList<UserInfo> userInfos){
        peopleFragmentRecyclerViewAdapter.filterList(userInfos);
    }

    


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_people,container,false);
        peoplefragment_imageview_search = view.findViewById(R.id.peoplefragment_imageview_search);
        peoplefragment_edittext_search = view.findViewById(R.id.peoplefragment_edittext_search);
        peoplefragment_textview_friendlist = view.findViewById(R.id.peoplefragment_textview_friendlist);


       //userInfos = new ArrayList<>();                               //친구목록 data input

        /*userInfos.add(new UserInfo("ww","wonkyo","wonkyo","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","dong","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","gun","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","wkyo","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","hyo","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","ggggyo","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","woleho","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","wttyo","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","이동o","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","w원교yo","","오늘 코딩 ㄴ","hi"));
        userInfos.add(new UserInfo("ww","wonkyo","wo효근o","","오늘 코딩 ㄴ","hi"));*/



        peoplefragment_edittext_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.peoplefragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        peopleFragmentRecyclerViewAdapter = new PeopleFragmentRecyclerViewAdapter();
        recyclerView.setAdapter(peopleFragmentRecyclerViewAdapter);


        return view;

    }
    private void filter(String text){
        ArrayList<UserInfo> filteredList = new ArrayList<>();

        if(text.equals(null)){
            for(UserInfo userInfo : userInfos){
                filteredList.add(userInfo);
            }
        }else {
            for (UserInfo userInfo : userInfos) {
                if (userInfo.getUserName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(userInfo);
                }
            }
        }
        peopleFragmentRecyclerViewAdapter.filterList(filteredList);
    }

    class PeopleFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<UserInfo> adapterList;

        public PeopleFragmentRecyclerViewAdapter() {


            adapterList = new ArrayList<UserInfo>();                                    //filtering된 친구목록

           for(UserInfo userInfo : userInfos){
                adapterList.add(userInfo);
            }
            peoplefragment_textview_friendlist.setText("친구 "+ adapterList.size());
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend,viewGroup,false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
            ((CustomViewHolder)viewHolder).profileImage.setImageBitmap(adapterList.get(i).getProfileImg());
            ((CustomViewHolder)viewHolder).textview_name.setText(adapterList.get(i).getUserName());
            ((CustomViewHolder)viewHolder).textview_message.setText(adapterList.get(i).getComment());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SelectedUserInfo.setUserInfo(adapterList.get(i));
//                    UserInfo selectedUserInfo = new UserInfo(adapterList.get(i).getUserName(),adapterList.get(i).getComment());
                    Intent intent = new Intent(v.getContext(),ProfileActivity.class);
                    /*intent.putExtra("selectedinfo",selectedUserInfo);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    adapterList.get(i).getProfileImg().compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
                    intent.putExtra("byteArray",byteArrayOutputStream.toByteArray());*/
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            peoplefragment_textview_friendlist.setText("친구 "+ adapterList.size());
            return adapterList.size();
        }

        public void filterList(ArrayList<UserInfo> filteredList){
            adapterList = filteredList;
            notifyDataSetChanged();
        }


        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView profileImage;
            public TextView textview_name;
            public TextView textview_message;
            public CustomViewHolder(View view) {
                super(view);
                profileImage = view.findViewById(R.id.frienditem_imageview);
                textview_name = view.findViewById(R.id.frienditem_name);
                textview_message = view.findViewById(R.id.frienditem_message);
            }
        }
    }
}
