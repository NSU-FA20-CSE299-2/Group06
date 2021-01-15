const { default: axios } = require('axios')
const express = require('express')
const router = express.Router()
const path = require('path')


//for help post 
router.get('/', (req,res,next) =>{
    const pid = req.query.pid
    
    if ( pid == null || pid.length < 5) {
        res.render('errorpage');
    }
    else{
        axios({
            method: 'get',
            url: 'https://helpmeapi-deploy.herokuapp.com/post',
            params: {
                pid: pid
            }
        }).then(response => {
            //console.log(response.data)
            let post = response.data
            if (post.result == false) {
                res.render('errorpage');
            }
            else {
                
                if (post.photoURL == "" || post.photoURL == null)
                {
                    post.photoURL = "../images/no-image.png"
                }
                post.author = post.author.charAt(0).toUpperCase() + post.author.substring(1).toLowerCase()
                
                const time = post.timeStamp.split(' ')[1]
                const date = post.timeStamp.split(' ')[0]
                const maplink = `https://www.google.com/maps/search/?api=1&query=${post.latitude},${post.longitude}`
                
                res.render('help', { data: post, time: time, date: date, maplink: maplink })
            }
        })
            .catch(err => console.error(err))
    }
})

module.exports = router