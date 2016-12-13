var MongoCrud   = require('mongo-crud-layer');
var mongocrud;

var scoreCollection_;
var mongoUrl_;

module.exports = {
  initializeModule: function(mongoUrl, scoreCollection) {
    scoreCollection_ = scoreCollection;
    mongoUrl_ = mongoUrl;
  }
};