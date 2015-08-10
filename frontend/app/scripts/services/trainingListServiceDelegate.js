'use strict';

angular.module('frontendApp')
  .service('trainingListServiceDelegate', ['$http', function($http) {
  //TODO change URLs
    this.getPage = function (pageStart, number, tableState) {

      var _url = 'http://localhost:8080/trainings/pages/'+pageStart+'?size='+number;
      angular.forEach(tableState.search.predicateObject, function(value, key) {
        _url += '&' + key + '=' + value;
      });
      if(!_.isEmpty(tableState.sort)) {
        _url += '&sortParam=' + tableState.sort.predicate + '&isReversed=' + tableState.sort.reverse;
      }
      return $http.get(_url);
    };

    this.getPageCount = function(start, number, tableState) {
      return $http.get('http://localhost:8080/trainings/pages/count/'+start+'?size='+number+'&sortParam='+tableState.sort.predicate);
    };
  }]);
