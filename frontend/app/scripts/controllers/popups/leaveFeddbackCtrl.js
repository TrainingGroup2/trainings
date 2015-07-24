'use strict';

angular.module('frontendApp')
  .controller('LeaveFeedbackCtrl', ['$scope','TrainingFeedbackService','UserFeedbackService', function($scope,TrainingFeedbackService,UserFeedbackService) {

    $scope.rating = 0;
    $scope.ratings = {
      current: 3,
      max: 5
    };

    // temp data
    $scope.levles = [
      { Name: 'PreInt'},
      { Name: 'Int'},
      { Name: 'UppInt'},
      { Name: 'Ad'}
    ];

    // temp data
    $scope.marks = [
      { grade: 0},
      { grade: 1},
      { grade: 2},
      { grade: 3},
      { grade: 4}
    ];

    $scope.leaveTrainingFeedback = function() {
      var userId =1;
      var trainingId = 123;
      var feed = new TrainingFeedbackService();
      feed.id = trainingId;
      feed.userId = userId;
      feed.effectiveness = $scope.$$childHead.ratings.current;        //not good
      feed.understandable = $scope.understandable;
      feed.interesting = $scope.interesting;
      feed.newKnowledge = $scope.newKnowledge;
      feed.studyWithTrainer = $scope.studyWithTrainer;
      feed.recommend = $scope.recommend;
      feed.otherInfo = $scope.feedbackInput;
      feed.date = new Date();
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
