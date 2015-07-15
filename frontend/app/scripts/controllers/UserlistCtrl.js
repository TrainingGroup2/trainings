'use strict';


angular.module('frontendApp')
  .controller('UserlistCtrl', ['$scope', 'data',
    function($scope, data) {
      $scope.users = data;
    }]);


