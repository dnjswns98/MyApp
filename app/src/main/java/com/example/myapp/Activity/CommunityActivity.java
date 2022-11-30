package com.example.myapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

public class CommunityActivity extends BasicActivity{
    Button btn_home,btn_setting, btn_community, btn_menu;
    Button btn_chatting, btn_bulletinboard, btn_challenge;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_community);


        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);
        btn_chatting = findViewById(R.id.btn_chatting);
        btn_bulletinboard = findViewById(R.id.btn_bulletinboard);
        btn_challenge = findViewById(R.id.btn_go_challenge);

        btn_chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, RoomActivity.class);
                startActivity(intent);
            }
        });



        btn_bulletinboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, BulletinBoardActivity.class);
                startActivity(intent);
            }
        });

        btn_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, ThirtyChallangeActivity.class);
                startActivity(intent);
            }
        });


        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityActivity.this, SettingActivity.class));
                finish();
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityActivity.this, MainActivity.class));
            }
        });
    }
}
