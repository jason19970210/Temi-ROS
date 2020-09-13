package com.example.ros;

import android.content.Context;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.Timer;
import java.util.TimerTask;

public class Game extends Page {
    private int num = getrandom(1, 15);
    private int i = 0;
    int min = 1;
    int max = 15;
    private String St = "失敗";
    private EditText inputText;
    private Button btGO;
    private Button btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        inputText = (EditText)findViewById(R.id.txtSub);
        btGO = (Button)findViewById(R.id.btGO);
        btBack = (Button)findViewById(R.id.btBack);

        ViewCompat.setElevation(inputText, 20);

//        inputText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
//        inputText.setFocusable(true);
//        inputText.setFocusableInTouchMode(true);
//        inputText.requestFocus(); //先將inputText取得焦點
//        waitPop();//再另一方法裡等待彈出,因為在onCreate()方法中android會做一些準備工作,使鍵盤無法彈出,那麼我們就等一會兒,個人覺得0.3秒比較好。
//        //等待彈出方法

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
        final TextView txtRange;

        txtRange = (TextView) findViewById(R.id.range);
        txtRange.setText("1~15");

        btGO.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                i++;

                String S = inputText.getText().toString();
                int Input= Integer.parseInt(S);
                Run(Input);

                String stMin = ""+min;
                String stMax = ""+max;
                txtRange.setText(stMin +'~'+ stMax);
                inputText.setText("");
            }
        });

        btBack.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SwitchPage(Discount.class);
                finish();
            }
        });
    }

    private void Run(int input){
        String st;

        if(min<=input&&input<=max){
            if(i == 3){
                if(input == num){
                    st = "成功!!";
                    Toast.makeText(Game.this, st, Toast.LENGTH_SHORT).show();
                    SwitchPage(Success.class);
                    finish();
                }else {
                    Toast.makeText(Game.this, St, Toast.LENGTH_SHORT).show();
                    SwitchPage(Failed.class);
                    finish();
                }
            } else if (input > num) {
                max = input;
                st = "數字太大，請往小的猜";
                Toast.makeText(Game.this, st, Toast.LENGTH_LONG).show();
            } else if (input < num) {
                min = input;
                st = "數字太小，請往大的猜";
                Toast.makeText(Game.this, st, Toast.LENGTH_LONG).show();
            } else if (input == num){
                st = "成功!!";
                Toast.makeText(Game.this, st, Toast.LENGTH_SHORT).show();
                SwitchPage(Success.class);
                finish();
            }
        }else{
            st = "請輸入正確範圍！";
            Toast.makeText(Game.this, st, Toast.LENGTH_LONG).show();
            i--;
        }
    }

    private static int getrandom(int min, int max) {
        return (int)(Math.random()*(max-min+1)+min);
    }

//    private void waitPop() {
//        Timer timer = new Timer();//開啟一個時間等待任務
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                InputMethodManager imm = (InputMethodManager)inputText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//得到系統的輸入方法服務
//                imm.showSoftInput(inputText, 0);
//            }
//        }, 300);
//    }
}
