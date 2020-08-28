package com.example.ros;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.RequiresApi;

public class Location extends Page {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
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

    private  void buildViews() {
        final Button bt1;
        final Button bt2;
        final Button bt3;
        final Button bt4;
        final Button bt5;
        final Button bt6;
        final Button bt7;
        final Button bt8;
        final Button btBack;
        final Button btHome;

        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        bt3 = (Button)findViewById(R.id.bt3);
        bt4 = (Button)findViewById(R.id.bt4);
        bt5 = (Button)findViewById(R.id.bt5);
        bt6 = (Button)findViewById(R.id.bt6);
        bt7 = (Button)findViewById(R.id.bt7);
        bt8 = (Button)findViewById(R.id.bt8);
        btBack = (Button)findViewById(R.id.btBack);
        btHome = (Button)findViewById(R.id.btHome);

        bt1.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(Location.this, Brand.class);

                Bundle bundle = new Bundle();
                bundle.putString("BrandType", "1");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(Location.this, Brand.class);

                Bundle bundle = new Bundle();
                bundle.putString("BrandType", "2");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        bt3.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(Location.this, Brand.class);

                Bundle bundle = new Bundle();
                bundle.putString("BrandType", "3");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        bt4.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(Location.this, Brand.class);

                Bundle bundle = new Bundle();
                bundle.putString("BrandType", "4");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        bt5.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(Location.this, Brand.class);

                Bundle bundle = new Bundle();
                bundle.putString("BrandType", "5");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        bt6.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(Location.this, Brand.class);

                Bundle bundle = new Bundle();
                bundle.putString("BrandType", "6");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        bt7.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(Location.this, Brand.class);

                Bundle bundle = new Bundle();
                bundle.putString("BrandType", "7");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        bt8.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(Location.this, Brand.class);

                Bundle bundle = new Bundle();
                bundle.putString("BrandType", "8");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        btBack.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Location.super.LastPage();
            }
        });

        btHome.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Location.super.HomePage();
            }
        });
    }
}
