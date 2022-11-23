package com.example.myapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

public class UserLeaveActivity extends BasicActivity {

    Button btn_user_leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_leave);

        btn_user_leave = findViewById(R.id.btn_user_leave);

        btn_user_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLeaveActivity.this, UserLeaveCheckActivity.class);
                startActivity(intent);
            }
        });

    }
}