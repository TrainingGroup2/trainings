'use strict';

angular.module('frontendApp').factory('EventService',['$resource', function($resource) {
  return $resource('http://localhost:8080/events/unwatched', {}, {
    getEventList: {
      method: 'GET',
      isArray: true
    }
  });
}]);
