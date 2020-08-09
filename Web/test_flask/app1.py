import eventlet
import redis
from flask import Flask,render_template
#import paho.mqtt.client as mqtt
from flask_mqtt import Mqtt
from flask_socketio import SocketIO

eventlet.monkey_patch()

app = Flask(__name__)

app.config['MQTT_BROKER_URL'] = '120.126.18.94'
app.config['MQTT_BROKER_PORT'] = 1883
#app.config['MQTT_REFRESH_TIME'] = 1.0

mqtt = Mqtt(app)
socketio = SocketIO(app)

@mqtt.on_connect()
def handle_connect(client, userdata, flags, rc):
    mqtt.subscribe('test/+')

@mqtt.on_message()
def handle_mqtt_message(client, userdata, message):
    data = dict(
        topic=message.topic,
        payload=message.payload.decode()
    )
    # emit a mqtt_message event to the socket containing the message data
    socketio.emit('mqtt_message', data=data)


@app.route('/')
def home():
    return render_template('index.html')


if __name__ == '__main__':
    #app.run(debug=True)
    socketio.run(app, host='localhost', port=5000, use_reloader=True, debug=True)
    