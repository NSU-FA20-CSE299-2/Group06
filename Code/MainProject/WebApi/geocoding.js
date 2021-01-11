const { default: axios } = require('axios')
const { response } = require('express')
const express = require('express')
const router = express.Router()
const path = require('path')

router.get('/',(req,res,next)=>{
    const lat = req.query.lat
    const long = req.query.long
    const qValueParam = lat + "+" + long
    const apiKeyValuParam = "edf1ffbd06444d9883e6005fdb80f7f3"
    axios({
        method: 'get',
        //FQL query cannot be done using Axios params (custom url provided)
        url: "https://api.opencagedata.com/geocode/v1/json?q="+qValueParam+"&key="+apiKeyValuParam,
    }).then(response=>
        {
            const apiData = response.data
            res.json({
                address: apiData.results[0].formatted,
                staus: apiData.status.code, 
                message: apiData.status.message,
                //fullGeoData: apiData
                })
                next()
            }).catch(err=>
                {
                res.json({
                    address: "Not found",
                    staus: 404,
                    message: "FAILED",
                    //fullGeoData: apiData
                })
                    console.error(err)
                    next()
    })
})

module.exports = router