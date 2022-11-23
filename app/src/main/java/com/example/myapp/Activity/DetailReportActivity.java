package com.example.myapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

public class DetailReportActivity extends BasicActivity{

    Button btn_home,btn_setting, btn_community, btn_menu;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_detail_report);

        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                startActivity(new Intent(DetailReportActivity.this, SettingActivity.class));
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailReportActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailReportActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailReportActivity.this, CommunityActivity.class));
            }
        });
    }
}
