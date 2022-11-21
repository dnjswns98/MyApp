package com.example.myapp.Activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends BasicActivity {

    EditText nickname, email, phoneNum, password, username, checkPwd;
    Button btn_register, btn_login;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nickname = findViewById(R.id.et_Nickname);
        email = findViewById(R.id.et_email);
        phoneNum = findViewById(R.id.et_callNum);
        password = findViewById(R.id.et_pwd);
        username = findViewById(R.id.et_name);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.login_button);
        checkPwd = findViewById(R.id.et_checkPwd);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_nickname = nickname.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_phoneNum = phoneNum.getText().toString();
                String txt_username = username.getText().toString();
                String txt_checkPwd = checkPwd.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)
                        || TextUtils.isEmpty(txt_nickname) || TextUtils.isEmpty(txt_phoneNum)){
                    Toast.makeText(RegisterActivity.this,"빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 8 && txt_password.length() > 15) {
                    Toast.makeText(RegisterActivity.this,"비밀번호를 8자리 이상 15자리 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                // 비밀번호 재확인을 위해 필요한 코드
                else if (txt_password != txt_checkPwd){
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_username, txt_email, txt_password, txt_phoneNum, txt_username);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void register(String nickname, String email, String password, String phoneNum, String username){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("pwd", password);
                    hashMap.put("nickname", nickname);
                    hashMap.put("phoneNum", phoneNum);
                    hashMap.put("username", username);
                    hashMap.put("imageURL","default");
                    hashMap.put("status","offline");
                    hashMap.put("search", nickname.toLowerCase());

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}