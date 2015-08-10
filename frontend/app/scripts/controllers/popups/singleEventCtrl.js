'use strict';

angular.module('frontendApp')
  .controller('EventCtrl', ['$scope', '$http', '$location', 'userService', 'data', 'userServiceDelegate', 'SingleEventService', 'training',
    function($scope, $http, $location, userService, data, userServiceDelegate, SingleEventService, training) {
     $scope.singleevent = data;

      $scope.userId = 0;
      $scope.trainingId = 0;

      if($scope.singleevent.eventType == "TRAINING_FEEDBACK" || $scope.singleevent.eventType == "TRAINING"){
        training.getTrainingIdByFeedbackId({id : $scope.singleevent.subjectId}, function(resp){
          $scope.trainingId = resp.id;
        });
      }
      if($scope.singleevent.eventType == "USER_FEEDBACK")
       userServiceDelegate.getUserByFeedbackId($scope.singleevent.subjectId).then(function(resp){
         $scope.userId =  resp.data;
     });



      $scope.redirectToEntity = function(singleevent){
        if(singleevent.eventType == "TRAINING"){
          $location.path("/training/editTraining/"+$scope.trainingId);
        }
        if(singleevent.eventType == "USER_FEEDBACK"){
          $scope.setWatched();
          userService.setRedirectUserId($scope.userId);
          $location.path("/userprofile/"+$scope.userId);
        }
        if(singleevent.eventType == "TRAINING_FEEDBACK"){
          $scope.setWatched();
          $location.path("/training/"+$scope.trainingId);
        }
        $scope.closeThisDialog();
      };

      $scope.setWatched = function() {
        $scope.singleevent.watched = true;
        var event = new SingleEventService();
        _.extend(event, {
          id : $scope.singleevent.id,
          description : $scope.singleevent.description,
          watched: $scope.singleevent.watched,
          subjectId: $scope.singleevent.subjectId,
          eventType: $scope.singleevent.eventType,
          date : $scope.singleevent.date
        });
        SingleEventService.update(event);
        $scope.closeThisDialog();
      };

      $scope.isTrainingEvent = function(eventType) {
        return eventType == "TRAINING";
      };

      $scope.isUserFeedbackEvent = function(eventType) {
        return eventType == "USER_FEEDBACK";
      };

      $scope.isTrainingFeedbackEvent = function(eventType) {
        return  eventType == "TRAINING_FEEDBACK";
      };
    }
  ]);

