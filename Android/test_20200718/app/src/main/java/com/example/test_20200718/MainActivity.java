package com.example.test_20200718;

import androidx.appcompat.app.AppCompatActivity;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Collections;
import java.util.Calendar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.MemoryFile;
import android.util.Log;
import android.os.Handler;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import android.hardware.camera2.*;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.jetbrains.annotations.NotNull;

import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.constants.SdkConstants;
import com.robotemi.sdk.constants.Utils;
import com.robotemi.sdk.activitystream.ActivityStreamObject;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
//import com.robotemi.sdk.constants.ContentType;
import com.robotemi.sdk.constants.SdkConstants;
//import com.robotemi.sdk.exception.OnSdkExceptionListener;
//import com.robotemi.sdk.exception.SdkException;
//import com.robotemi.sdk.face.ContactModel;
//import com.robotemi.sdk.face.OnFaceRecognizedListener;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnConstraintBeWithStatusChangedListener;
//import com.robotemi.sdk.listeners.OnDetectionDataChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
//import com.robotemi.sdk.listeners.OnRobotLiftedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnTelepresenceEventChangedListener;
import com.robotemi.sdk.listeners.OnUserInteractionChangedListener;
import com.robotemi.sdk.model.CallEventModel;
//import com.robotemi.sdk.model.DetectionData;
//import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener;
//import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener;
//import com.robotemi.sdk.navigation.model.Position;
//import com.robotemi.sdk.navigation.model.SafetyLevel;
//import com.robotemi.sdk.navigation.model.SpeedLevel;
//import com.robotemi.sdk.permission.OnRequestPermissionResultListener;
import com.robotemi.sdk.permission.Permission;


// NodeJS Port : 3000
// MQTT Port : 1883
// Redis Port : 6379
// Redis Webadmin Port : 8085
// arangoDB Webadmin Port : 8529

public class MainActivity extends AppCompatActivity implements OnRobotReadyListener {

    private static final String TAG = "data";
    private static final String TAG_Life = "life_cycle";

    List<String> locations;
    private static Robot robot;

    private static Context context;

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG_Life, "onStart() function");
        robot.addOnRobotReadyListener(this);
//        robot.addOnGoToLocationStatusChangedListener(this);
//        robot.addOnLocationsUpdatedListener(this);
        robot.showTopBar();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG_Life, "onStop() function");
        robot.removeOnRobotReadyListener(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG_Life, "onDestroy() function");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG_Life, "onCreate() function");

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

        //goTo();



        // Make app running specified function in a period
        // https://www.itread01.com/p/1358274.html
        final int time = 1000  ; // set period time (ms)
        final Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                // 在此處新增執行的程式碼
                //pubUtils();

                //goTo();
                handler.postDelayed(this, time);// time ms後執行this,即runable
            }
        };
        handler.postDelayed(runnable, time);// 開啟定時器,time ms後執行runnable操作

    } // end of onCreate()


    //public void goTo(View view) {
    public void goTo() {
        Log.d("location", "goTo()");
        locations = robot.getLocations();
        Log.d("Location", locations.toString());
//        for (String location : robot.getLocations()) {
//            Log.d("location", location);
//            if (location.equals(etGoTo.getText().toString().toLowerCase().trim())) {
//                robot.goTo(etGoTo.getText().toString().toLowerCase().trim());
//                hideKeyboard(MainActivity.this);
//            }
//        }
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

} //end of MainActivity class