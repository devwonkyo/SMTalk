package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.result.LoginResult;
import com.example.dnjsr.smtalk.update.UserLogin;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText id;
    private EditText password;
    private Button signup;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final String currentSever = ServerURL.getUrl();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }
        id = (EditText)findViewById(R.id.loginactivity_edittext_id);
        password = (EditText)findViewById(R.id.loginactivity_edittext_password);
        login = findViewById(R.id.loginactivity_button_login);
        signup = findViewById(R.id.loginactivity_button_signup);

        Bundle bundle = getIntent().getExtras();                            //회원가입 페이지에서 넘어온 값(데이터 자동저장 아닐 시 주석처리)
        if(bundle != null) {
            UserInfo userInfo = bundle.getParcelable("userinfo");
            id.setText(userInfo.get_id());
            password.setText(userInfo.getUserPassword());
        }





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                try {
                    HashMap<String, String> input = new HashMap<>();
                    input.put("userId", id.getText().toString());
                    input.put("userPassword", password.getText().toString());


                    Retrofit retrofit = new Retrofit.Builder().baseUrl(currentSever)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    RetrofitApi loginApi = retrofit.create(RetrofitApi.class);
                    loginApi.postLoginUserInfo(input).enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                            if (response.isSuccessful()) {
                                LoginResult map = response.body();
                                if (map != null) {
                                    switch (map.getResult()) {
                                        case -2:
                                            Toast.makeText(LoginActivity.this, "데이터베이스 오류로 인한 로그인 불가", Toast.LENGTH_SHORT).show();
                                            break;
                                        case -1:
                                            Toast.makeText(LoginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 0:
                                            Toast.makeText(LoginActivity.this, "가입되지 않은 유저 입니다.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            UserLogin userLogin = new UserLogin();
                                            userLogin.Login(id.getText().toString(),password.getText().toString(),new Intent(LoginActivity.this,MainActivity.class),LoginActivity.this);
                                           /* UserInfo userinfo = map.getUserInfo();
                                            CurrentUserInfo.setUserInfo(map.getUserInfo());
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("userinfo", userinfo);
                                            startActivity(intent);
                                            finish();*/
                                            break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
                //startActivity(new Intent(LoginActivity.this,MainActivity.class));


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

    }

}
