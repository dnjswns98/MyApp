package com.example.myapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.myapp.R;

import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
public class PedometerActivity extends BasicActivity implements SensorEventListener {

    TextView tv_sensor;
    TextView kcal, km;
    SensorManager sm;
    Sensor sensor_step_detector;
    ProgressBar progressBar;
    int steps = 0; //sensor
    String Kcal, Km;

    Button btn_home,btn_setting, btn_community, btn_menu;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_pedometer);
// 활동 퍼미션 체크
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        tv_sensor = (TextView) findViewById(R.id.sensor);        // 텍스트뷰 인식
        tv_sensor.setText("0"); // 걸음 수 초기화 및 출력
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);   // 센서 매니저 생성
        sensor_step_detector = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);  // 스템 감지 센서 등록

        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                startActivity(new Intent(PedometerActivity.this, SettingActivity.class));
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PedometerActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PedometerActivity.this, MainActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PedometerActivity.this, CommunityActivity.class));
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, sensor_step_detector, SensorManager.SENSOR_DELAY_NORMAL);
    };

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    };

    // 센서값이 변할때
    @Override
    public void onSensorChanged(SensorEvent event) {

        // 센서 유형이 스텝감지 센서인 경우 걸음수 +1
        switch (event.sensor.getType()) {
            case Sensor.TYPE_STEP_DETECTOR:
                tv_sensor.setText("" + (++steps));
                if (steps >= 10000) {
                    progressBar.setProgress(10000);
                }
                else progressBar.setProgress(steps);
                Kcal=Double.toString(steps*0.034);
                kcal.setText(Kcal);
                Km=Double.toString(steps*0.001);
                km.setText(Km);
                break;
        }
    };

}
