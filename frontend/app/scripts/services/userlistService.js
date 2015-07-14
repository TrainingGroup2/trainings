'use strict';

angular.module('frontendApp')
  .factory('userlist',['$resource', function($resource) {
  return $resource('http://localhost:8080/users', {}, {
    getUserList: {
      method: 'GET',
      isArray: true
    }
  });
}]);
