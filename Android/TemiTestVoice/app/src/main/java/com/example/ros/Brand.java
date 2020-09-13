package com.example.ros;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Brand extends Page {

    private int total;
    private int allnum;
    private int thisnum;
    private int a = 0;
    private int b = 1;
    private int c = 2;
    private int d = 3;
    private ArrayList<String> storeID = new ArrayList<String>();
    private ArrayList<String> brandID = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> zone = new ArrayList<String>();
    private ArrayList<String> floor = new ArrayList<String>();
    private ArrayList<String> link = new ArrayList<String>();
    private ArrayList<String> content = new ArrayList<String>();
    private ArrayList<String> openTime = new ArrayList<String>();
    private ArrayList<String> logo = new ArrayList<String>();
    private ArrayList<String> big = new ArrayList<String>();

    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txtAll;
    private TextView txtThis;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private GifImageView image1;
    private GifImageView image2;
    private GifImageView image3;
    private GifImageView image4;
    private Button btL;
    private Button btR;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.brand);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        Bundle bundle = this.getIntent().getExtras();
        final String BrandType = bundle.getString("BrandType");
        Log.e("BrandType", BrandType);

        image1 = (GifImageView) findViewById(R.id.image1);
        image2 = (GifImageView) findViewById(R.id.image2);
        image3 = (GifImageView) findViewById(R.id.image3);
        image4 = (GifImageView) findViewById(R.id.image4);
        title = (TextView)findViewById(R.id.main);
        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt3 = (TextView)findViewById(R.id.txt3);
        txt4 = (TextView)findViewById(R.id.txt4);
        txtAll = (TextView)findViewById(R.id.txtAll);
        txtThis = (TextView)findViewById(R.id.txtThis);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        btL = (Button)findViewById(R.id.btL);
        btR = (Button)findViewById(R.id.btR);
        final Button btBack = (Button)findViewById(R.id.btBack);
        final Button btHome = (Button)findViewById(R.id.btHome);

        btL.setClickable(false);
        btL.setAlpha((float) 0.3);

        thisnum = 1;
        txtThis.setText(numToStr(thisnum));


        //set View shadow
        ViewCompat.setElevation(imageView1, 16);
        ViewCompat.setElevation(imageView2, 16);
        ViewCompat.setElevation(imageView3, 16);
        ViewCompat.setElevation(imageView4, 16);

        if(BrandType.equals("1")){
            title.setText(R.string.t1);
        }else if (BrandType.equals("2")){
            title.setText(R.string.t2);
        }else if (BrandType.equals("3")){
            title.setText(R.string.t3);
        }else if (BrandType.equals("4")){
            title.setText(R.string.t4);
        }else if (BrandType.equals("5")){
            title.setText(R.string.t5);
        }else if (BrandType.equals("6")){
            title.setText(R.string.t6);
        }else if (BrandType.equals("7")){
            title.setText(R.string.t7);
        }else if (BrandType.equals("8")){
            title.setText(R.string.t8);
        }

        txt1.setText("Loading...."); txt2.setText("Loading...."); txt3.setText("Loading...."); txt4.setText("Loading....");

        try {
            // 如果載入的是gif動圖，第一步需要先將gif動圖資源轉化為GifDrawable
            // 將gif圖資源轉化為GifDrawable
            GifDrawable gifDrawable1 = new GifDrawable(getResources(), R.drawable.loading);
            GifDrawable gifDrawable2 = new GifDrawable(getResources(), R.drawable.loading);
            GifDrawable gifDrawable3 = new GifDrawable(getResources(), R.drawable.loading);
            GifDrawable gifDrawable4 = new GifDrawable(getResources(), R.drawable.loading);
            // gif1載入一個動態圖gif
            image1.setImageDrawable(gifDrawable1);
            image2.setImageDrawable(gifDrawable2);
            image3.setImageDrawable(gifDrawable3);
            image4.setImageDrawable(gifDrawable4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btL.setOnClickListener(new OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v){
                setAnimation();

                if (thisnum==1){
                    txtThis.setText(numToStr(thisnum));
                }else {
                    thisnum--;
                    txtThis.setText(numToStr(thisnum));
                }

                a-=4; b-=4; c-=4; d-=4;

                if(a==-4){
                    a+=4; b+=4; c+=4; d+=4;
                }else if(a==0){
                    btL.setClickable(false);
                    btL.setAlpha((float) 0.3);
                }else{
                    btL.setClickable(true);
                    btL.setAlpha((float) 1);
                }

                if(d>total-1){
                    int num = d+1 - total;
                    if(num == 1){
                        txt1.setText(Name.get(a));
                        txt2.setText(Name.get(b));
                        txt3.setText(Name.get(c));
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        txt3.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, logo.get(a));
                        setLOGO(imageView2, logo.get(b));
                        setLOGO(imageView3, logo.get(c));

                        imageView4.setBackgroundResource(R.mipmap.empty);
                        imageView4.setVisibility(View.GONE);
                        txt4.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image4);
                    }else if(num == 2){
                        txt1.setText(Name.get(a));
                        txt2.setText(Name.get(b));
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, logo.get(a));
                        setLOGO(imageView2, logo.get(b));

                        imageView3.setBackgroundResource(R.mipmap.empty);
                        imageView4.setBackgroundResource(R.mipmap.empty);
                        imageView3.setVisibility(View.GONE);
                        imageView4.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                        txt4.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image3);
                        setVis(image4);
                    }else if(num == 3){
                        txt1.setText(Name.get(a));
                        txt1.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, logo.get(a));

                        imageView2.setBackgroundResource(R.mipmap.empty);
                        imageView3.setBackgroundResource(R.mipmap.empty);
                        imageView4.setBackgroundResource(R.mipmap.empty);
                        imageView2.setVisibility(View.GONE);
                        imageView3.setVisibility(View.GONE);
                        imageView4.setVisibility(View.GONE);
                        txt2.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                        txt4.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image2);
                        setVis(image3);
                        setVis(image4);
                    }
                }else if(d == total-1){
                    txt1.setText(Name.get(a));
                    txt2.setText(Name.get(b));
                    txt3.setText(Name.get(c));
                    txt4.setText(Name.get(d));
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);
                    txt4.setVisibility(View.VISIBLE);

                    setLOGO(imageView1, logo.get(a));
                    setLOGO(imageView2, logo.get(b));
                    setLOGO(imageView3, logo.get(c));
                    setLOGO(imageView4, logo.get(d));
                    imageView1.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView2.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView3.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView4.setBackgroundResource(R.drawable.bd_circle_shape);
                    btR.setClickable(false);
                    btR.setAlpha((float) 0.3);

                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.VISIBLE);
                    image4.setVisibility(View.VISIBLE);
                }else{
                    txt1.setText(Name.get(a));
                    txt2.setText(Name.get(b));
                    txt3.setText(Name.get(c));
                    txt4.setText(Name.get(d));
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);
                    txt4.setVisibility(View.VISIBLE);

                    setLOGO(imageView1, logo.get(a));
                    setLOGO(imageView2, logo.get(b));
                    setLOGO(imageView3, logo.get(c));
                    setLOGO(imageView4, logo.get(d));
                    imageView1.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView2.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView3.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView4.setBackgroundResource(R.drawable.bd_circle_shape);
                    btR.setClickable(true);
                    btR.setAlpha((float) 1);

                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.VISIBLE);
                    image4.setVisibility(View.VISIBLE);
                }
            }
        });

        btR.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                setAnimation();

                if (thisnum==allnum){
                    txtThis.setText(numToStr(thisnum));
                }else {
                    thisnum++;
                    txtThis.setText(numToStr(thisnum));
                }

                a+=4; b+=4; c+=4; d+=4;

                if(a==0){
                    btL.setClickable(false);
                    btL.setAlpha((float) 0.3);
                }else{
                    btL.setClickable(true);
                    btL.setAlpha((float) 1);
                }

                if (Name.size()==4){
                    a-=4; b-=4; c-=4; d-=4;
                }else if(d>total-1){
                    int num = d+1 - total;
                    if(num == 1){
                        txt1.setText(Name.get(a));
                        txt2.setText(Name.get(b));
                        txt3.setText(Name.get(c));
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        txt3.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, logo.get(a));
                        setLOGO(imageView2, logo.get(b));
                        setLOGO(imageView3, logo.get(c));

                        imageView4.setBackgroundResource(R.mipmap.empty);
                        imageView4.setVisibility(View.GONE);
                        txt4.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image4);
                    }else if(num == 2){
                        txt1.setText(Name.get(a));
                        txt2.setText(Name.get(b));
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, logo.get(a));
                        setLOGO(imageView2, logo.get(b));

                        imageView3.setBackgroundResource(R.mipmap.empty);
                        imageView4.setBackgroundResource(R.mipmap.empty);
                        imageView3.setVisibility(View.GONE);
                        imageView4.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                        txt4.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image3);
                        setVis(image4);
                    }else if(num == 3){
                        txt1.setText(Name.get(a));
                        txt1.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, logo.get(a));

                        imageView2.setBackgroundResource(R.mipmap.empty);
                        imageView3.setBackgroundResource(R.mipmap.empty);
                        imageView4.setBackgroundResource(R.mipmap.empty);
                        imageView2.setVisibility(View.GONE);
                        imageView3.setVisibility(View.GONE);
                        imageView4.setVisibility(View.GONE);
                        txt2.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                        txt4.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image2);
                        setVis(image3);
                        setVis(image4);
                    }
                }else if(d == total-1){
                    txt1.setText(Name.get(a));
                    txt2.setText(Name.get(b));
                    txt3.setText(Name.get(c));
                    txt4.setText(Name.get(d));
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);
                    txt4.setVisibility(View.VISIBLE);

                    setLOGO(imageView1, logo.get(a));
                    setLOGO(imageView2, logo.get(b));
                    setLOGO(imageView3, logo.get(c));
                    setLOGO(imageView4, logo.get(d));
                    imageView1.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView2.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView3.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView4.setBackgroundResource(R.drawable.bd_circle_shape);
                    btR.setClickable(false);
                    btR.setAlpha((float) 0.3);

                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.VISIBLE);
                    image4.setVisibility(View.VISIBLE);
                }else{
                    txt1.setText(Name.get(a));
                    txt2.setText(Name.get(b));
                    txt3.setText(Name.get(c));
                    txt4.setText(Name.get(d));
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);
                    txt4.setVisibility(View.VISIBLE);

                    setLOGO(imageView1, logo.get(a));
                    setLOGO(imageView2, logo.get(b));
                    setLOGO(imageView3, logo.get(c));
                    setLOGO(imageView4, logo.get(d));
                    imageView1.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView2.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView3.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView4.setBackgroundResource(R.drawable.bd_circle_shape);
                    btR.setClickable(true);
                    btR.setAlpha((float) 1);

                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.VISIBLE);
                    image4.setVisibility(View.VISIBLE);
                }
            }
        });

        btBack.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Brand.super.LastPage();
            }
        });

        btHome.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Brand.super.HomePage();
            }
        });

        imageView1.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                imageView1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                imageView1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(Brand.this, Info.class);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Logo", logo.get(a));
                        intent.putExtras(bundle1);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("Name", Name.get(a));
                        intent.putExtras(bundle2);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString("Zone", zone.get(a));
                        intent.putExtras(bundle3);

                        Bundle bundle4 = new Bundle();
                        bundle4.putString("Floor", floor.get(a));
                        intent.putExtras(bundle4);

