// Safari : Missing popper.min.js.map
// Safari : Missing bootstrap.min.js.map
// Solution : https://www.it-swarm.dev/zh/bootstrap-4/webpack安装bootstrap缺少popperjsmap/835338106/

const cors = require('cors')
const bodyParser = require('body-parser')
const mqtt = require('mqtt')
const redis = require('redis')
const io = require('socket.io')
const express = require('express')
const app = express()

app.use(express.static('www'))
// https://github.com/expressjs/cors
app.use(cors())    // Enable CROS 跨域請求
app.set('view engine', 'ejs')

const web_host = '0.0.0.0'
const web_port = 80; //3000
const mqtt_host = 'tcp://120.126.16.92'
const mqtt_topic = 'test/+'

//app.use(function (req,res,next){
//    console.log("at 404")
//    res.status(404).render('404');
//});

//  Domain Management
//  https://nctu.me/domains/

//  轉埠 nodejs port 3000 to ubuntu port 80
//  https://medium.com/@dkxmonster/%E6%8A%80%E8%A1%93%E5%88%86%E4%BA%AB-%E5%A6%82%E4%BD%95%E8%A7%A3%E6%B1%BAnode-js-port-3000%E7%9A%84%E5%95%8F%E9%A1%8C-b2a425308a3d

//  開機自動化轉埠
//  https://kawsing.gitbook.io/opensystem/docker-cong-an-zhuang-dao-ying-yong-ru-men-pian/qi-yong-host-ji-de-etcrc.local

var web_server = app.listen(web_port, web_host, () => console.log("Listening on " + web_host + ":" + web_port + "\n" + "CROS Enabled"))

var mqtt_client = mqtt.connect(mqtt_host)
var sio = io.listen(web_server)

mqtt_client.on('connect', function(){
    //console.log("Connect to MQTT Server")
    mqtt_client.subscribe(mqtt_topic)    
})

var fake_data = '{"key":"value"}'

function sendData(socket){
    socket.emit('mqtt', {'msg': fake_data})
}
//console.log(typeof(fake_data))
sio.on('connection', function(socket){
    
    var i;
    for(i = 0; i < 7; i++){
        sendData(socket);
    }
    //socket.emit('mqtt', {'msg': fake_data})
    mqtt_client.on('message', function(topic, msg){
        //console.log('Get topic: ' + topic + 'with msg: ' + msg.toString())
        socket.emit('mqtt', {'msg': msg.toString()})
    })
    
    
    setInterval( () => sendData(socket), 1000);
})

// Connect to Redis Server
var redis_client = redis.createClient(db=0)

redis_client.on("error", function(error) {
    console.error("Redis Connection Error : " + error);
});


app.get('/', (req, res) => {
    console.log("GET / ")
    res.render('index')    
})


// API

// =====================
// GET Method
app.get('/offline/:sn', function(req, res){
   
    const sn = req.params.sn
    //console.log("/offline/" + sn + " is requested !!")
    redis_client.get(sn, (error, result) => {
        if (error) {
            console.log("Redis Get " + sn + " : " + error)
            throw error
        }
        if(result == null){
            result = sn + ' is offline'
        }
        console.log('GET result -> ' + result);
        res.json(result)
        //console.log(typeof(result))    
    })
})


// =====================
// POST Method
// Express Post Method get query from client & send response back to client
app.post('/api', (req, res) => {
    console.log(req.query)
    res.send("OK")
})



// IMPORTANT
// Always KEEP this as the last route
app.use(function (req,res,next){
    res.status(404).render('404')
})
