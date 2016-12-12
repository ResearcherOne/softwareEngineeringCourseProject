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
*/
var should      = require('should');
var MongoCrud   = require('mongo-crud-layer');
var mongoModule = require('../../lib/mongoModule.js');

var mongoUrl        = "mongodb://birkan:yoyo980*@ds127958.mlab.com:27958/software-engineering-course-project";
var testCollection  = "test-collection-"+(new Date().getTime()).toString();

var mockObj = {"user": "birkan", "date":12312312};
var mongocrud;


function clearCollection(callback) {
  mongocrud.delete({}, testCollection, callback);
}

describe('mongoModule.js', function(){
  before(function(done) {
    mongocrud = new MongoCrud(mongoUrl);
    mongocrud.create({}, testCollection, function(err, id) { //Create an empty collection.
        if (!err) {
          //test initializning module
          
          done();
        } else {
          throw new Error(err);
        }
    });
  });
  beforeEach(function(done) {
    clearCollection(function(err, result){
      //if everything went smootly
        done();
    });
  });
  describe('saveScore()', function(){
    it("should create score object", function(){
      
    });
    //it should save score
      //findViaWrapper
  });
  describe('getTopTenList()', function(){
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