package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dnjsr.smtalk.api.JoinApi;
import com.example.dnjsr.smtalk.info.UserInfo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;

public class SignupActivity extends AppCompatActivity {
    private EditText id;
    private EditText name;
    private EditText password;
    private EditText password_check;
    private Button signup;
    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        signup = findViewById(R.id.signupActivity_button_signup);
        id = findViewById(R.id.signupActivity_edittext_id);
        name = findViewById(R.id.signupActivity_edittext_name);
        password = findViewById(R.id.signupActivity_edittext_password);
        password_check = findViewById(R.id.signupActivity_edittext_passwordcheck);

        signup.setBackgroundColor(Color.parseColor("#2f2f30"));



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id.getText().toString().equals("")||name.getText().toString().equals("")||password.getText().toString().equals("")||password_check.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if(!password.getText().toString().equals(password_check.getText().toString())){
                    Toast.makeText(SignupActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
                try {
                HashMap<String,String> input = new HashMap<>();
                input.put("userId",id.getText().toString());
                input.put("userPassword",password.getText().toString());
                input.put("userName",name.getText().toString());


                    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://18.188.144.183:8888/")
                            .addConverterFactory(GsonConverterFactory.create()).build();

                    JoinApi joinApi = retrofit.create(JoinApi.class);

                    joinApi.postJoinUserInfo(input).enqueue(new Callback<JoinResult>() {
                        @Override
                        public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                            if(response.isSuccessful()){
                                JoinResult map = response.body();

                                if(map != null){
                                    switch(map.getResult()){
                                        case 0:
                                            Toast.makeText(SignupActivity.this, "존재하는 아이디 입니다. 다른 아이디를 사용해주세요.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            Toast.makeText(SignupActivity.this, "회원가입 되었습니다.", Toast.LENGTH_SHORT).show(); //아이디 비밀번호 비워둔 Login창
                                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                            finish();
                                            break;

                                            /*UserInfo userInfo = new UserInfo(id.getText().toString(),password.getText().toString(), //아이디 비밀번호 저장된 login창
                                                    name.getText().toString(),"","","");
                                            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                            intent.putExtra("userinfo",userInfo);
                                            startActivity(intent);
                                            finish();*/

                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<JoinResult> call, Throwable t) {

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



}
