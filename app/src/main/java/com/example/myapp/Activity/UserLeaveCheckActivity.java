package com.example.myapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserLeaveCheckActivity extends BasicActivity {

    Button btn_ok, btn_no;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_leave_check);

        btn_ok = findViewById(R.id.btn_yes);
        btn_no = findViewById(R.id.btn_no);

        auth = FirebaseAuth.getInstance();
        btn_ok.setOnClickListener(new View.OnClickListener() {
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
    }
}
