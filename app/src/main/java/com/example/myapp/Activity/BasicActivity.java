package com.example.myapp.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;


import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapp.R;

public class BasicActivity extends AppCompatActivity {
    @Override
    //어플 생명주기, 사용자가 다른 어플 사용하거나 통화를 하거나 등 다른 활동을 할때 문제 해결
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //세로 화면으로 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    public void setToolbarTitle(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setTitle(title);
    }
}
