const { default: axios } = require('axios')
const express = require('express')
const app = express()
const path = require('path')
const port = 3000

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
app.use(express.static(__dirname + '/views'));

app.get('/', (req, res) => {

    //console.log(req.query.id,req.query.pid)
    
    axios({
        method: 'get',
        url:'https://helpmeapi-deploy.herokuapp.com/post',
        params : {
            uid: "8989898989",
            pid: "Xtsd13Axd123zXc"
        }
    }).then(response => {        
        console.log(response.data)
        const post = response.data
        if(post.result == false)
        {
            res.render('errorpage');
        }
        else{
            const time = "Confused Unga Bunga" //timeStamp will be converted to real time and date
            const date = "Also Confused Unga Bunga"
            res.render('index', { data: post, time: time ,date: date})
        }
    })
    .catch(err => console.error(err))
})


app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})


