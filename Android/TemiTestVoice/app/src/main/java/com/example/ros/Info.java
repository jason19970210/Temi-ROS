package com.example.ros;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Info extends Page implements OnGoToLocationStatusChangedListener{

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
    private TtsRequest going;
    private TtsRequest finish;

    List<String> locations;
    private static Robot robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        robot = Robot.getInstance();
        locations = robot.getLocations();

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
        String Floor = bundle4.getString("Floor");
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

        txtName.setText("專櫃名稱：" + Name);
        txtOpenTime.setText("營業時間：" + OpenTime);
        txtLocation.setText("　　"+"專櫃位置：" + Zone+ "區" + Floor);

        String  floor;
        if(Floor.contains("-")){
            Log.e("ifZ", Floor);
            Floor = Floor.replace('-', '之');
            Log.e("ifZ", Floor);
        }

        if (Floor.charAt(0)=='L'){
            Log.e("ifLB", Floor);
            floor = Floor.replace("LB", "Lobby ");
            Log.e("ifLB", floor);
        }else if(Floor.charAt(0)=='B'){
            Log.e("ifB", Floor);
            floor = Floor.replace("B1", "B one ");
            Log.e("ifB", floor);
        }else if(Floor.charAt(0)=='R'){
            Log.e("ifR", Floor);
            floor = Floor.replace("RF", "頂樓");
            Log.e("ifR", floor);
        }else {
            Log.e("ifF", Floor);
            floor = Floor.replace('F', '樓');
            Log.e("ifF", floor);
        }
        final TtsRequest Intro = TtsRequest.create(Name.trim()+"位於"+Zone+"區"+floor.trim(), false);
        final TtsRequest Intro2 = TtsRequest.create(Name.trim()+"位於"+Zone+"區"+floor.trim()+"按下立即前往即可位您帶位", false);

        if(Zone.equals("A") && Floor.equals("4F")){
            robot.speak(Intro2);
        }else {
            robot.speak(Intro);
        }

        final TtsRequest Going = TtsRequest.create("正在前往"+Name.trim()+"請跟在我身後", true);
        going = Going;
        final TtsRequest Finish = TtsRequest.create("已抵達"+Name.trim()+"祝您購物愉快", true);
        finish = Finish;

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

        setLOGO(imageLogo, Logo);
        setLOGO(imageBig, Big);
        getCode(imageQRcode, StoreID);

        btCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

//        void onGoToLocationStatusChanged(String location, String status, int descriptionId, String description);
//        void addOnGoToLocationStatusChangedListener(OnGoToLocationStatusChangedListener listener);


        btGoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                btGoto.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                btGoto.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("location_list", String.valueOf(locations.size()));
                        Log.d("location_list", StoreID);
                        if(locations.contains(StoreID)){
                            robot.toggleNavigationBillboard(true);
                            robot.goTo(StoreID);
                        } else {
                            Toast.makeText(getApplicationContext(),"地圖資料未建立",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 290);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        robot.addOnGoToLocationStatusChangedListener(this);
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
        robot.removeOnGoToLocationStatusChangedListener(this);
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


    @Override
    public void onGoToLocationStatusChanged(@NotNull String location, @NotNull String status, int descriptionId, @NotNull String description) {
        switch (status) {
            case "start":
//                robot.speak(TtsRequest.create("Starting", false));
                robot.speak(going);
                break;

            case "calculating":
//                robot.speak(TtsRequest.create("Calculating", false));
                break;

            case "going":
//                robot.speak(TtsRequest.create("Going", false));
                break;

            case "complete":
//                robot.speak(TtsRequest.create("Completed", false));
                robot.speak(finish);
                break;

            case "abort":
//                robot.speak(TtsRequest.create("Cancelled", false));
                break;
        }
    }
}
