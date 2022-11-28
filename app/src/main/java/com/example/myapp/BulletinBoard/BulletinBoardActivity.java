package com.example.myapp.BulletinBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.Activity.CommunityActivity;
import com.example.myapp.Activity.MainActivity;
import com.example.myapp.Activity.MenuActivity;
import com.example.myapp.Activity.SettingActivity;
import com.example.myapp.R;

public class BulletinBoardActivity extends AppCompatActivity {

    Button btn_tip, btn_review, btn_free;
    Button btn_setting, btn_community, btn_menu, btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);

        btn_tip = findViewById(R.id.btn_tip);
        btn_review = findViewById(R.id.btn_review);
        btn_free = findViewById(R.id.btn_free);
        btn_setting = findViewById(R.id.btn_setting);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);
        btn_home = findViewById(R.id.btn_home);

        btn_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BulletinBoardActivity.this, TipActivity.class);
                startActivity(intent);
            }
        });

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BulletinBoardActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });

        btn_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BulletinBoardActivity.this, FreeActivity.class);
                startActivity(intent);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BulletinBoardActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BulletinBoardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BulletinBoardActivity.this, CommunityActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BulletinBoardActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}