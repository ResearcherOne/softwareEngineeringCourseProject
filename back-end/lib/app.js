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

app.get('/savescore', function (req, res) {
  mongoModule.saveScore("umut", 15, 550, 110, function(err, result){
    res.send("saved");
  });
});

app.post('/savescore', parseUrlencoded, function (req, res) {
  var newScore = request.body;
  console.log("ClientId: "+newScore.clientId.toString());
  mongoModule.saveScore("umut", 15, 550, 110, function(err, result){
    res.send("saved");
  });
});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});