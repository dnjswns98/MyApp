package com.example.myapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserLeaveCheckActivity extends BasicActivity {
    Button btn_home,btn_setting, btn_community, btn_menu;
    Button btn_yes,btn_no;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_leave_check);

        btn_yes = findViewById(R.id.btn_yes);
        btn_no = findViewById(R.id.btn_no);
        btn_home = findViewById(R.id.btn_home);
        btn_setting = findViewById(R.id.btn_setting);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);

        auth = FirebaseAuth.getInstance();
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().delete();

                Intent intent = new Intent(UserLeaveCheckActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLeaveCheckActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLeaveCheckActivity.this, SettingActivity.class));
                finish();
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLeaveCheckActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLeaveCheckActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLeaveCheckActivity.this, CommunityActivity.class));
            }
        });

    }
}
