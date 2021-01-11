const bodyParser = require('body-parser');
const express = require('express')
const app = express();
const PORT = process.env.PORT || 3000
const admin = require('firebase-admin')
var Firebase = require('firebase');
const fs = require('fs');
const { default: axios } = require('axios')
const geoCodingRoute = require('./geocoding')


var serviceAccount = require("./api-testing-b6ee5-firebase-adminsdk-zu5i1-53b9ce1a66.json");

app.use(function (req, res, next) { //allow cross origin requests
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Methods", "POST, PUT, OPTIONS, DELETE, GET");
    res.header("Access-Control-Max-Age", "3600");
    res.header("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    next();
});

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://api-testing-b6ee5-default-rtdb.firebaseio.com"
});

Firebase.initializeApp({

    databaseURL: "https://api-testing-b6ee5-default-rtdb.firebaseio.com/",

    serviceAccount: serviceAccount, //downloaded from Firebase Console

});

app.use('/geocode', geoCodingRoute)


// app.use('/', postRouter)
var db = Firebase.database();
var usersRef = db.ref("helpPosts");


//For viewing post by PID(user)
app.get('/post', function (req, res) {

    const pid = req.query.pid
    let date_ob = new Date();
    let logdata = {
        time: date_ob.toLocaleTimeString(),
        date: date_ob.toLocaleDateString(),
        pid: pid,
        request: "user query by pid",
        status: "success"
    }

    if ( pid == null) { //
        logdata.status = "failed"
        res.json({ message: "Error: Invalid query parameter.", result: false });
    }
    else if ( pid.length < 5) //firebase pid length: 20
    {
        logdata.status = "failed"
        res.json({ message: "Error: Invalid PID.", result: false });
    }
    else {
        usersRef.child(pid).once("value", function (snapshot) {
            if (snapshot.val() == null) {

                res.json({ message: "Error: No data found", "result": false });

            } else {

                res.json(snapshot.val());
            }
        });
    }

    fs.appendFile('logs.txt', JSON.stringify(logdata) + "\n", function (err) {
        if (err) throw err;
    });

});



//API Home
app.get('/', (req,res)=>{
    res.send("<center><h1>Help Post API Home</h1></center>")
})




//View API Logs
app.get('/devlog', (req, res) => {
    fs.readFile('logs.txt', (e, data) => {
        if (e) throw e;
        let htmlHeader = "<h1>API Logs (Dev) </h1>\n"
        res.send(htmlHeader+data.toString());
    });
})


//Database trigger route
app.get('/trigger', (req, res,next) => {
    const pid = req.query.pid

    if (pid == null || pid.length < 5 ) { //
        res.json({ status:"FAILED",message: "Error: Invalid query parameter(pid => null/value too short).", result: false })
        next()
    }
    else {
        usersRef.child(pid).once("value", function (snapshot) {
            if (snapshot.val() == null) {
                res.json({ message: "Error: No data found", "result": false })
                next()
            } else {
                const post = snapshot.val()
                if (post.address == null || post.address == "") {
                    var lat = post.latitude
                    var long = post.longitude
                    var qValueParam = lat + "+" + long
                    var apiKeyValuParam = "edf1ffbd06444d9883e6005fdb80f7f3"                    
                    axios.get(
                         "https://api.opencagedata.com/geocode/v1/json?q=" + qValueParam + "&key=" + apiKeyValuParam
                    ).then(response => {
                        const apiData = response.data
                        var address = "Cannot retrive address"
                        if (apiData.results!=null)
                        {
                            address = apiData.results[0].formatted
                        }
                        usersRef.child(pid).update({ address:address})
                        res.json({ status: "OK", message: "Address updated" })
                        next()
                    }).catch(err => {
                        console.log(err)
                        var address = "Cannot retrive address"
                        usersRef.child(pid).update({ address: address })
                        res.json({status:"FAILED", message:"Could not update address"})
                        next()
                    })
                }
                else
                {
                    res.json({ status: "FAILED", message: "Not requied" })
                    next()
                }
            }
        });
    }

})


app.listen(PORT, ()=>console.log("Server is running on PORT " + PORT))

