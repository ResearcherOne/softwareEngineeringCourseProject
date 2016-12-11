var should  = require('should');
var utility = require('../../lib/utility.js');

describe('utility.js', function() {
  describe('getCurrentDateInMillis()', function(){
    it("should return increased value for consecutive calls with 10 milliseconds delay", function(){
      var currentMillisA = utility.getCurrentDateInMillis();
      var currentMillisB;
      setTimeout(function() {
        currentMillisB = utility.getCurrentDateInMillis();
        currentMillisA.should.be.below(currentMillisB);
      }, 10);
    });
    
    it("should return bigger value than previously measured date in milliseconds", function() {
      var measuredMillis = new Date().getTime();
      setTimeout(function() {
        var currentMillis = utility.getCurrentDateInMillis();
        measuredMillis.should.be.below(currentMillis);
      }, 10);
    });
    
    it("should return smaller value than afterwards measured date in milliseconds", function() {
      var currentMillisA = utility.getCurrentDateInMillis();
      var currentMillisB;
      
      setTimeout(function() {
        currentMillisB = new Date().getTime();
        currentMillisA.should.be.below(currentMillisB);
      }, 10);
    });
    
    it("should return a value should bu smaller than previously measured time and bigger than afterwards measured time", function() {
      var measuredTimeA = new Date().getTime();
      var returnedValue;
      var measuredTimeB;
      
      setTimeout(function() {
        returnedValue = utility.getCurrentDateInMillis();
        setTimeout(function() {
          measuredTimeB = new Date().getTime();
          returnedValue.should.be.below(measuredTimeB);
          measuredTimeA.should.be.below(returnedValue);
        }, 10);
      }, 10);
    });
  });
});