package com.example.dnjsr.smtalk;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    EditText searchActivity_edittext_search;
    Button searchActivity_button_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("친구 추가");

        searchActivity_button_search = findViewById(R.id.searchActivity_button_search);
        searchActivity_edittext_search = findViewById(R.id.searchActivity_edittext_search);

        searchActivity_button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
