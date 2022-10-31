package com.example.myapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;

public class SplashscreenActivity extends AppCompatActivity {

    TextView txtSplash;
    RelativeLayout relativeLayout;
    Animation txtAnimation,layoutAnimation;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_splash_screen);

        txtAnimation = AnimationUtils.loadAnimation(SplashscreenActivity.this, R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(SplashscreenActivity.this, R.anim.bottom_to_top);

        txtSplash = findViewById(R.id.txtSplash);
        relativeLayout = findViewById(R.id.relMain);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setAnimation(layoutAnimation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtSplash.setVisibility(View.VISIBLE);

                        txtSplash.setAnimation(txtAnimation);
                    }
                }, 900);

            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashscreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        },6000);
    }
}
