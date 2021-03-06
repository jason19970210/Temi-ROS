// Safari : Missing popper.min.js.map
// Safari : Missing bootstrap.min.js.map
// Solution : https://www.it-swarm.dev/zh/bootstrap-4/webpack安装bootstrap缺少popperjsmap/835338106/

//const https = require('https');
const fs = require('fs');
const cors = require('cors')
const bodyParser = require('body-parser')
const mqtt = require('mqtt')
const redis = require('redis')
const io = require('socket.io')
const express = require('express')
const https = require('https');
const app = express()


//app.use(bodyParser.json());
//app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.json());
app.use(express.static('www'))
// https://github.com/expressjs/cors
app.use(cors())    // Enable CROS 跨域請求
app.use (function (req, res, next) {
        if (req.secure) {
                // request was via https, so do no special handling
                next();
        } else {
                // request was via http, so redirect to https
                res.redirect('https://' + req.headers.host + req.url);
        }
});


app.set('view engine', 'ejs')

const web_host = '0.0.0.0'
const web_port = 443; //3000
const mqtt_host = 'tcp://120.126.16.92'
const mqtt_topic = 'test/+'


//  Domain Management
//  https://nctu.me/domains/

//  轉埠 nodejs port 3000 to ubuntu port 80
//  https://medium.com/@dkxmonster/%E6%8A%80%E8%A1%93%E5%88%86%E4%BA%AB-%E5%A6%82%E4%BD%95%E8%A7%A3%E6%B1%BAnode-js-port-3000%E7%9A%84%E5%95%8F%E9%A1%8C-b2a425308a3d

//  開機自動化轉埠
//  https://kawsing.gitbook.io/opensystem/docker-cong-an-zhuang-dao-ying-yong-ru-men-pian/qi-yong-host-ji-de-etcrc.local

const privateKey = fs.readFileSync('/etc/letsencrypt/live/cgutemi.nctu.me/privkey.pem', 'utf8');
const certificate = fs.readFileSync('/etc/letsencrypt/live/cgutemi.nctu.me/cert.pem', 'utf8');
const ca = fs.readFileSync('/etc/letsencrypt/live/cgutemi.nctu.me/chain.pem', 'utf8');

const credentials = {
	key: privateKey,
	cert: certificate,
	ca: ca
};

//var web_server = app.listen(web_port, web_host, () => console.log("Listening on " + web_host + ":" + web_port + "\n" + "CROS Enabled"))
var web_server = https.createServer(credentials, app).listen(web_port, web_host, () => console.log("Listening on " + web_host + ":" + web_port + "\n" + "CROS Enabled"))


// https://stackoverflow.com/questions/7450940/automatic-https-connection-redirect-with-node-js-express
// Redirect from http port 80 to https
var http = require('http');
http.createServer(function (req, res) {
    res.writeHead(301, { "Location": "https://" + req.headers['host'] + req.url });
    res.end();
}).listen(80);



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
    //console.log("Connected to socket")
    
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
var redis_client = redis.createClient(port=55555)
//console.log(redis_client)

redis_client.on("error", function(error) {
    console.error("Redis Connection Error : " + error);
});


// Routes

app.get('/', (req, res) => {
    //console.log("GET / ")
    res.render('index')   
})


// API
// =====================
// GET Method
app.get('/offline/:sn', function(req, res){
   
    const sn = req.params.sn
    //console.log("/offline/" + sn + " is requested !!")
    redis_client.select(0)
    redis_client.get(sn, (error, result) => {
        if (error) {
            console.log("Redis Get " + sn + " : " + error)
            throw error
        }
        if(result == null){
            //result = sn + ' is offline'
            result = {}
            result[sn] = "Offline"
        }
        //console.log('GET result -> ' + result);
        res.json(result)
        //console.log(typeof(result))    
    })
})


app.get('/BrandLists/:brand_type_id', function(req,res){
    var list1 = []
    const brand_type_id = req.params.brand_type_id
    //console.log(brand_type_id)
    redis_client.select(2)

    // https://gist.github.com/atree/4557289
    redis_client.keys('*',(error, reply) => {
        // console.log(result) // >> array
        var i = 0;
        reply.forEach(function(item){
            // console.log(item) // items in array
            redis_client.get(item, (error, result)=>{
                i++;
                //console.log(result)
                json_result = JSON.parse(result)
                //console.log(json_result['BrandTypeID'])
                if(json_result['BrandTypeID']==brand_type_id){
                    //console.log(json_result['BrandTypeID'])
                    list1.push(json_result)
                }
                if(i == reply.length){
                    //console.log(list1.length)
                    //console.log("End")
                    var output = {}
                    output[brand_type_id] = list1
                    //console.log(output['1'])
                    //console.log(list1.toString())
                    //console.log(JSON.stringify(output))
                    res.send(output)
                }
            })
        })
    })
    //res.send(brand_type_id)
})


app.get('/events', (req, res)=>{

    var list1 = []
    redis_client.select(3)
    redis_client.keys('*', (error, reply)=>{
    
        var i = 0
        reply.forEach(function(item){
            redis_client.get(item, (error, result)=>{
                i++;
                json_result = JSON.parse(result)
                list1.push(json_result)

                if(i == reply.length){
                    var output = {}
                    output['events'] = list1
                    res.send(output)
                }
            })
        })
    })
})


// =====================
// POST Method
// Express Post Method get query from client & send response back to client


// IMPORTANT
// Always KEEP this as the last route
app.use(function (req,res,next){
    res.status(404).render('404')
})
