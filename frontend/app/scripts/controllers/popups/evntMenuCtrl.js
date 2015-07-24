'use strict';

angular.module('frontendApp').controller('EventMenuCtrl', [ '$scope', '$aside', 'EventService', function($scope, $aside, EventService) {

  $scope.eventList = angular.copy(EventService.list);

  $scope.asideState = {
    open: false
  };

  $scope.openAside = function(position) {
    if(!$scope.asideState.open) {
      $scope.asideState = {
        open: true,
        position: position
      };
    }
    else {
      postClose();
      return;
    }

    function postClose() {
      $scope.asideState.open = false;
      angular.element('.modal.fade.horizontal.right.in').remove();
    }

    $aside.open({
      templateUrl: 'views/popups/eventMenu.html',
      placement: position,
      size: 'sm',
      backdrop: false,
      controller: function($scope, $modalInstance) {
        $scope.ok = function(e) {
          $modalInstance.close();
          e.stopPropagation();
        };
        $scope.cancel = function(e) {
          $modalInstance.dismiss();
          e.stopPropagation();
        };
      }
    }).result.then(postClose, postClose);
  }

}]);
