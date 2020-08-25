import paho.mqtt.client as mqtt
import json, time
import redis


## Redis Connection

r0 = redis.StrictRedis(host='120.126.16.92', port=55555, db=0)
r1 = redis.StrictRedis(host='120.126.16.92', port=55555, db=1)
#r.set("key", "value")
#print(r.get("key"))
#time.sleep(20)
#print(r.get("key"))



## MQTT Connection
# 當地端程式連線伺服器得到回應時，要做的動作
def on_connect(client, userdata, flags, rc):
    # 將訂閱主題寫在on_connet中
    # 如果我們失去連線或重新連線時
    # 地端程式將會重新訂閱
    # client.subscribe("Try/MQTT")
    print("Connected to mqtt server")
    client.subscribe("test/+")

# 當接收到從伺服器發送的訊息時要進行的動作
def on_message(client, userdata, msg):
    # 轉換編碼utf-8才看得懂中文
    message = msg.payload.decode("utf-8")
    #print(msg.topic + "\n" + message)
    params = json.loads(message)
    #print(params) #> <class 'dict'>
    for i in params:
        #print(params[i])
        value = json.dumps(params[i])
        datetime = json.dumps(params[i]['Time'])[:-2].replace(":", ".").replace("\"", " ")
        r0.set(i, value)
        r1.set(i+":"+datetime, value)


# 連線設定
# 初始化地端程式
client = mqtt.Client()

# 設定連線的動作
client.on_connect = on_connect

# 設定接收訊息的動作
client.on_message = on_message

# 設定登入帳號密碼
# client.username_pw_set("try","xxxx")

# 設定連線資訊(IP, Port, 連線時間)
# MQTT Client
client.connect("120.126.16.92", 1883)

# 開始連線，執行設定的動作和處理重新連線問題
# 也可以手動使用其他loop函式來進行連接
client.loop_forever()
