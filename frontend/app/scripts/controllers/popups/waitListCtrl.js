'use strict';

angular.module('frontendApp')
  .controller('WaitListCtrl', ['$scope', 'training', function($scope, training) {

    $scope.participants = training.participants;
    $scope.wlSize = training.membersCount - training.membersCountMax;

  }]);
