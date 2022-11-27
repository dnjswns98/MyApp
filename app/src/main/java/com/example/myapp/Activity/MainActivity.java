package com.example.myapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.myapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;



public class MainActivity extends BasicActivity {

//    캘린더용변수
    public String fname=null;
    public String str=null;
    public CalendarView calendarView;
    public Button chaFood_Btn,delFood_Btn,saveFood_Btn;
    public TextView diaryTextView,textView2,textF,textview5,todayEx,textEx;
    public EditText contextEditText;

    Button btn_setting, btn_community, btn_menu;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        btn_setting = findViewById(R.id.btn_setting);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);
        auth = FirebaseAuth.getInstance();
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CommunityActivity.class));
            }
        });
//캘린더
        calendarView=findViewById(R.id.calendarView);
        diaryTextView=findViewById(R.id.diaryTextView);
        saveFood_Btn=findViewById(R.id.saveFood_Btn);
        delFood_Btn=findViewById(R.id.delFood_Btn);
        chaFood_Btn=findViewById(R.id.chaFood_Btn);
        textView2=findViewById(R.id.textView2);
        textview5=findViewById(R.id.textview5);     //
        todayEx=findViewById(R.id.todayEx);
        textEx= findViewById(R.id.textEx);
        textF=findViewById(R.id.textF);
        contextEditText=findViewById(R.id.contextEditText);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                diaryTextView.setVisibility(View.VISIBLE);
                saveFood_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textF.setVisibility(View.VISIBLE);
                textview5.setVisibility(View.VISIBLE);
                todayEx.setVisibility(View.VISIBLE);
                textEx.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                chaFood_Btn.setVisibility(View.INVISIBLE);
                delFood_Btn.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d",year,month+1,dayOfMonth));
                contextEditText.setText("");
                checkDay(year,month,dayOfMonth);
            }
        });
        saveFood_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str=contextEditText.getText().toString();
                textView2.setText(str);
                saveFood_Btn.setVisibility(View.INVISIBLE);
                chaFood_Btn.setVisibility(View.VISIBLE);
                delFood_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);

            }
        });
    }

    public void  checkDay(int cYear,int cMonth,int cDay){
        fname=""+cYear+"-"+(cMonth+1)+""+"-"+cDay+".txt";//저장할 파일 이름설정
        FileInputStream fis=null;//FileStream fis 변수

        try{
            fis=openFileInput(fname);

            byte[] fileData=new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str=new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);

            saveFood_Btn.setVisibility(View.INVISIBLE);
            chaFood_Btn.setVisibility(View.VISIBLE);
            delFood_Btn.setVisibility(View.VISIBLE);

            chaFood_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);

                    saveFood_Btn.setVisibility(View.VISIBLE);
                    chaFood_Btn.setVisibility(View.INVISIBLE);
                    delFood_Btn.setVisibility(View.INVISIBLE);
                    textView2.setText(contextEditText.getText());
                }

            });
            delFood_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    saveFood_Btn.setVisibility(View.VISIBLE);
                    chaFood_Btn.setVisibility(View.INVISIBLE);
                    delFood_Btn.setVisibility(View.INVISIBLE);
                    removeDiary(fname);
                }
            });
            if(textView2.getText()==null){
                textView2.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                saveFood_Btn.setVisibility(View.VISIBLE);
                chaFood_Btn.setVisibility(View.INVISIBLE);
                delFood_Btn.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content="";
            fos.write((content).getBytes());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content=contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        FirebaseUser user = auth.getCurrentUser();
        if(user == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }



    }



}