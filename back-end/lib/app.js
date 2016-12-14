var express = require('express');
var app = express();
var mongoModule = require('./mongoModule.js');

var bodyParser = require('body-parser');
var parseUrlencoded = bodyParser.urlencoded({extended: false});

mongoModule.initializeModule("mongodb://birkan:yoyo980*@ds127958.mlab.com:27958/software-engineering-course-project","testColl");

app.get('/toptenlist.json', function (req, res) {
  mongoModule.getTopTenList(function(err, result){
    res.json({"toptenlist":result});
  });
});
// userName, score, dateInMillis, clientId
app.get('/savescore/:scoredata', function (req, res) { // .../savescore/username,score,clientId
  var scoredata       = req.params.scoredata;
  var receivedParams  = scoredata.split(",");
  console.log("username: "+receivedParams[0]+" score: "+ receivedParams[1]+" clietnId: "+receivedParams[2]);
  mongoModule.saveScore(receivedParams[0], parseInt(receivedParams[1]), new Date().getTime(), parseInt(receivedParams[2]), function(err, result){
    res.send("username: "+receivedParams[0]+" score: "+ receivedParams[1]+" clietnId: "+receivedParams[2]);
  });
});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});