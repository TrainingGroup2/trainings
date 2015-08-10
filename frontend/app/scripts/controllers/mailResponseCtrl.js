'use strict';

angular.module('frontendApp')
  .controller('MailResponseCtrl', ['$scope', '$http', '$route', '$location', '$window',
    function($scope, $http, $route, $location, $window) {

    $scope._confirm = ($location.path().split('/')[2] === 'confirm_participation')
    $scope.isLoading = false;

    $scope.confirm = function() {
      $scope.isLoading = true;
      $http.post('http://localhost:8080/training/confirm_participation?userId='+$route.current.params.userId+
        '&trainingId='+$route.current.params.trainingId).then(function(resp) {

        alert('SUCCESS \n TODO: do bootstrap one');
        $scope.isLoading = false;
        $window.close();
      });
    };

    $scope.decline = function() {
      $scope.isLoading = true;
      $http.post('http://localhost:8080/training/cancel_participation?userId='+$route.current.params.userId+
        '&trainingId='+$route.current.params.trainingId).then(function(resp) {

        alert('SUCCESS \n TODO: do bootstrap one');
        $scope.isLoading = false;
        $window.close();
      });
    };

}]);
