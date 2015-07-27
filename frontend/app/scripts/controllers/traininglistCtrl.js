'use strict';

angular.module('frontendApp').controller('TrainingListCtrl', ['$location', '$scope', 'TrainingList', 'data',
  function($location, $scope, TrainingList, data) {
    $scope.trainingsList = data;

    $scope.filtreData = function(){
      for(var i = 0; i < $scope.trainingsList.length; ++i){
        $scope.trainingsList[i].rating = Math.floor($scope.trainingsList[i].rating+1);
      }
      return $scope.trainingsList;
    };

    $scope.trainingsList = $scope.filtreData();

    $scope.rating = 0;
    $scope.ratings = {
      current: 1,
      max: 5
    };

    $scope.openTraining = function(trainingId) {
      $location.path('/training/' + trainingId);
    };
  }]);
