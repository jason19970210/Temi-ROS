package com.example.ros;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class InfoAct extends Page {

//    private WebView webView1;
//    private Button btCancel2;
    private TextView content;
    private ImageView INDEX_PIC_PATH;
    private ImageView btCancel3;
    private ImageView imageBackground3;
    private ScrollView ScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoact);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

//        Bundle bundle1 = this.getIntent().getExtras();
//        final String NewsID = bundle1.getString("NewsID");
//        Log.e("NewsID", NewsID);

        Bundle bundle2 = this.getIntent().getExtras();
        final String Content = bundle2.getString("Content");
        Log.e("Content", Content);

        Bundle bundle3 = this.getIntent().getExtras();
        final String iNDEX_PIC_PATH = bundle3.getString("INDEX_PIC_PATH");
        Log.e("INDEX_PIC_PATH", iNDEX_PIC_PATH);

//        String url = "http://www.edamall.com.tw/NewsShowPage.aspx?NewsId="+NewsID;

        content = (TextView)findViewById(R.id.content);
        INDEX_PIC_PATH = (ImageView) findViewById(R.id.iNDEX_PIC_PATH);
        btCancel3 = (ImageView)findViewById(R.id.btCancel3);
        imageBackground3 = (ImageView)findViewById(R.id.imageBackground3);
        ScrollView = (ScrollView)findViewById(R.id.ScrollView);

        ViewCompat.setElevation(imageBackground3, 12);
        ViewCompat.setElevation(btCancel3, 15);
        ViewCompat.setElevation(ScrollView, 15);

        content.setText(Html.fromHtml(Content));
        content.setTextSize(25);
        setLOGO(INDEX_PIC_PATH, iNDEX_PIC_PATH);

        btCancel3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                finish();
            }
        });
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

    private void setLOGO(final ImageView imageView, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(InfoAct.this)
                        .asBitmap()
                        .load(value)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                imageView.setImageBitmap(resource);
                            }
                        });
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }
}