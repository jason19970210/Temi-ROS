package com.example.ros;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class Dm extends Page {

//    private ArrayList<Integer> images2 = new ArrayList<Integer>();

    int[] images = new int[] { R.drawable.page_1,R.drawable.page_2,R.drawable.page_3,R.drawable.page_4,R.drawable.page_5,
                                R.drawable.page_6,R.drawable.page_7,R.drawable.page_8,R.drawable.page_9,R.drawable.page_10,
                                R.drawable.page_11,R.drawable.page_12,R.drawable.page_13,R.drawable.page_14,R.drawable.page_15,
                                R.drawable.page_16,R.drawable.page_17,R.drawable.page_18,R.drawable.page_19,R.drawable.page_20,
                                R.drawable.page_21,R.drawable.page_22,R.drawable.page_23,R.drawable.page_24,R.drawable.page_25,
                                R.drawable.page_26,R.drawable.page_27,R.drawable.page_28,R.drawable.page_29,R.drawable.page_30,
                                R.drawable.page_31,R.drawable.page_32};

    private ImageView imageDM;
    private ImageView imageQR;
    private TextView txtNum;
    private TextView txtthis;
    private Button btR;
    private Button btL;
    private Button btBack;
    private int i=0;
    private int thisNum=1;
    private int Length = images.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dm);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        final String DMurl = "http://www.edamall.com.tw/DM_MALL/DM_Mobile/DM_1/index.html";

        imageDM = (ImageView)findViewById(R.id.imageDM);
        imageQR = (ImageView)findViewById(R.id.imageQR);
        txtNum = (TextView)findViewById(R.id.txtNum);
        txtthis = (TextView)findViewById(R.id.txtthis);
        btR = (Button)findViewById(R.id.btR);
        btL = (Button)findViewById(R.id.btL);
        btBack = (Button)findViewById(R.id.btBack);

        ViewCompat.setElevation(imageDM, 20);

        txtNum.setText(numToStr(Length));
        txtthis.setText(numToStr(thisNum));
        setLOGO(imageDM, images[0]);
        getCode(imageQR, DMurl);

        btR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageDM.setVisibility(View.INVISIBLE);
                if (i + 1 == Length) {
                    i = 0;
                    thisNum = 1;
                } else {
                    i++;
                    thisNum++;
                }
                setLOGO(imageDM, images[i]);
                txtthis.setText(numToStr(thisNum));
            }
        });

        btL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageDM.setVisibility(View.INVISIBLE);
                if (i == 0) {
                    i = Length-1;
                    thisNum = Length;
                } else {
                    i--;
                    thisNum--;
                }
                setLOGO(imageDM, images[i]);
                txtthis.setText(numToStr(thisNum));
            }
        });

        btBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Dm.super.LastPage();
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

    private void getCode(final ImageView imageView,final String ID){
        String url = ID;
        BarcodeEncoder encoder = new BarcodeEncoder();
        try{
            Bitmap bit = encoder.encodeBitmap(url , BarcodeFormat.QR_CODE,200,200); // change Image Size here
            imageView.setImageBitmap(bit);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }

    private void setLOGO(final ImageView imageView, final int value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(Dm.this).load(value).into(imageView);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

    private String numToStr(final int i){
        String s = new String(""+i);
        return s;
    }
}

