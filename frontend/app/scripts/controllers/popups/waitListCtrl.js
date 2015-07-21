'use strict';

angular.module('frontendApp')
  .controller('WaitListCtrl', ['participants','$scope', function(participants, $scope) {

    $scope.participants = participants.participants;
    $scope.wlSize = participants.membersCount - participants.membersCountMax;

  }]);