//                Bundle bundle5 = new Bundle();
//                bundle5.putString("Content", content.get(a));
//                intent.putExtras(bundle5);

                        Bundle bundle6 = new Bundle();
                        bundle6.putString("OpenTime", openTime.get(a));
                        intent.putExtras(bundle6);

                        Bundle bundle7 = new Bundle();
                        bundle7.putString("StoreID", storeID.get(a));
                        intent.putExtras(bundle7);

                        Bundle bundle8 = new Bundle();
                        bundle8.putString("Big", big.get(a));
                        intent.putExtras(bundle8);

                        startActivity(intent);
                    }
                }, 290);
            }
        });

        imageView2.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                imageView2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                imageView2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(Brand.this, Info.class);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Logo", logo.get(b));
                        intent.putExtras(bundle1);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("Name", Name.get(b));
                        intent.putExtras(bundle2);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString("Zone", zone.get(b));
                        intent.putExtras(bundle3);

                        Bundle bundle4 = new Bundle();
                        bundle4.putString("Floor", floor.get(b));
                        intent.putExtras(bundle4);

//                Bundle bundle5 = new Bundle();
//                bundle5.putString("Content", content.get(b));
//                intent.putExtras(bundle5);

                        Bundle bundle6 = new Bundle();
                        bundle6.putString("OpenTime", openTime.get(b));
                        intent.putExtras(bundle6);

                        Bundle bundle7 = new Bundle();
                        bundle7.putString("StoreID", storeID.get(b));
                        intent.putExtras(bundle7);

                        Bundle bundle8 = new Bundle();
                        bundle8.putString("Big", big.get(b));
                        intent.putExtras(bundle8);

                        startActivity(intent);
                    }
                }, 290);
            }
        });

        imageView3.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                imageView3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                imageView3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(Brand.this, Info.class);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Logo", logo.get(c));
                        intent.putExtras(bundle1);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("Name", Name.get(c));
                        intent.putExtras(bundle2);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString("Zone", zone.get(c));
                        intent.putExtras(bundle3);

                        Bundle bundle4 = new Bundle();
                        bundle4.putString("Floor", floor.get(c));
                        intent.putExtras(bundle4);

