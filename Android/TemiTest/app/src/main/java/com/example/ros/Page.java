package com.example.ros;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class Page extends Activity {
    public void LastPage(){
        finish();
    }

    public void HomePage(){
        Intent intent = new Intent();
        intent.setClass(this , MainActivity.class);
        startActivity(intent);

        finish();
    }

    public void SwitchPage(Class nextPage){
        Intent intent = new Intent();
        intent.setClass(this , nextPage);
        startActivity(intent);
    }
}
