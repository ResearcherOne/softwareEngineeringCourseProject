/*
  Should i really create special basic CRUD module for test (or other) purposes.
    Yes, and i should assume that it works correct, just as i assume "should" library works correctly
      so that i do not write tests for the library. Instead, authors of the module write test for it.
    I should write a service test that will test connection with mlab.com
*/
var should      = require('should');
var 
var mongoModule = require('../../lib/mongoModule.js');

var mongoUrl        = "mongodb://birkan:yoyo980*@ds127958.mlab.com:27958/software-engineering-course-project";
var testCollection  = "test-collection-"+(new Date().getTime()).toString();

describe('mongoModule.js', function(){
  before(function(){
    //create the collection
    //insert necessary data
    //test initializning module
  });
  describe('saveScore()', function(){
    
  });
  describe('getTopTenList()', function(){
    
  });
  after(function(){
    //Drop the collection.
  });
});