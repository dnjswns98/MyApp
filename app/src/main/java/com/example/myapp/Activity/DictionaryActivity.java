package com.example.myapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

public class DictionaryActivity extends BasicActivity {
    Button btn_go_food_dictionary,btn_go_exer_dictionary;
    Button btn_home,btn_setting, btn_community, btn_menu;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_dictionary);

        btn_go_food_dictionary = findViewById(R.id.btn_go_food_dictionary);
        btn_go_exer_dictionary = findViewById(R.id.btn_go_exer_dictionary);

        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);

        btn_go_food_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DictionaryActivity.this, FoodDictionaryActivity.class));
            }
        });
        btn_go_exer_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DictionaryActivity.this, ExerDictionaryActivity.class));
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                startActivity(new Intent(DictionaryActivity.this, SettingActivity.class));
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DictionaryActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DictionaryActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DictionaryActivity.this, CommunityActivity.class));
            }
        });
    }
}
