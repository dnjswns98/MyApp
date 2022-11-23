package com.example.myapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class UserLeaveActivity extends BasicActivity {
    Button btn_home,btn_setting, btn_community, btn_menu;
    Button btn_user_leave;
    EditText et_email, et_password;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_leave);

        btn_user_leave = findViewById(R.id.btn_user_leave);
        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_pwd);


        user = auth.getInstance().getCurrentUser();


        btn_user_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null) {
                    String txt_email = et_email.getText().toString();
                    String txt_password = et_password.getText().toString();
                    if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                        Toast.makeText(UserLeaveActivity.this, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if(txt_email.equals(email) || txt_password.equals(password)) {
                        Intent intent = new Intent(UserLeaveActivity.this, UserLeaveCheckActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(UserLeaveActivity.this, "Email 혹은 Password가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                startActivity(new Intent(UserLeaveActivity.this, SettingActivity.class));
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLeaveActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLeaveActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLeaveActivity.this, CommunityActivity.class));
            }
        });

    }
}