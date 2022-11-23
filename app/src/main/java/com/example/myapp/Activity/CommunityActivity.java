package com.example.myapp.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

import com.example.myapp.R;

public class CommunityActivity extends BasicActivity{
    @Override
    //어플 생명주기, 사용자가 다른 어플 사용하거나 통화를 하거나 등 다른 활동을 할때 문제 해결
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
    }
}
