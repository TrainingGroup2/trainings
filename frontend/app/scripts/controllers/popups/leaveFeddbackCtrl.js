'use strict';

angular.module('frontendApp')
  .controller('LeaveFeedbackCtrl', ['$scope','TrainingFeedbackService','UserFeedbackService', 'Levels','Marks',
    function($scope, TrainingFeedbackService, UserFeedbackService, Levels, Marks) {

    $scope.rating = 0;
    $scope.ratings = {
      current: 3,
      max: 5
    };

    $scope.levels = angular.copy(Levels);
    $scope.marks  = angular.copy(Marks);

    $scope.leaveTrainingFeedback = function() {
      var userId =1;
      var trainingId = 123;
      var feed = new TrainingFeedbackService();
      _.extend(feed,{
        id : trainingId,
        userId : userId,
        effectiveness: $scope.$$childHead.ratings.current, //fix me
        date : new Date()
      });
      _.extend(feed,$scope.entity);
      feed.$save();
      $scope.$parent.feedbackInput = "";
      $scope.closeThisDialog();
    };

    $scope.leaveUserFeedback = function() {
      var userId =123; //id of user on we write feedback
      var feedbackerId = 1; // id of feedbacker
      var feed = new UserFeedbackService();
      _.extend(feed,{
        trainer : {id: feedbackerId},
        user : {id: userId},
        date : new Date(),
        englishLevel : $scope.$$childHead.ddlLevle,
        grade : $scope.$$childTail.ddlMark
      });
      _.extend(feed,$scope.entity);
      feed.$save();
      $scope.$parent.feedbackInput = "";
      $scope.closeThisDialog();
    };

  }]);
