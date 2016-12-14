var MongoCrud   = require('mongo-crud-layer');

var mongocrud;
var _scoreCollection;
var _mongoUrl;

function _saveScoreCheckInputs(userName, score, dateInMillis, clientId) {
  var invalidArgumentError = new Error ("invalid argument");
  if (userName === "")
    return invalidArgumentError;
  else if (!(userName || score || dateInMillis || clientId)) {
    return invalidArgumentError;
  } else if (typeof userName !== "string") {
    return invalidArgumentError;
  } else if (typeof score !== "number" || score < 0) {
    return invalidArgumentError;
  } else if (typeof dateInMillis !== "number" || dateInMillis < 0) {
    return invalidArgumentError;
  } else if (typeof clientId !== "number" || clientId < 0) {
    return invalidArgumentError;
  }
}
module.exports = {
  initializeModule: function(mongoUrl, scoreCollection) {
    _scoreCollection = scoreCollection;
    _mongoUrl = mongoUrl;
    mongocrud = new MongoCrud(_mongoUrl);
  },
  saveScore: function (userName, score, dateInMillis, clientId, callback) {
    var error = _saveScoreCheckInputs(userName, score, dateInMillis, clientId);
    if (!error) {
      var scoreObj = {
          "userName": userName,
          "score": score,
          "dateInMillis": dateInMillis,
          "clientId": clientId
      };
      mongocrud.create(scoreObj, _scoreCollection, function(err, result) {
        callback(err, result);
      });
    } else {
      callback(error, null);
    }
  },
  getTopTenList: function (callback) {
    mongocrud.readAll(_scoreCollection, function(err, docs) {
      if (!err) {
        docs.sort(function(a, b) {
            return b.score - a.score;
        });
        var trimmedResult = docs.slice(0, 10);
        callback(null, trimmedResult);
      } else {
        callback(err, null);
      }
    });
  },
  clearAllScores: function (callback) {
    mongocrud.delete({}, _scoreCollection, callback);
  }
};