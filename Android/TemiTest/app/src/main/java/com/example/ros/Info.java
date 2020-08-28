package com.example.ros;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Info extends Page{

    private TextView txtName;
    private TextView txtLocation;
    private TextView txtOpenTime;
    private ImageView imageQRcode;
    private ImageView imageLogo;
    private ImageView imageBackground;
    private ImageView imageBig;
    private ImageView btCancel;
    private LinearLayout lay;
    private ImageView btGoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        Bundle bundle1 = this.getIntent().getExtras();
        final String Logo = bundle1.getString("Logo");
        Log.e("Logo", Logo);

        Bundle bundle2 = this.getIntent().getExtras();
        final String Name = bundle2.getString("Name");
        Log.e("Name", Name);

        Bundle bundle3 = this.getIntent().getExtras();
        final String Zone = bundle3.getString("Zone");
        Log.e("Zone", Zone);

        Bundle bundle4 = this.getIntent().getExtras();
        final String Floor = bundle4.getString("Floor");
        Log.e("Floor", Floor);

//        Bundle bundle5 = this.getIntent().getExtras();
//        final String Content = bundle5.getString("Content");
//        Log.e("Content", Content);

        Bundle bundle6 = this.getIntent().getExtras();
        final String OpenTime = bundle6.getString("OpenTime");
        Log.e("OpenTime", OpenTime);

        Bundle bundle7 = this.getIntent().getExtras();
        final String StoreID = bundle7.getString("StoreID");
        Log.e("StoreID", StoreID);

        Bundle bundle8 = this.getIntent().getExtras();
        final String Big = bundle8.getString("Big");
        Log.e("Big", Big);

        txtName = (TextView)findViewById(R.id.txtName);
        lay = (LinearLayout) findViewById(R.id.lay);
        txtLocation = (TextView)findViewById(R.id.txtLocation);
        txtOpenTime = (TextView)findViewById(R.id.txtOpenTime);
//        txtContent = (TextView)findViewById(R.id.txtContent);
//        txtContent.setMovementMethod(ScrollingMovementMethod.getInstance()); //實現滾動
        imageQRcode = (ImageView)findViewById(R.id.imageQRcode);
        imageLogo = (ImageView)findViewById(R.id.imageLogo);
        imageBackground = (ImageView)findViewById(R.id.imageBackground);
        imageBig = (ImageView)findViewById(R.id.imageBig);
        btCancel = (ImageView)findViewById(R.id.btCancel);
        btGoto = (ImageView)findViewById(R.id.btGoto);

        ViewCompat.setElevation(imageBackground, 12);
        ViewCompat.setElevation(lay, 15);
//        ViewCompat.setElevation(txtName, 15);
//        ViewCompat.setElevation(txtLocation, 15);
        ViewCompat.setElevation(txtOpenTime, 15);
//        ViewCompat.setElevation(txtContent, 15);
        ViewCompat.setElevation(imageQRcode, 15);
        ViewCompat.setElevation(imageLogo, 15);
        ViewCompat.setElevation(imageBig, 15);
        ViewCompat.setElevation(btCancel, 15);
        ViewCompat.setElevation(btGoto, 15);

        if(Zone.equals("A") && Floor.equals("4F")){
            btGoto.setVisibility(View.VISIBLE);
        }else {
            btGoto.setVisibility(View.GONE);
        }

        txtName.setText("專櫃名稱：" + Name);
        txtOpenTime.setText("營業時間：" + OpenTime);
        txtLocation.setText("　　"+"專櫃位置：" + Zone+ "區" + Floor);
//        txtContent.setText(Content);

        setLOGO(imageLogo, Logo);
        setLOGO(imageBig, Big);
        getCode(imageQRcode, StoreID);

        btCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        btGoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                btGoto.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                btGoto.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        
                    }
                }, 290);
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
                Glide.with(Info.this)
                        .load(value)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(25)))
                        .into(imageView);
                //imageView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getCode(final ImageView imageView,final String ID){
//        if (url.equals("")){
//            imageQRcode.setVisibility(View.GONE);
//            txtContent.getLayoutParams().width = (int) getResources().getDimension(R.dimen.txtContent_width);
//        }else {

            String url = "http://www.edamall.com.tw/BrandGuideImage_byFloor.aspx?BrandId="+ID;
            BarcodeEncoder encoder = new BarcodeEncoder();
            try{
                Bitmap bit = encoder.encodeBitmap(url , BarcodeFormat.QR_CODE,200,200); // change Image Size here
                imageView.setImageBitmap(bit);
            }catch (WriterException e){
                e.printStackTrace();
            }
//        }
    }
}
