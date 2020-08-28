package com.example.ros;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Information extends Page {

    private int total;
    private int allnum;
    private int thisnum;
    private int a = 0;
    private int b = 1;
    private int c = 2;
    private ArrayList<String> NewsID = new ArrayList<String>();
    private ArrayList<String> Title = new ArrayList<String>();
    private ArrayList<String> Content = new ArrayList<String>();
    private ArrayList<String> ACTIVITY_DATE = new ArrayList<String>();
    private ArrayList<String> INDEX_PIC_PATH = new ArrayList<String>();
    private ArrayList<String> INDEX_PIC_PATH2 = new ArrayList<String>();

    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txtAll;
    private TextView txtThis;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private GifImageView image1;
    private GifImageView image2;
    private GifImageView image3;
    private Button btL;
    private Button btR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        image1 = (GifImageView) findViewById(R.id.image1);
        image2 = (GifImageView) findViewById(R.id.image2);
        image3 = (GifImageView) findViewById(R.id.image3);
        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt3 = (TextView)findViewById(R.id.txt3);
        txtAll = (TextView)findViewById(R.id.txtAll);
        txtThis = (TextView)findViewById(R.id.txtThis);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
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

        txt1.setText("Loading...."); txt2.setText("Loading...."); txt3.setText("Loading....");

        try {
            // 如果載入的是gif動圖，第一步需要先將gif動圖資源轉化為GifDrawable
            // 將gif圖資源轉化為GifDrawable
            GifDrawable gifDrawable1 = new GifDrawable(getResources(), R.drawable.loading);
            GifDrawable gifDrawable2 = new GifDrawable(getResources(), R.drawable.loading);
            GifDrawable gifDrawable3 = new GifDrawable(getResources(), R.drawable.loading);
            // gif1載入一個動態圖gif
            image1.setImageDrawable(gifDrawable1);
            image2.setImageDrawable(gifDrawable2);
            image3.setImageDrawable(gifDrawable3);
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

                a-=3; b-=3; c-=3;

                if(a==-4){
                    a+=4; b+=4; c+=4;
                }else if(a==0){
                    btL.setClickable(false);
                    btL.setAlpha((float) 0.3);
                }else{
                    btL.setClickable(true);
                    btL.setAlpha((float) 1);
                }

                if(c>total-1){
                    int num = c+1 - total;
                    if(num == 1){
                        txt1.setText(Title.get(a));
                        txt2.setText(Title.get(b));
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, INDEX_PIC_PATH.get(a));
                        setLOGO(imageView2, INDEX_PIC_PATH.get(b));

                        imageView3.setBackgroundResource(R.mipmap.empty);
                        imageView3.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image3);
                    }else if(num == 2){
                        txt1.setText(Title.get(a));
                        txt1.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, INDEX_PIC_PATH.get(a));

                        imageView2.setBackgroundResource(R.mipmap.empty);
                        imageView3.setBackgroundResource(R.mipmap.empty);
                        imageView2.setVisibility(View.GONE);
                        imageView3.setVisibility(View.GONE);
                        txt2.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image2);
                        setVis(image3);
                    }
                }else if(c == total-1){
                    txt1.setText(Title.get(a));
                    txt2.setText(Title.get(b));
                    txt3.setText(Title.get(c));
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);

                    setLOGO(imageView1, INDEX_PIC_PATH.get(a));
                    setLOGO(imageView2, INDEX_PIC_PATH.get(b));
                    setLOGO(imageView3, INDEX_PIC_PATH.get(c));
                    imageView1.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView2.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView3.setBackgroundResource(R.drawable.bd_circle_shape);
                    btR.setClickable(false);
                    btR.setAlpha((float) 0.3);

                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.VISIBLE);
                }else{
                    txt1.setText(Title.get(a));
                    txt2.setText(Title.get(b));
                    txt3.setText(Title.get(c));
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);

                    setLOGO(imageView1, INDEX_PIC_PATH.get(a));
                    setLOGO(imageView2, INDEX_PIC_PATH.get(b));
                    setLOGO(imageView3, INDEX_PIC_PATH.get(c));
                    imageView1.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView2.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView3.setBackgroundResource(R.drawable.bd_circle_shape);
                    btR.setClickable(true);
                    btR.setAlpha((float) 1);

                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.VISIBLE);
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

                a+=3; b+=3; c+=3;

                if(a==-4){
                    a+=4; b+=4; c+=4;
                }else if(a==0){
                    btL.setClickable(false);
                    btL.setAlpha((float) 0.3);
                }else{
                    btL.setClickable(true);
                    btL.setAlpha((float) 1);
                }

                if(c>total-1){
                    int num = c+1 - total;
                    if(num == 1){
                        txt1.setText(Title.get(a));
                        txt2.setText(Title.get(b));
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, INDEX_PIC_PATH.get(a));
                        setLOGO(imageView2, INDEX_PIC_PATH.get(b));

                        imageView3.setBackgroundResource(R.mipmap.empty);
                        imageView3.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image3);
                    }else if(num == 2){
                        txt1.setText(Title.get(a));
                        txt1.setVisibility(View.VISIBLE);
                        setLOGO(imageView1, INDEX_PIC_PATH.get(a));

                        imageView2.setBackgroundResource(R.mipmap.empty);
                        imageView3.setBackgroundResource(R.mipmap.empty);
                        imageView2.setVisibility(View.GONE);
                        imageView3.setVisibility(View.GONE);
                        txt2.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                        btR.setClickable(false);
                        btR.setAlpha((float) 0.3);
                        setVis(image2);
                        setVis(image3);
                    }
                }else if(c == total-1){
                    txt1.setText(Title.get(a));
                    txt2.setText(Title.get(b));
                    txt3.setText(Title.get(c));
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);

                    setLOGO(imageView1, INDEX_PIC_PATH.get(a));
                    setLOGO(imageView2, INDEX_PIC_PATH.get(b));
                    setLOGO(imageView3, INDEX_PIC_PATH.get(c));
                    imageView1.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView2.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView3.setBackgroundResource(R.drawable.bd_circle_shape);
                    btR.setClickable(false);
                    btR.setAlpha((float) 0.3);

                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.VISIBLE);
                }else{
                    txt1.setText(Title.get(a));
                    txt2.setText(Title.get(b));
                    txt3.setText(Title.get(c));
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);

                    setLOGO(imageView1, INDEX_PIC_PATH.get(a));
                    setLOGO(imageView2, INDEX_PIC_PATH.get(b));
                    setLOGO(imageView3, INDEX_PIC_PATH.get(c));
                    imageView1.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView2.setBackgroundResource(R.drawable.bd_circle_shape);
                    imageView3.setBackgroundResource(R.drawable.bd_circle_shape);
                    btR.setClickable(true);
                    btR.setAlpha((float) 1);

                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.VISIBLE);
                }
            }
        });

        btBack.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Information.super.LastPage();
            }
        });

        btHome.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Information.super.HomePage();
            }
        });

        imageView1.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                imageView1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                imageView1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(Information.this, InfoAct.class);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("NewsID", NewsID.get(a));
                        intent.putExtras(bundle1);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("Content", Content.get(a));
                        intent.putExtras(bundle2);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString("INDEX_PIC_PATH", INDEX_PIC_PATH.get(a));
                        intent.putExtras(bundle3);

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
                        intent.setClass(Information.this, InfoAct.class);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("NewsID", NewsID.get(b));
                        intent.putExtras(bundle1);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("Content", Content.get(b));
                        intent.putExtras(bundle2);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString("INDEX_PIC_PATH", INDEX_PIC_PATH.get(b));
                        intent.putExtras(bundle3);

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
                        intent.setClass(Information.this, InfoAct.class);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("NewsID", NewsID.get(c));
                        intent.putExtras(bundle1);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("Content", Content.get(c));
                        intent.putExtras(bundle2);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString("INDEX_PIC_PATH", INDEX_PIC_PATH.get(c));
                        intent.putExtras(bundle3);

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
                    String mWeatherUrl = "https://cgutemi.nctu.me/events";

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
                    JSONArray jsonArray = jsonObject1.getJSONArray("events");
                    total = jsonArray.length();
                    if (total%3==0){
                        allnum = total/3;
                    }else {
                        allnum = (total/3)+1;
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        //取出name
                        String newsID = jsonObject.getString("NewsID");
                        String title = jsonObject.getString("Title");
                        String content = jsonObject.getString("Content");
                        String aCTIVITY_DATE = jsonObject.getString("ACTIVITY_DATE");
                        String iNDEX_PIC_PATH = jsonObject.getString("INDEX_PIC_PATH");
                        String iNDEX_PIC_PATH2 = jsonObject.getString("INDEX_PIC_PATH2");

                        Log.e("NewsID", newsID);
                        Log.e("Title", title);
                        Log.e("Content", content);
                        Log.e("ACTIVITY_DATE", aCTIVITY_DATE);
                        Log.e("INDEX_PIC_PATH", iNDEX_PIC_PATH);
                        Log.e("INDEX_PIC_PATH2", iNDEX_PIC_PATH2);

                            NewsID.add(newsID);
                            Title.add(title);
                            Content.add(content);
                            ACTIVITY_DATE.add(aCTIVITY_DATE);
                            INDEX_PIC_PATH.add(iNDEX_PIC_PATH);
                            INDEX_PIC_PATH2.add(iNDEX_PIC_PATH2);

                        if (i==2){
                            Log.e("Start", "Start");
                            setText(txt1, Title.get(a));
                            setText(txt2, Title.get(b));
                            setText(txt3, Title.get(c));
                            setLOGO(imageView1, INDEX_PIC_PATH.get(a));
                            setLOGO(imageView2, INDEX_PIC_PATH.get(b));
                            setLOGO(imageView3, INDEX_PIC_PATH.get(c));
                            Log.e("Finish", "Finish");
                        }
                    }
                    setText(txtAll, numToStr(allnum));

                    if (jsonArray.length()==3){
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
                Glide.with(Information.this)
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
                imageView1.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);
                imageView3.setVisibility(View.GONE);
            }
        });

    }

    private String numToStr(final int i){
        String s = new String(""+i);
        return s;
    }
}