const { default: axios } = require('axios')
const express = require('express')
const router = express.Router()
const path = require('path')

//for help post 
router.get('/', (req,res,next) =>{
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
            const time = "Confused Unga Bunga" //timeStamp will be converted to real time and date
            const date = "Also Confused Unga Bunga"
            res.render('help', { data: post, time: time, date: date })
        }
    })
        .catch(err => console.error(err))

})

module.exports = router