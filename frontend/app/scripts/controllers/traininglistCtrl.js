'use strict';

angular.module('frontendApp').controller('TrainingListCtrl', ['$location', '$scope', 'TrainingList', 'trainingListServiceDelegate',
  function($location, $scope, TrainingList,trainingListServiceDelegate ) {

    $scope.filterData = function(){
      for(var i = 0; i < $scope.trainingsList.length; ++i){
        $scope.trainingsList[i].rating = Math.floor($scope.trainingsList[i].rating);
      }
      return $scope.trainingsList;
    };

    //$scope.trainingsList = $scope.filterData();

    $scope.rating = 0;
    $scope.ratings = {
      current: 1,
      max: 5
    };

    $scope.callServer = function(tableState) {
      $scope.isLoading = true;
      var pagination = tableState.pagination;

      var start = (pagination.start || 0) + 1;
      var number = pagination.number || 5;  // Number of entries showed per page.
      trainingListServiceDelegate.getPageCount(start, number, tableState).then(function (res) {
        console.log(res);
        tableState.pagination.numberOfPages = res.data;
        console.log(tableState);
        trainingListServiceDelegate.getPage(start, number, tableState).then(function (result) {
          console.log(result);
          $scope.trainingsList = result.data;
          $scope.filterData();
          //set the number of pages so the pagination can update
          $scope.isLoading = false;
        });
      });
    };

    $scope.openTraining = function(trainingId) {
      $location.path('/training/' + trainingId);
    };
  }]);
