const { default: axios } = require('axios')
const express = require('express')
const app = express()
const postRoute = require('./help')
const path = require('path')
const port = process.env.PORT || 3000


app.set('views', path.join(__dirname, 'views/pages'));
app.set('view engine', 'jade');
app.use(express.static(__dirname + '/views'));


//help post route
app.use('/help', postRoute)


//home page router
app.get('/', (req, res) => {
    res.render('index')
})


//ajax request handler
app.get('/trigger', (req, res) => {
  const pid = req.query.pid
  axios({
    method: 'get',
    url: 'https://helpmeapi-deploy.herokuapp.com/trigger',
    params: {pid: pid}
  }).then(response =>
    {
      //console.log(response.data)
      let addressData = response.data
      if (addressData.status != "FAILED")
      {
        res.send(addressData.address);
      }
    })
})


app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})


