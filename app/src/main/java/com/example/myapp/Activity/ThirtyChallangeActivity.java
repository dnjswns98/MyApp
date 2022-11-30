package com.example.myapp.Activity;

import static com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.utils.TimeUtilities.formatTime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapp.R;
import com.example.myapp.PostWriteinfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ThirtyChallangeActivity extends BasicActivity{

    Button btn_home,btn_setting, btn_community, btn_menu, btn_my_challenge, btn_challenge_flour, btn_challenge_manbo,
            btn_challenge_yasik;
    //    TextView yasik_tvTime;
    TextView yasik_tvTime, flour_tvTime, manbo_tvTime, invisible_flour_text, invisible_yasik_text, invisible_manbo_text;


    private int btn_cnt_flour = 0;
    private int btn_cnt_yasik = 0;
    private int btn_cnt_manbo = 0;


//    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirty_challange);


        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);

        yasik_tvTime = (TextView) findViewById(R.id.yasik_tvTime);
        flour_tvTime = (TextView) findViewById(R.id.flour_tvTime);
        manbo_tvTime = (TextView) findViewById(R.id.manbo_tvTime);
        invisible_flour_text = (TextView) findViewById(R.id.invisible_flour_text);
        invisible_yasik_text = (TextView) findViewById(R.id.invisible_yasik_text);
        invisible_manbo_text = (TextView) findViewById(R.id.invisible_manbo_text);

        invisible_flour_text.setVisibility(View.INVISIBLE);
        invisible_yasik_text.setVisibility(View.INVISIBLE);
        invisible_manbo_text.setVisibility(View.INVISIBLE);

        btn_my_challenge = (Button) findViewById(R.id.btn_my_challenge);
        btn_challenge_flour = (Button) findViewById(R.id.btn_challenge_flour);
        btn_challenge_manbo = (Button) findViewById(R.id.btn_challenge_manbo);
        btn_challenge_yasik = (Button) findViewById(R.id.btn_challenge_yasik);





        btn_challenge_flour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String Hour = String.format("%02d", SystemClock.elapsedRealtime()/1000/3600);
//                String Min = String.format("%02d",(SystemClock.elapsedRealtime()/1000/60)%60);
//                String Sec = String.format("%02d",(SystemClock.elapsedRealtime()/1000)%60);
//                flour_tvTime.setText("" + SystemClock.elapsedRealtime());

                btn_cnt_flour++;
                if (btn_cnt_flour % 2 == 0) {
                    invisible_flour_text.setVisibility(View.INVISIBLE);
                } else if (btn_cnt_flour % 2 == 1) {
                    invisible_flour_text.setVisibility(View.VISIBLE);
                }
//                Btn_timestamp btn_timestamp = new Btn_timestamp();
//                btn_timestamp.timestamp = ServerValue.TIMESTAMP;
                Toast.makeText(ThirtyChallangeActivity.this, "30일 동안 밀가루 참기 챌린지를 시작합니다!", Toast.LENGTH_SHORT).show();


                String Day = String.format("%01d", SystemClock.elapsedRealtime()/1000/3600/24);
                flour_tvTime.setText(Day+"일");
            }
        });


        btn_challenge_manbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String Day = String.format("%01d", SystemClock.elapsedRealtime()/1000/3600/24);
//                manbo_tvTime.setText(Day+"일");

                btn_cnt_manbo++;
                if (btn_cnt_manbo % 2 == 0) {
                    invisible_manbo_text.setVisibility(View.INVISIBLE);
                } else if (btn_cnt_manbo % 2 == 1) {
                    invisible_manbo_text.setVisibility(View.VISIBLE);
                }

                Toast.makeText(ThirtyChallangeActivity.this, "30일 동안 만보 걷기 챌린지를 시작합니다!", Toast.LENGTH_SHORT).show();

                String Day = String.format("%01d", SystemClock.elapsedRealtime()/1000/3600/24);
                manbo_tvTime.setText(Day+"일");
            }


        });

        btn_challenge_yasik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Day = String.format("%01d", SystemClock.elapsedRealtime()/1000/3600/24);
                yasik_tvTime.setText(Day+"일");
                btn_cnt_yasik++;
                if (btn_cnt_yasik % 2 == 0) {
                    invisible_yasik_text.setVisibility(View.INVISIBLE);
                } else if (btn_cnt_yasik % 2 == 1) {
                    invisible_yasik_text.setVisibility(View.VISIBLE);
                }

                Toast.makeText(ThirtyChallangeActivity.this, "30일 동안 야식 참기 챌린지를 시작합니다!", Toast.LENGTH_SHORT).show();

            }

        });

        btn_my_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirtyChallangeActivity.this, SettingActivity.class));
                finish();
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirtyChallangeActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirtyChallangeActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirtyChallangeActivity.this, CommunityActivity.class));
            }
        });


    }


}