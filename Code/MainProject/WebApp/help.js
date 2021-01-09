const { default: axios } = require('axios')
const express = require('express')
const router = express.Router()
const path = require('path')

//for help post 
router.get('/', (req,res,next) =>{
    
    const uid = req.query.uid
    const pid = req.query.pid

    if (uid == null || pid == null 
        || uid.length < 5 || pid.length < 5) {
        res.render('errorpage');
    }
    else{
        axios({
            method: 'get',
            url: 'https://helpmeapi-deploy.herokuapp.com/post',
            params: {
                uid: uid,
                pid: pid
            }
        }).then(response => {
            //console.log(response.data)
            const post = response.data
            if (post.result == false) {
                res.render('errorpage');
            }
            else {
                //timeStamp will be converted to real time and date
                const time = timeStampToTimeData(response.data.timeStamp).time
                const date = timeStampToTimeData(response.data.timeStamp).date
                const maplink = `https://www.google.com/maps/search/?api=1&query=${post.latitude},${post.longitude}`
                
                res.render('help-old', { data: post, time: time, date: date, maplink: maplink })
            }
        })
            .catch(err => console.error(err))
    }
})


//unix timestamp converter
function timeStampToTimeData(timestamp)
{
    var date = new Date(timestamp*1000);
    var hours = date.getHours();
    var minutes = "0" + date.getMinutes();
    var seconds = "0" + date.getSeconds();
    var formattedTime = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
    var date = date.getDay() + "-" + date.getMonth() + "-" + date.getFullYear();
    return { time: formattedTime, date: date}
}


module.exports = router