package com.example.dnjsr.smtalk;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.SelectedUserInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.result.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    EditText searchActivity_edittext_search;
    Button searchActivity_button_search;
    String searchString = "";
    SearchActivityRecyclerViewAdapter searchActivityRecyclerViewAdapter;
    RecyclerView searchActivity_recyclerview;
    ArrayList<UserInfo> userInfos = new ArrayList<>();


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) {
                searchActivityRecyclerViewAdapter.setItems(userInfos);
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//---------------------------------------------------------------------------------------------------------------------------------------  activity 종료 수신기
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
//---------------------------------------------------------------------------------------------------------------------------------------

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("친구 추가");

        searchActivity_button_search = findViewById(R.id.searchActivity_button_search);
        searchActivity_edittext_search = findViewById(R.id.searchActivity_edittext_search);
        searchActivity_recyclerview = findViewById(R.id.searchActivity_recyclerview);

        searchActivity_button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchId = searchActivity_edittext_search.getText().toString();
                if(searchId.length()<3){
                    Toast.makeText(SearchActivity.this, "3글자 이상 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    LoadingThread(searchId);
                }
            }
        });

        searchActivity_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        searchActivityRecyclerViewAdapter= new SearchActivityRecyclerViewAdapter(userInfos);
        searchActivity_recyclerview.setAdapter(searchActivityRecyclerViewAdapter);
    }

    public class SearchActivityRecyclerViewAdapter extends RecyclerView.Adapter<SearchActivityRecyclerViewAdapter.ViewHolder> {

        private ArrayList<UserInfo> items;

        public SearchActivityRecyclerViewAdapter(ArrayList<UserInfo> items)
        {
            this.items=items;
        }
        @NonNull
        @Override
        public SearchActivityRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SearchActivityRecyclerViewAdapter.ViewHolder viewHolder,final int position) {

            ((ViewHolder)viewHolder).profileImage.setImageBitmap(items.get(position).getProfileImg());
            ((ViewHolder)viewHolder).textview_name.setText(items.get(position).getUserName());
            ((ViewHolder)viewHolder).textview_message.setText(items.get(position).getComment());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectedUserInfo.setUserInfo(items.get(position));
                    Intent intent = new Intent(v.getContext(),ProfileActivity.class);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(ArrayList<UserInfo> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView profileImage;
            public TextView textview_name;
            public TextView textview_message;
            public ViewHolder(View view) {
                super(view);
                profileImage = view.findViewById(R.id.frienditem_imageview);
                textview_name = view.findViewById(R.id.frienditem_name);
                textview_message = view.findViewById(R.id.frienditem_message);
            }
        }
    }

    public void LoadingThread(final String searchId) {
        userInfos.clear();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerURL.getUrl())
                            .addConverterFactory(GsonConverterFactory.create()).build();

                    RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
                    retrofitApi.postIdForSearch(searchId).enqueue(new Callback<SearchResult>() {
                        @Override
                        public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                            if (response.isSuccessful()) {
                                final SearchResult map = response.body();
                                if (map != null) {
                                    switch (map.getResult()) {
                                        case 0:
                                            Toast.makeText(SearchActivity.this, "로드 실패.", Toast.LENGTH_LONG).show();
                                            break;
                                        case 1:
                                            Thread thread_inner = new Thread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    for (UserInfo userInfo : map.getUsers()) {
                                                        URL url = null;
                                                        try {
                                                            url = new URL(ServerURL.getUrl() + userInfo.getProfileImgUrl());

                                                        } catch (MalformedURLException e) {
                                                            e.printStackTrace();
                                                        }


                                                        InputStream inputStream = null;
                                                        try {
                                                            inputStream = url.openStream();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        final Bitmap bm = BitmapFactory.decodeStream(inputStream);
                                                        userInfo.setProfileImg(bm);
                                                        if(!userInfo.get_id().equals(CurrentUserInfo.getUserInfo().get_id()))
                                                            userInfos.add(userInfo);
                                                    }

                                                    handler.sendEmptyMessage(0);

                                                }
                                            });
                                            thread_inner.start();
                                            break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SearchResult> call, Throwable t) {

                        }

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }
}