//                Bundle bundle5 = new Bundle();
//                bundle5.putString("Content", content.get(c));
//                intent.putExtras(bundle5);

                        Bundle bundle6 = new Bundle();
                        bundle6.putString("OpenTime", openTime.get(c));
                        intent.putExtras(bundle6);

                        Bundle bundle7 = new Bundle();
                        bundle7.putString("StoreID", storeID.get(c));
                        intent.putExtras(bundle7);

                        Bundle bundle8 = new Bundle();
                        bundle8.putString("Big", big.get(c));
                        intent.putExtras(bundle8);

                        startActivity(intent);
                    }
                }, 290);
            }
        });

        imageView4.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                imageView4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                imageView4.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(Brand.this, Info.class);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Logo", logo.get(d));
                        intent.putExtras(bundle1);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("Name", Name.get(d));
                        intent.putExtras(bundle2);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString("Zone", zone.get(d));
                        intent.putExtras(bundle3);

                        Bundle bundle4 = new Bundle();
                        bundle4.putString("Floor", floor.get(d));
                        intent.putExtras(bundle4);

//                Bundle bundle5 = new Bundle();
//                bundle5.putString("Content", content.get(d));
//                intent.putExtras(bundle5);

                        Bundle bundle6 = new Bundle();
                        bundle6.putString("OpenTime", openTime.get(d));
                        intent.putExtras(bundle6);

                        Bundle bundle7 = new Bundle();
                        bundle7.putString("StoreID", storeID.get(d));
                        intent.putExtras(bundle7);

                        Bundle bundle8 = new Bundle();
                        bundle8.putString("Big", big.get(d));
                        intent.putExtras(bundle8);

                        startActivity(intent);
                    }
                }, 290);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection conn = null;
                try {
                    String mWeatherUrl = "https://cgutemi.nctu.me/BrandLists/"+BrandType;

                    URL url = new URL(mWeatherUrl);

                    SSLContext sslContext = null;
                    try {
                        TrustManager[] tm = {
                                new X509TrustManager() {
                                    @Override
                                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                                    @Override
                                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                                    @Override
                                    public X509Certificate[] getAcceptedIssuers() { return null; }
                                }
                        };
                        sslContext = SSLContext.getInstance("SSL");
                        sslContext.init(null, tm, null);
                        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        });
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    conn = (HttpsURLConnection) url.openConnection(); // open connect
                    conn.setSSLSocketFactory(sslContext.getSocketFactory());

                    conn.setRequestMethod("GET");										 // using GET method
                    int responseCode = conn.getResponseCode();       // responseCode will return what eror.
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));   //read your data
                    String inputLine = "";
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    Log.d("Response", response.toString());


                        JSONObject jsonObject1 = new JSONObject(response.toString());
                        JSONArray jsonArray = jsonObject1.getJSONArray(BrandType);
                        total = jsonArray.length();
                        if (total%4==0){
                            allnum = total/4;
                        }else {
                            allnum = (total/4)+1;
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            //取出name
                            String StoreID = jsonObject.getString("StoreID");
                            String BrandID = jsonObject.getString("BrandID");
                            String BrandName = jsonObject.getString("BrandName");
                            String ZoneID = jsonObject.getString("ZoneID");
                            String Floor = jsonObject.getString("Floor");
                            String Link = jsonObject.getString("Link");
                            String Content = jsonObject.getString("Content");
                            String OpenTime = jsonObject.getString("OpenTime");
                            String BrandPicB = jsonObject.getString("BrandPicB");
                            String BrandPicBIG = jsonObject.getString("BrandPicBIG");

                            Log.e("StoreID", StoreID);
                            Log.e("BrandID", BrandID);
                            Log.e("BrandName", BrandName);
                            Log.e("ZoneID", ZoneID);
                            Log.e("Floor", Floor);
                            Log.e("Link", Link);
                            Log.e("Content", Content);
                            Log.e("OpenTime", OpenTime);
                            Log.e("BrandPicB", BrandPicB);
                            Log.e("BrandPicBIG", BrandPicBIG);

                            storeID.add(StoreID);
                            brandID.add(BrandID);
                            Name.add(BrandName);
                            zone.add(ZoneID);
                            floor.add(Floor);
                            link.add(Link);
                            content.add(Content);
                            openTime.add(OpenTime);
                            logo.add(BrandPicB);
                            big.add(BrandPicBIG);


                            if (i==3){
                            Log.e("Start", "Start");
                            setText(txt1, Name.get(a));
                            setText(txt2, Name.get(b));
                            setText(txt3, Name.get(c));
                            setText(txt4, Name.get(d));
                            setLOGO(imageView1, logo.get(a));
                            setLOGO(imageView2, logo.get(b));
                            setLOGO(imageView3, logo.get(c));
                            setLOGO(imageView4, logo.get(d));
                            Log.e("Finish", "Finish");
                            }
                        }

                        setText(txtAll, numToStr(allnum));

                    if (jsonArray.length()==4){
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) conn.disconnect();               //make sure you disconnnect
                }
            }
        }).start();

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
                Glide.with(Brand.this)
                        .load(value)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(25)))
                        .into(imageView);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    private void setVis(final GifImageView image){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                image.setVisibility(View.GONE);
            }
        });
    }

    private void  setAnimation(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt1.setVisibility(View.GONE);
                txt2.setVisibility(View.GONE);
                txt3.setVisibility(View.GONE);
                txt4.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);
                imageView3.setVisibility(View.GONE);
                imageView4.setVisibility(View.GONE);
            }
        });

    }

    private String numToStr(final int i){
        String s = new String(""+i);
        return s;
    }
}