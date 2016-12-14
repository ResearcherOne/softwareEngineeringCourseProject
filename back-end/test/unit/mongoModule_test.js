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
  Normally, i was supposed to go with TDD approach where i would be writing test cases step by step and
    implementing in scynchornously however this time, i wrote (probably not) all test cases at once because
    i was supposed to give those tests to my teammate so that he would be implementing the functions according
    to tests. Anyways, there is no reason for not to contniue to development with actualy TDD approach.
  While "before" function runs before any other "it" function, it does not gurantee to first run stuff that are
    inside "describe" but outside "it".
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

function getScoreCollectionAsArray(callback) {
  mongocrud.readAll(testCollection, function(err, docs) {
    callback(err, docs);
  });
}

function populateRandomScoreObjects(objectCount,callback){//Here, the point is to call the callback when all funcs are completed.
  var randomInt = generateRandomInt (-1000, 1000);
  var completedCallCount = 0;
  for (var i = 0; i < objectCount; i++) {
    var j = i + randomInt;
    var absJ = Math.abs(j);
    var scoreObj = {
        "userName": "birkan"+absJ.toString(),
        "score": j,
        "dateInMillis": new Date().getTime(),
        "clientId": absJ
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
    it("should insert score object", function(done){
      mongoModule.saveScore(userName, score, dateInMillis, clientId, function(err, result){
        mongocrud.read({ "clientId": clientId }, testCollection, function(err, doc) {
            should.not.exist(err);
            done();
        });
      });
    });
    it("should return err object with 'invalid argument' message if userName with empty string entered", function(done){
      mongoModule.saveScore("", score, dateInMillis, clientId, function(err, result){
            should.exist(err);
            err.message.should.be.equal("invalid argument");
            done();
      });
    });
    it("should return err object with 'invalid argument' message on any null argument", function(done){ //Is calling saveScore four times for each null is OK or not?
      var clientId_      = clientId;
      mongoModule.saveScore(null, score, dateInMillis, clientId_, function(err, result){
        should.exist(err);
        err.message.should.be.equal("invalid argument");
        mongoModule.saveScore(userName, null, dateInMillis, ++clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
          mongoModule.saveScore(userName, score, null, ++clientId_, function(err, result){
            should.exist(err);
            err.message.should.be.equal("invalid argument");
            mongoModule.saveScore(userName, score, dateInMillis, null, function(err, result){
              should.exist(err);
              err.message.should.be.equal("invalid argument");
              done();
            });
          });
        });
      });
      });
    it("should return err object with 'invalid argument' message on any type mismatch", function(done){
      var clientId_      = clientId;
      mongoModule.saveScore(5, score, dateInMillis, clientId_, function(err, result){
        should.exist(err);
        err.message.should.be.equal("invalid argument");
        mongoModule.saveScore(userName, "invalid_score", dateInMillis, ++clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
          mongoModule.saveScore(userName, score, "invalid_dateInMillis", ++clientId_, function(err, result){
            should.exist(err);
            err.message.should.be.equal("invalid argument");
            mongoModule.saveScore(userName, score, dateInMillis, "invalid_clientId", function(err, result){
              should.exist(err);
              err.message.should.be.equal("invalid argument");
              done();
            });
          });
        });
      });
    });
    it("should return err object with 'invalid argument' on negative score, dateInMillis or clientId", function(done){
      var clientId_ = clientId;
      mongoModule.saveScore(userName, -1, dateInMillis, clientId_, function(err, result){
        should.exist(err);
        err.message.should.be.equal("invalid argument");
        mongoModule.saveScore(userName, score, -2, ++clientId_, function(err, result){
          should.exist(err);
          err.message.should.be.equal("invalid argument");
          mongoModule.saveScore(userName, score, dateInMillis, -5, function(err, result){
            should.exist(err);
            err.message.should.be.equal("invalid argument");
            done();
          });
        });
      });
    });
  });
  describe('getTopTenList(callback)', function(){
    var mockScoreObj = {
      "userName": "birkan",
      "score": 1000,
      "dateInMillis": new Date().getTime(),
      "clientId": 999
    }
    it("should return an array with ten object if collection document count exceeds ten", function(done){
      //this.timeout(5000);
      populateRandomScoreObjects(11,function(err, isSucceed){
        if (!err) {
          mongoModule.getTopTenList(function(err, result){
            should.not.exist(err);
            should.exist(result);
            result.should.be.an.Array();
            result.length.should.be.equal(10);
            done();
          });
        } else {
          throw new Error(err);
        }
      });
    });
    it("should return an array with x object if collection document count x <= 10", function(done){
      populateRandomScoreObjects(1,function(err, isSucceed){
        if (!err) {
          mongoModule.getTopTenList(function(err, result){
            should.not.exist(err);
            should.exist(result);
            result.should.be.an.Array();
            result.length.should.be.equal(1);
            done();
          });
        } else {
          throw new Error(err);
        }
      });
    });
    it("should return an sorted array with top score start at index 0", function(done){
      populateRandomScoreObjects(3,function(err, isSucceed){
        if (!err) {
          mongoModule.getTopTenList(function(err, result){
            should.not.exist(err);
            should.exist(result);
            result.should.be.an.Array();
            result.length.should.be.equal(3);
            result[1].score.should.be.belowOrEqual(result[0].score);
            result[2].score.should.be.belowOrEqual(result[1].score);
            done();
          });
        } else {
          throw new Error(err);
        }
      });
    });
  });
  describe('clearAllScores(callback)', function(){
    it("should return array with zero length", function(done){
      populateRandomScoreObjects(3,function(err, isSucceed){
        if (!err) {
          mongoModule.clearAllScores(function(err){
            should.not.exist(err);
            getScoreCollectionAsArray(function(err, docs) {
              if (!err) {
                docs.length.should.be.equal(0);
                done();
              } else {
                throw new Error(err);
              }
            });
          });
        } else {
          throw new Error(err);
        }
      });
    });
  });
  after(function(){
    //dropCollectionViaWrapper
      //close connection of wrapper
      //close connection of mongoModule
  });
});