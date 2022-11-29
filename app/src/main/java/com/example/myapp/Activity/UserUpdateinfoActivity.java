package com.example.myapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapp.Fragments.ProfileFragment;
import com.example.myapp.Memberinfo;
import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserUpdateinfoActivity extends BasicActivity {

    Button btn_home,btn_setting, btn_community, btn_menu, btn_pwdReset, btn_info_update;
    EditText username, phoneNum;
    ImageView profile_image;
    TextView txt_email, txt_nickname;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_update);

        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);
        btn_info_update = findViewById(R.id.btn_info_update);
        btn_pwdReset = findViewById(R.id.btn_pwdReset);
        profile_image = findViewById(R.id.profile_image);
        txt_email = findViewById(R.id.txt_email);
        txt_nickname = findViewById(R.id.txt_nickname);
        username = findViewById(R.id.et_name);
        phoneNum = findViewById(R.id.et_callNum);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        firebaseUser.getEmail();

        //프로필 정보 가져오기
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Memberinfo memberinfo = dataSnapshot.getValue(Memberinfo.class);
                txt_nickname.setText(memberinfo.getNickname());
                txt_email.setText(firebaseUser.getEmail());
                if (memberinfo.getImageURL().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(getApplicationContext()).load(memberinfo.getImageURL()).into(profile_image);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //fragment intent
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                transaction.replace(R.id.fragment_container, profileFragment);
                transaction.commit();
            }
        });

        btn_info_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_phoneNum = phoneNum.getText().toString();
                String txt_username = username.getText().toString();
                if (!TextUtils.isEmpty(txt_username) || !TextUtils.isEmpty(txt_phoneNum)){
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            reference.child("username").setValue(txt_username);
                            reference.child("phoneNum").setValue(txt_phoneNum);

                            Toast.makeText(UserUpdateinfoActivity.this,"회원정보 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserUpdateinfoActivity.this, SettingActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(UserUpdateinfoActivity.this,"수정할 정보를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_pwdReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserUpdateinfoActivity.this, ResetPasswordActivity.class));
                finish();
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                startActivity(new Intent(UserUpdateinfoActivity.this, SettingActivity.class));
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserUpdateinfoActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserUpdateinfoActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserUpdateinfoActivity.this, CommunityActivity.class));
            }
        });

    }
}