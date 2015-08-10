angular.module('frontendApp').service('userServiceDelegate', function ($http) {
  this.updateUserInfo = function (saveType, user) {
    var _url = "http://localhost:8080/users/" + saveType;
    return $http.put(_url, user);
  };
  this.getUserByFeedbackId = function (id) {
    var _url = "http://localhost:8080/user/byFeedback?id=" + id;
    return $http.get(_url);
  };
  this.getPage = function (pageStart, number, tableState) {
    var _url = 'http://localhost:8080/users/pages/'+pageStart+'?size='+number;
    angular.forEach(tableState.search.predicateObject, function(value, key) {
      _url += '&' + key + '=' + value;
    });
    if(!_.isEmpty(tableState.sort)) {
      _url += '&sortParam=' + tableState.sort.predicate + '&isReversed=' + tableState.sort.reverse;
    }
    return $http.get(_url);
  };
  this.getPageCount = function(start, number, tableState) {
    return $http.get('http://localhost:8080/users/pages/count/'+start+'?size='+number+'&sortParam='+tableState.sort.predicate);
  };
});

