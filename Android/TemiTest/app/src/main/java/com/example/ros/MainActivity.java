package com.example.ros;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;

import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Calendar;

public class MainActivity extends Activity implements OnRobotReadyListener {

    private static final String TAG = "data";
    private static final String TAG_Life = "life_cycle";
    private static Robot robot;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        buildViews();

        // Keep the screen on
        // https://developer.android.com/training/scheduling/wakelock
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        // Static way to get 'Context' on Android?
        // https://bibby1101.pixnet.net/blog/post/62556473-%3Candroid%3E-static-way-to-get-%27context%27-on-android%3F
        MainActivity.context = getApplicationContext();


        Log.d("Name", "Name");
        Log.d("Name", getDeviceName());


        // Main

        // Change Background color (Basic & Gradient 漸層)
        // https://stackoverflow.com/questions/2748830/how-to-change-background-color-in-android-app

        // Change Background by edit HEX in `colors.xml`

        // https://learnexp.tw/【android】隱藏標題列title-bar與狀態列status-bar/
        //getSupportActionBar().hide(); //Hide Action Bar (Navigation Bar)

        // Hide Action Bar & Status Bar
        // http://dog0416.blogspot.com/2018/04/android-hide-action-bar-and-status-bar.html
        // Modify 2 files :
        // Path : res/values/styles.xml
        //      `<style name="AppTheme.NoActionBar" parent="AppTheme">` flag
        // Path : AndroidManifest.xml
        //      Default : android:theme="@style/AppTheme"
        //      Modified : android:theme="@style/AppTheme.NoActionBar"


        robot = Robot.getInstance();



        // Make app running specified function in a period
        // https://www.itread01.com/p/1358274.html
        final int time = 1000  ; // set period time (ms)
        final Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                // 在此處新增執行的程式碼
                pubUtils();

