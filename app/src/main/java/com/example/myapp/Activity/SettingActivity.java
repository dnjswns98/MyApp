package com.example.myapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends BasicActivity {

    Button btn_go_info_update, btn_go_user_leave, btn_logout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        btn_go_info_update = findViewById(R.id.btn_go_info_update);
        btn_logout = findViewById(R.id.btn_logout);
        btn_go_user_leave = findViewById(R.id.btn_go_user_leave);

        auth = FirebaseAuth.getInstance();

        btn_go_info_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, UserUpdateinfoActivity.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            }
        });

        btn_go_user_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, UserLeaveActivity.class));
            }
        });
    }
}




