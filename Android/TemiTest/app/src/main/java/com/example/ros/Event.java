package com.example.ros;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.view.ViewCompat;

public class Event extends Page {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        buildViews();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
    }
    @Override
    public void onStop()
    {
        super.onStop();
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private  void buildViews(){
        final ImageView btSpecial;
        final ImageView btDM;
        final Button btBack;
        final Button btHome;

        btSpecial = (ImageView)findViewById(R.id.bt1);
        btDM = (ImageView)findViewById(R.id.bt2);
        btBack = (Button)findViewById(R.id.btBack);
        btHome = (Button)findViewById(R.id.btHome);

        ViewCompat.setElevation(btSpecial, 30);
        ViewCompat.setElevation(btDM, 30);

        btSpecial.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                btSpecial.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                btSpecial.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Event.super.SwitchPage(Information.class);
                    }
                }, 290);
            }
        });

        btDM.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                btDM.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                btDM.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Event.super.SwitchPage(Dm.class);
                    }
                }, 290);
            }
        });

        btBack.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Event.super.LastPage();
            }
        });

        btHome.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Event.super.HomePage();
            }
        });
    }
}
