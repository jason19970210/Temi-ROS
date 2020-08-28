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

public class Discount extends Page {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount);
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
        final ImageView btBrand;
        final ImageView btGame;
        final Button btBack;
        final Button btHome;

        btBrand = (ImageView)findViewById(R.id.btBrand);
        btGame = (ImageView)findViewById(R.id.btGame);
        btBack = (Button)findViewById(R.id.btBack);
        btHome = (Button)findViewById(R.id.btHome);

        ViewCompat.setElevation(btBrand, 30);
        ViewCompat.setElevation(btGame, 30);

        btBrand.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btBrand.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                Toast toast = Toast.makeText(getApplicationContext(),
                        "目前淡季尚無活動，敬請期待", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastView = (LinearLayout) toast.getView();
                ImageView imageCodeProject = new ImageView(getApplicationContext());
                imageCodeProject.setImageResource(R.drawable.eda);
                toastView.addView(imageCodeProject, 0);
                toast.show();
            }
        });

        btGame.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btGame.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                btGame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Discount.super.SwitchPage(Story.class);
                        finish();
                    }
                }, 290);

            }
        });

        btBack.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Discount.super.LastPage();
            }
        });

        btHome.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Discount.super.HomePage();
            }
        });
    }
}
