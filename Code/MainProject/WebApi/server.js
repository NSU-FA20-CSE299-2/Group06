const bodyParser = require('body-parser');
const express = require('express')
const app = express();
const postRouter = require('./routes/posts')
const PORT = process.env.PORT || 3000
const admin = require('firebase-admin')
var Firebase = require('firebase');
const fs = require('fs');



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
    databaseURL: "PLACE DATABASE URL HERE"
});

Firebase.initializeApp({

    databaseURL: "PLACE DATABASE URL HERE",
    serviceAccount: serviceAccount, 
});


var db = Firebase.database();
var usersRef = db.ref("posts");

//For viewing post by UID and PID(user)
app.get('/post', function (req, res) {

    const uid = req.query.uid
    const pid = req.query.pid

    let date_ob = new Date();
    let logdata = {
        time: date_ob.toLocaleTimeString(),
        date: date_ob.toLocaleDateString(),
        uid: uid,
        pid: pid,
        request: "user query by uid & pid",
        status: "success"
    }

    if (uid == null || pid == null) { //

        logdata.status = "failed"
        res.json({ message: "Error: Invalid query parameter.", result: false });

    }
    else if (uid.length < 5 || pid.length < 5) //firebase uid length: 20
    {
        logdata.status = "failed"
        res.json({ message: "Error: Invalid UID/PID.", result: false });
    }
    else {
        usersRef.child(uid).child(pid).once("value", function (snapshot) {
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



app.listen(PORT, ()=>console.log("Server is running on PORT " + PORT))

