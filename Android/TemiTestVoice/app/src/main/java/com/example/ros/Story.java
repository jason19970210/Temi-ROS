package com.example.ros;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import static android.view.View.getDefaultSize;

public class Story extends Page {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //執行在主執行緒
//                //啟動主頁面
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.MATCH_PARENT,
//                        RelativeLayout.LayoutParams.MATCH_PARENT);
//
//                videoView.setLayoutParams(params);
//                videoView.layout(10, 10, 10, 10);
                startActivity(new Intent(Story.this, Game.class));
                finish();
            }
        },10500);

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//        buildViews();
        videoView = findViewById(R.id.videoView1);
        String VideoPath = "android.resource://" + getPackageName() + "/" + R.raw.video_intro;
        Log.d("Tag", VideoPath);
        Uri uri= Uri.parse(VideoPath);
        videoView.setVideoURI(uri);

//        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(null);
//        mediaController.setAnchorView(videoView);

        videoView.start();
    }

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
}

