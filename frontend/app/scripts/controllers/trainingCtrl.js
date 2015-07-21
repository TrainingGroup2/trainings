'use strict';

angular.module('frontendApp')
  .controller('TrainingCtrl', ['$scope', '$location', 'ngDialog', 'trainingInfo', 'trainingParticipants', 'trainingFeedbacks',
    function ($scope, $location, ngDialog, trainingInfo, trainingParticipants, trainingFeedbacks) {


      $scope.attachments = [{
        key: "name",
        value: "link"
      }];

      $scope.rating = 0;
      $scope.ratings = {
        current: 3,
        max: 5
      };

      $scope.askFeedback = function (userId) {
        console.log(userId);

      };

      $scope.openUserpage = function (userId) {

        if ($scope.isAdmin()) {
          console.log(userId);
          $location.path('/users/user/' + userId);
        }
      };

      $scope.openCreatePopup = function(type) {
        ngDialog.open({
          template: "views/popups/createExternal.html",
          controller: 'CreateExternalUserCtrl',
          resolve: {
            type: function() {
              return type;
            }
          }
        });
      };

      $scope.openWaitList = function() {
        ngDialog.open({
          template: "views/popups/waitList.html",
          controller: 'WaitListCtrl',
          resolve: {
            participants: function() {
              return {
                participants: $scope.training.participants,
                membersCountMax: $scope.training.membersCountMax,
                membersCount: $scope.training.membersCount
              };
            }
          }
        })
      };

      $scope.leaveTrainingFeedback = function () {
        ngDialog.open({
          template: "views/popups/leaveTrainingFeedback.html",
          controller: 'LeaveFeedbackCtrl'
        })
      };

      $scope.openInfo = function() {
        var info = new trainingInfo;
        info.id = 'aba';
        info.$get();
      };

      $scope.openParticipantsList = function() {
        var part = new trainingParticipants;
        part.id = 'aba';
        part.$query();
      };

      $scope.openFeedbacks = function() {
        var feedbacks = new trainingFeedbacks;
        feedbacks.id = 'aba';
        feedbacks.$query();
      };

      $scope.openJournal = function() {

      };

      $scope.registrated = false;

      $scope.training = {
        id: 2,
        name: "Angular",
        trainer: {
          id: 2,
          name: "Jan",
          surname: "Palaznik",
          phone: "+375293368803",
          email: "yan.palaznik@yandex.by",
          role: "EMPLOYEE"
        },
        targetAudience: "front-end developers",
        language: "russian",
        description: "Interesting course",
        status: "DRAFTED",
        membersCountMax: 30,
        membersCount: 35,
        rating: 0,
        participants: [
          {
            id: 4,
            name: "Mihail",
            surname: "Kukuev",
            phone: "+375291817718",
            email: "Mihail.Kukuev@yandex.by",
            role: "EXTERNAL_TRAINER"
          }
        ],
        feedbacks: [
          {
            id: 2,
            understandable: false,
            interesting: false,
            newKnowledge: true,
            effectiveness: 1,
            studyWithTrainer: false,
            recommend: false,
            otherInfo: "awful!",
            feedbacker: {
              id: 5,
              name: "Ivan",
              surname: "Poyda",
              phone: "+375291302232",
              email: "rock.ivan.poyda@gmail.com",
              role: "EMPLOYEE"
            },
            date: 1432656000000
          }
        ],
        entries: [
          {
            id: 3,
            place: "Minsk, 243",
            beginTime: 1432821600000,
            endTime: 1432825200000
          }
        ]

      };

    }]);
