package com.example.myapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

public class UserInfoActivity extends BasicActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Button btn_go_info_update = findViewById(R.id.btn_go_info_update);
        btn_go_info_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserUpdateinfoActivity.class);
                startActivity(intent);
            }
        });

        Button btn_go_user_leave = findViewById(R.id.btn_go_user_leave);
        btn_go_user_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserLeaveActivity.class);
                startActivity(intent);
            }
        });

    }
}
