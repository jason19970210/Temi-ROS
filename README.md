# Temi-ROS

## Project Web & System Monitor
- https://cgutemi.nctu.me


## Environment & Tools
- Ubuntu 18.04
    - MQTT Broker
    - Redis DB Server
    - Redis Web GUI Console
    - NodeJS
    - Python
        - Sub MQTT & Write Data into Redis DB
- Temi Robot
    - Java
        - MQTT Client

## Installation
### Ubuntu
- MQTT
    - Broker
        ```zsh
        $ sudo apt update -y && sudo apt install -y mosquitto
        ```
        - Enable Service
            ```zsh
            $ sudo systemctl enable mosquitto
            ```
    - Client
        ```zsh
        $ sudo apt update -y && sudo apt install -y mosquitto-clients
        ```
- Redis
    ```zsh
    $ sudo apt update -y && sudo apt install redis-server -y
    ```

    - Enable Service
        ```zsh
        $ sudo systemctl enable redis-server.service
        ```
    - Config
        ```zsh
        $ sudo vim /etc/redis/redis.conf
        ```
        ```
        bind 0.0.0.0
        # port (default 6379)
        # requirepass <password>
        stop-writes-on-bgsave-error no
        ```

- NoeJS
    - Node & NPM
        ```zsh
        $ sudo apt update -y && sudo apt install nodejs -y
        ```
- Python
    - Python v2.7
        ```zsh
        $ sudo apt install python -y
        ```
    - Python v3.6+
        - MQTT Client
            ```zsh
            $ pip3 install paho-mqtt
            ```
        - redis
            ```zsh
            $ pip3 install redis
            ```



## Connect to Temi
- Install Android Studio
- Install ADB (Android Debug Bridge)
    - Windows
        1. Download SDK Platform-Tools
    - MacOS
        - Via `Homebrew`
            1. Make sure brew is installed
            2. `$ brew cask install android-platform-tools`
        - Mannual
            1. If installed before, run this command `$ rm -rf ~/.android-sdk-macosx/` to delete privious document
            2. Download SDK Platform-Tools
            <https://developer.android.com/studio/releases/platform-tools>
            3. Add  to environment variables
            ```zsh
            $ echo 'export PATH=$PATH:~/.android-sdk-macosx/platform-tools/' >> ~/.bash_profile
            ``` 
            or change `.bash_profile` to the active shell config such as `zsh` config `.zshrc`

- Open Temi ADB Connection Port
    > Settings -> temi Developer Tools -> ADB Port Opening

- Connect
    ```zsh
    # Default Port : 5555
    $ adb connect ip:port

    $ adb devices # check connection

    $ adb logcat | grep D\ <TAG>

    $ adb shell

    $ adb disconnect
    ```

## APIs
- Getting Brand List
    - Method : GET
    - Parameters : BrandList_ID
    - Usage : https://cgutemi.nctu.me/BrandLists/<ID\>
- Getting Special Events & News
    - Method : GET
    - Parameters : Null
    - Usage : https://cgutemi.nctu.me/events

## Database - RedisDB
- Table [0] : Robot Utils & Status  
    - Key : S/N
        - Value : Data JSON Format

    | S/N | Status | Time | MAC_Addr | SSID | IP Addr | RSSI | Battery_Info | Charging_Status |   
    | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
    |  |  |  |  |  |  |  |  | Boolean | 

- Table [1] : Robot Status Log
    - Key : S/N
        - Field : Date, Time
            - Value : Data JSON Format

- Table [2] : 櫃位引導

    | Brand_ID | Brand_Name | Brand_URL | Brand_Logo | Brand_Description |  Brand_Location | Ask_Counter |
    | ---- | ---- | ---- | ---- | ---- | ---- | ---- | 
    |  |  |  | URL |  |  |  |

- Table [3] : 活動列表

    | Time | Activity_Name | Provider_ID | ProviderName | DM_Sources | TTL |
    | ---- | ---- | ---- | ---- | ---- | ---- | 
    |  |  |  |  | List |  |

- Table [4] : Coupon List

    | Time | Coupon_Number | Amount | TTL |
    | ---- | ---- | ---- | ---- | 

- Table [5] : User Data

    | UserID | UserName | UserPhone | UserMail |
    | ---- | ---- | ---- | ---- | 


## Andriod (On Temi)
- IDE : Android Studio (v4.0)
- Language : Java
- System Target : Android Version 6 Marshmallow (API 23)
- Screen Spec : 10.1" inches, 1920x1200(px), 224dpi >> [Officail Web Spec](https://www.robotemi.com/specs/)

#### How to send data periodly ? 

```java
// Make app running specified function in a period
// https://www.itread01.com/p/1358274.html

final int time = 1000  ; // set period time (ms)
final Handler handler = new Handler();
Runnable runnable = new Runnable(){
    @Override
    public void run() {
        // 在此處新增執行的程式碼

        // TO-DO
        // Can be a Function

        handler.postDelayed(this, time);// time ms後執行this,即runable
    }
};
handler.postDelayed(runnable, time);// 開啟定時器,time ms後執行runnable操作
```

#### Getting Temi Util Info
- Serial Number
- Current Time
- MAC Address
- IP Address
- SSID
- RSSI (Connection Strength)
- Memory
    - System Memory
        - totalMem
        - availMem
        - usedMem = totalMem - availMem
    - Runtime Memory
        - maxMemory
        - totalMemory
        - freeMemory
        - usedMemory = totalMemory - freeMemory
- Battery
    - BatteryLevel
    - isCharging

#### Push the message to MQTT Server
```java
public static void publishUtilTOMqtt(String content){ // Void function without return value (ex. string, int ...

        String broker = "tcp://ip:1883";
        String clientId = "Temi";
        MemoryPersistence persistence = new MemoryPersistence();
        String topic = "test/sub_topic"; // or "test/+" to subscribe the sub topic

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
```

## Python + MQTT + Redis
- Connect to MQTT Server & Subcribe to `Topic`
- Connect to Redis & I/O Data

## Python + Redis >> Redis Admin Manage Webpage
- Clone from github
- Setup on http://ip:8085
- Can be access from http://cgutemi.nctu.me

## Backend Server & Frontend
- NodeJS
    - Install Node & NPM(Node Package Manager)
    
#### Realtime System Monitor
- Connect to MQTT Server
- Subscribe to `Topic`
- Start a socket to communicate between `server (backend)` & `webpage (frontend)`


## Domain Name
- Apply from NCTU free domain : https://nctu.me/





### Ref
[1] https://oranwind.org/-edge-zai-ubuntu-an-zhuang-mosquitto-mqtt-broker-part-2/  
[2] https://www.digitalocean.com/community/tutorials/how-to-install-and-secure-redis-on-ubuntu-20-04  
[3] https://www.digitalocean.com/community/tutorials/how-to-install-node-js-on-ubuntu-20-04
