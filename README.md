# Temi-ROS

## Connect to Temi
- Install Android Studio
- Install ADB (Android Debug Bridge)
    - Windows
        1. Download SDK Platform-Tools
    - macOS
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

## Database - RedisDB
- Table [0] : Robot Utils & Status  

    | Time | Device_ID | IP_Addr | MAC_Addr | Robox_OS | Launcher_OS | S/N |    Battery_Info | Charging_Status |   
    | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | 
    |  |  |  |  |  |  |  |  | Boolean | 

- Table [1] : 櫃位引導

    | Brand_ID | Brand_Name | Brand_URL | Brand_Logo | Brand_Description |  Brand_Location | Ask_Counter |
    | ---- | ---- | ---- | ---- | ---- | ---- | ---- | 
    |  |  |  | URL |  |  |  |

- Table [2] : 活動列表

    | Time | Activity_Name | Provider_ID | ProviderName | DM_Sources | TTL |
    | ---- | ---- | ---- | ---- | ---- | ---- | 
    |  |  |  |  | List |  |

- Table [3] : Coupon List

    | Time | Coupon_Number | Amount | TTL |
    | ---- | ---- | ---- | ---- | 

- Table [4] : User Data

    | UserID | UserName | UserPhone | UserMail |
    | ---- | ---- | ---- | ---- | 