                handler.postDelayed(this, time);// time ms後執行this,即runable
            }
        };
        handler.postDelayed(runnable, time);// 開啟定時器,time ms後執行runnable操作

        final int Time = 5000  ; // set period time (ms)
        final Handler handler1 = new Handler();
        Runnable runnable1 = new Runnable(){
            @Override
            public void run() {
                // 在此處新增執行的程式碼
                Log.e("Hi", "Hi");
                handler1.postDelayed(this, Time);// time ms後執行this,即runable
            }
        };
        handler1.postDelayed(runnable1, Time);// 開啟定時器,time ms後執行runnable操作
    }

    @Override
    protected void onStart() {
        super.onStart();
        robot.addOnRobotReadyListener(this);
        robot.showTopBar();
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
        robot.removeOnRobotReadyListener(this);
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

    @Override
    public void onRobotReady(boolean isReady) {
        Log.d(TAG_Life, "onRobotReady() function");
        if (isReady) {
            try {
                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                // Robot.getInstance().onStart() method may change the visibility of top bar.
                robot.onStart(activityInfo);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void buildViews() {
        final ImageView btGuide;
        final ImageView btActivity;
        final ImageView btDiscount;

        btGuide = (ImageView) findViewById(R.id.btGuide);
        btActivity = (ImageView) findViewById(R.id.btActivity);
        btDiscount = (ImageView) findViewById(R.id.btDiscount);

        ViewCompat.setElevation(btGuide, 30);
        ViewCompat.setElevation(btActivity, 30);
        ViewCompat.setElevation(btDiscount, 30);

        btGuide.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                btGuide.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                btGuide.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, Location.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                    }
                }, 290);
            }
        });

        btActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btActivity.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                btActivity.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, Event.class);
                        startActivity(intent);
                    }
                }, 290);
            }
        });

        btDiscount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btDiscount.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btanim));
                btDiscount.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, Discount.class);
                        startActivity(intent);
                    }
                }, 290);
            }
        });
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    // https://stackoverflow.com/questions/5369682/how-to-get-current-time-and-date-in-android
    public static void pubUtils(){
        //batteryInfo batteryInfo = getbatteryInfo();

        String serialNumber = getSerialNumber();
        String currentTime = Calendar.getInstance().getTime().toString();
        String mac = getMacAddr();
        String ip = getLocalIpAddress();
        String ssid = WifiUtils.getSSID(MainActivity.context);
        String rssi = WifiUtils.getRssi(MainActivity.context);
        String memoryUsage = getUsedMemorySize();
        int batteryPercentage = getbatteryInfo().batteryLevel;
        boolean isCharging = getbatteryInfo().isCharging;
        //String msg = String.format("{\"%s\":\"{%s, %s, %s, %s, %d%%, %s}\"}", serialNumber, serialNumber, mac, currentTime, ip, batteryPercentage, isCharging);
        String msg = String.format("{\"%s\":{\"SN\":\"%s\", \"Time\":\"%s\", \"MAC\":\"%s\", \"SSID\":\"%s\", \"IP\":\"%s\", \"RSSI\":\"%s\", \"BatteryLevel\":\"%d%%\", \"isCharging\":\"%s\", \"Mem\":\"%s\"}}", serialNumber, serialNumber, currentTime.replace("GMT+08:00 2020", ""), mac, ssid.replace("\"", ""), ip, rssi, batteryPercentage, isCharging, memoryUsage);
        //Log.d(TAG, msg);
        publishUtilTOMqtt(msg);
    }

    // https://stackoverflow.com/questions/2832472/how-to-return-2-values-from-a-java-method
    // Return 2 values in a function
    static final class batteryInfo {
        private final int batteryLevel;
        private final boolean isCharging;
        BatteryData batteryData = robot.getBatteryData();

        public batteryInfo(int batteryLevel, boolean isCharging) {
            this.batteryLevel = batteryLevel;
            this.isCharging = isCharging;
        }

        public int getBatteryLevel() {

            return batteryLevel;
        }
        public boolean isCharging() {
            return isCharging;
        }
    }

    public static batteryInfo getbatteryInfo(){
        BatteryData batteryData = robot.getBatteryData();
        if(batteryData == null){
            Log.d(TAG, "batteryData is null");
            return null;
        } else {
            int batteryLevel = batteryData.getBatteryPercentage();
            boolean isCharging = batteryData.isCharging();
            return new batteryInfo(batteryLevel, isCharging);
        }
    }

    public static void publishUtilTOMqtt(String content){ // Void function without return value (ex. string, int ...

        String broker = "tcp://120.126.16.92:1883";
        String clientId = "Temi";
        MemoryPersistence persistence = new MemoryPersistence();
        String topic = "test/sub_topic";
        //String content = currentTime.toString();
        // Setting mqtt connection quaility
        // https://swf.com.tw/?p=1015
        int qos = 0;

        Log.d(TAG, "message: " + content);

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            //Log.d(TAG, "Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            Log.d(TAG, "Connected");
            //Log.d(TAG, "Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            Log.d(TAG, "Message published");
            sampleClient.disconnect();
            //Log.d(TAG, "Disconnected");
            //System.exit(0);
        } catch (MqttException me) {
            Log.e(TAG, String.valueOf(me));
            Log.d(TAG, "reason: " + me.getReasonCode());
            Log.d(TAG, "msg: " + me.getMessage());
            Log.d(TAG, "loc: " + me.getLocalizedMessage());
            Log.d(TAG, "cause: " + me.getCause());
            Log.d(TAG, "excep " + me);
            me.printStackTrace();
        }
    }

    public static String getUsedMemorySize() {
        /*
        long freeSize = 0L;
        long totalSize = 0L;
        long usedSize = -1L;
        try {
            Runtime info = Runtime.getRuntime();
            freeSize = info.freeMemory();
            totalSize = info.totalMemory();
            Log.d("mmm", String.valueOf(totalSize));
            usedSize = totalSize - freeSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.toString(usedSize);
        */

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        Runtime runtime = Runtime.getRuntime();

        Long convert = 1048576L; // convert Long value to MB for human reading
        String totalMem = String.valueOf(memoryInfo.totalMem / convert);
        String avaiMem = String.valueOf(memoryInfo.availMem / convert);
        String usedMem = String.valueOf((memoryInfo.totalMem - memoryInfo.availMem)/convert);
        String runtimeMaxMem = String.valueOf(runtime.maxMemory() / convert);
        String runtimeTotalMem = String.valueOf(runtime.totalMemory() / convert);
        String runtimeFreeMem = String.valueOf(runtime.freeMemory()/ convert);
        String runtimeUsedMem =  String.valueOf((runtime.totalMemory() - runtime.freeMemory())/convert);

        Log.d("mmm", "totalMem = " + totalMem + " MB");
        Log.d("mmm", "avaiMem = " + avaiMem + " MB");
        Log.d("mmm", "usedMem = " + usedMem + " MB");
        Log.d("mmm", "runtimeMaxMem = " + runtimeMaxMem + " MB");
        Log.d("mmm", "runtimeTotalMem = " + runtimeTotalMem + " MB");
        Log.d("mmm", "runtimeFreeMem = " + runtimeFreeMem + " MB");
        Log.d("mmm", "runtimeUsedMem = " + runtimeUsedMem + " MB");

        /*
        08-11 21:48:03.565 D/mmm: totalMem = 2109140992
        08-11 21:48:03.565 D/mmm: avaiMem = 1186332672
        08-11 21:48:03.565 D/mmm: runtimeMaxMem = 201326592
        08-11 21:48:03.566 D/mmm: runtimeTotalMem = 3134812
        08-11 21:48:03.566 D/mmm: runtimeFreeMem = 952020
        */

        return "123";

    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    // ======== end of `getDeviceName()`

    public static String getSerialNumber(){
        String serialNumber = robot.getSerialNumber();
        return serialNumber;
    }

    public static class WifiUtils {

        public static WifiInfo getWifiInfo(Context context) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                return wifiManager.getConnectionInfo();
            }

            return null;
        }

        public static String getSSID(Context context) {
            WifiInfo wifiInfo = getWifiInfo(context);
            if (wifiInfo != null) {
                return wifiInfo.getSSID();
            }
            return null;
        }

        public static String getRssi(Context context){
            WifiInfo wifiInfo = getWifiInfo(context);
            if (wifiInfo != null){
                return String.valueOf(wifiInfo.getRssi());
            }
            return null;
        }
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    } //end of function getLocalIpAddress()

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "null";
    } //end of function getMacAddr()
}
