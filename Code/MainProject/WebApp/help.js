const { default: axios } = require('axios')
const express = require('express')
const router = express.Router()
const path = require('path')

//for help post 
router.get('/', (req,res,next) =>{
    console.log(req.query)

    axios({
        method: 'get',
        url: 'https://helpmeapi-deploy.herokuapp.com/post',
        params: {
            uid: "8989898989",
            pid: "Xtsd13Axd123zXc"
        }
    }).then(response => {
        console.log(response.data)
        const post = response.data
        if (post.result == false) {
            res.render('errorpage');
        }
        else {
            //timeStamp will be converted to real time and date
            const time = timeStampToTimeData(response.data.timeStamp).time 
            const date = timeStampToTimeData(response.data.timeStamp).date
            res.render('help', { data: post, time: time, date: date })
        }
    })
        .catch(err => console.error(err))
})


function timeStampToTimeData(unix_timestamp)
{
     var date = new Date(unix_timestamp * 1000);
    var hours = date.getHours();
    var minutes = "0" + date.getMinutes();
    var seconds = "0" + date.getSeconds();
    var formattedTime = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
    var date = date.getDay() + "-" + date.getMonth() + "-" + date.getFullYear();
    return { time: formattedTime, date: date}
}


module.exports = router