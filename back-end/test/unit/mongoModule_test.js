/*
  Should i really create special basic CRUD module for test (or other) purposes.
    Yes, and i should assume that it works correct, just as i assume "should" library works correctly
      so that i do not write tests for the library. Instead, authors of the module write tests for it.
  I should write a service test that will test connection with mlab.com
  I can not test whether initializing mongo connection at first step with mocha because
    raising error as if it were one of the test case means there is someting wrong
    with mongoModule not test case code. Instead i should kind of stop execution
    of the test case by raising kind of error.
  Instead of creating single mongoModule and using a mongoWrapper with it I can use mongoWrapper and various
    models that does the job using mongoWrapper. For example "models/comment.js" would interact with
    db for saving user comments such as saveComment(), deleteComment(), etc. "models/somethingElse.js"
    would do something else. Such structure would make database code more modular and easy to read.
  I THOUGHT mlab.com creates an empty collection if you try to insert an object to nonexistent collection
    BUT it appears to be like it is not the case. It actually inserts the document you pass it at the first
    create call.
    FACEPALM. It wasn't that, it was creating an empty collection. Instead, it was creating a collection
      and inserting the document i sent. However, i was deleting entire collection elements right after
      the insert operation because, beforeEach was calling the clearCollection function.
  this.timeout(5000); //Override 2000ms default mocha timeout for current "before", "beforeEach", "it", etc.
*/
var should      = require('should');
var MongoCrud   = require('mongo-crud-layer');
var mongoModule = require('../../lib/mongoModule.js');
var async       = require('async');

var mongoUrl        = "mongodb://birkan:yoyo980*@ds127958.mlab.com:27958/software-engineering-course-project";
var testCollection  = "test-collection-"+(new Date().getTime()).toString();
var mongocrud;

function generateRandomInt (low, high) {
    return Math.floor(Math.random() * (high - low) + low);
}

function clearCollection(callback) {
  mongocrud.delete({}, testCollection, callback);
}
/*
    populateRandomScoreObjects(5,function(err, isSucceed){ //Just for experimenting, i'll populate db with 5 objects.
      if (!err) {
        console.log("populated 5 score obj");
      } else {
        throw new Error(err);
      }
    });
*/
function populateRandomScoreObjects(objectCount,callback){//Here, the point is to call the callback when all funcs are completed.
  var randomInt = generateRandomInt (0, 99999);
  var completedCallCount = 0;
  for (var i = 0; i < objectCount; i++) {
    var j = i + randomInt;
    var scoreObj = {
        "userName": "birkan"+j.toString(),
        "score": j,
        "dateInMillis": new Date().getTime(),
        "clientId": j
    };
    mongocrud.create(scoreObj, testCollection, function(err, id) {
      var isSucceed = false;
      if(err) {
        callback(err, isSucceed);
      } else {
        completedCallCount ++;
        if (completedCallCount == objectCount) {
          isSucceed = true;
          callback(null, isSucceed);
        }
      }
    });
  }
}

describe('mongoModule.js', function(){
  before(function(done) {
    mongocrud = new MongoCrud(mongoUrl);
    mongoModule.initializeModule(mongoUrl, testCollection);
    done();
  });

  beforeEach(function(done) {
      clearCollection(function(err, result){
        if(!err){
          done();
        } else {
          throw new Error(err);
        }
      });
    });
  
  describe('saveScore(userName, score, dateInMillis, clientId, callback)', function(){
    var userName      = "birkan";
    var score         = 500;
    var dateInMillis  = new Date().getTime();
    var clientId      = 123123213;
    it("should insert score object", function(){
      mongoModule.saveScore(userName, score, dateInMillis, clientId, function(err, result){
        mongocrud.read({ "clientId": clientId }, testCollection, function(err, doc) {
            console.log(doc); // { _id: 55617c9226d7023b19edcdd1, name: "Athyrion" } 
            should.not.exist(err);
        });
      });
    });
    it("should return err object with 'invalid argument' message if userName with empty string entered", function(){
      mongoModule.saveScore("", score, dateInMillis, clientId, function(err, result){
            should.exist(err);
            err.message.should.be.equal("invalid argument");
      });
    });
    it("should return err object with 'invalid argument' message on any null argument", function(){ //Is calling saveScore four times for each null is OK or not?
      var clientId_      = clientId;
      mongoModule.saveScore(null, score, dateInMillis, clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
      mongoModule.saveScore(userName, null, dateInMillis, ++clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
      mongoModule.saveScore(userName, score, null, ++clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
      mongoModule.saveScore(userName, score, dateInMillis, null, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
    });
    it("should return err object with 'invalid argument' message on any type mismatch", function(){
      var clientId_      = clientId;
      mongoModule.saveScore(5, score, dateInMillis, clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
      mongoModule.saveScore(userName, "invalid_score", dateInMillis, ++clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
      mongoModule.saveScore(userName, score, "invalid_dateInMillis", ++clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
      mongoModule.saveScore(userName, score, dateInMillis, "invalid_clientId", function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
    });
    it("should return err object with 'invalid argument' on negative score, dateInMillis or clientId", function(){
      var clientId_ = clientId;
      mongoModule.saveScore(userName, -1, dateInMillis, clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
      mongoModule.saveScore(userName, score, -2, ++clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
      mongoModule.saveScore(userName, score, dateInMillis, -5, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
      });
    });
  });
  describe('getTopTenList()', function(){
    var mockScoreObj = {
      "userName": "birkan",
      "score": 1000,
      "dateInMillis": new Date().getTime(),
      "clientId": 999
    }
        it("should return an array with ten object if collection document count exceeds ten", function(){
          //load 11 element
        });
        it("should return an array with x object if collection document count x <= 10", function(){
          //load 5 element
        });
    //insertViaWrapper
      //it should get top ten list
  });
  describe('clearAllScores()', function(){
    //insertViaWrapper
      //it should clear all scores
        //findViaWrapper
  });
  after(function(){
    //dropCollectionViaWrapper
      //close connection of wrapper
      //close connection of mongoModule
  });
});