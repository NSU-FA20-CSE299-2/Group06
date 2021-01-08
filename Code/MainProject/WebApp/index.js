const { default: axios } = require('axios')
const express = require('express')
const app = express()
const postRoute = require('./help')
const path = require('path')
const port = 3000

app.set('views', path.join(__dirname, 'views/pages'));
app.set('view engine', 'jade');
app.use(express.static(__dirname + '/views'));

//help post route
app.use('/post', postRoute)

//home page router
app.get('/', (req, res) => {
    //console.log(req.query.id,req.query.pid)
    res.send("App Lander")
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})


