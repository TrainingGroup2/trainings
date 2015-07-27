'use strict';

angular.module('frontendApp').controller('UserTrainingsCtrl', ['$location', '$scope', '$q', '$rootScope', '$localStorage', 'userService',
  function($location, $scope, $q, $rootScope, $localStorage, userService) {

    $scope.userTrainings = [];

    $scope.rating = 0;
    $scope.ratings = {
      current: 0,
      max: 5
    };

    $scope.userId = $localStorage.userData.id;
    userService.setUserId($localStorage.userData.id);

    if($rootScope.isExternalTrainer()){
      userService.userResource.getMentoringTrainings(function(resp) {
        $scope.userTrainings =  resp;
        $scope.userTrainings = $scope.filtreData();
      });
    }else{
      $q.all({
        mentoringTrainings: userService.userResource.getMentoringTrainings().$promise,
        visitingTrainings: userService.userResource.getVisitingTrainings().$promise
      }).then(function (values) {
        $scope.userTrainings = values.mentoringTrainings.concat(values.visitingTrainings);
        $scope.userTrainings = $scope.filtreData();
      });
    }

    $scope.filtreData = function() {
      for(var i = 0; i < $scope.userTrainings.length; ++i){
        $scope.userTrainings[i].rating = Math.floor($scope.userTrainings[i].rating+1);
      }
      return $scope.userTrainings;
    };


    $scope.openTraining = function(trainingId) {
      $location.path('/training/' + trainingId);
    };

    $scope.createTraining = function(){
      $location.path('/training/edit');
    };

  }]);
