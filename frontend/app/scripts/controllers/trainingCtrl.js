'use strict';

angular.module('frontendApp')
  .controller('TrainingCtrl', ['$route', '$scope', '$location', '$localStorage', 'ngDialog', 'training',
    'UserFeedbackService', 'userService',
    function ($route, $scope, $location, $localStorage, ngDialog, training, UserFeedbackService, userService) {


      $scope.isTrainer = function() {
        return $localStorage.userData.id === $scope.training.trainer.id;
      };

      $scope.pendingR = false;

      $scope.feedbacks = [];
      $scope.training = {};
      $scope.participants = [];
      $scope.entries = [];

      $scope.activeTab = $scope.translation.INFO;

      $scope.openTab = function(tabName) {
        $scope.activeTab = tabName;
      };
      $scope.ifActive = function(tabName) {
        return $scope.activeTab == tabName;
      };

      $scope.trainingId = $route.current.params.trainingId;
      $scope.ratings = {
        current: 1,
        max: 5
      };

      $scope.checkDate = function(date) {
        return date < new Date;
      };

      $scope.askFeedback = function (userId, $event) {
        // TODO add new "Ask feedback" Form. See http://chewbacca.myjetbrains.com/youtrack/issue/App-41
        UserFeedbackService.askUserFeedback({userId: userId, trainingId:$route.current.params.trainingId}, function(resp) {
          console.log('DONE');
        });
        $event.stopPropagation();
      };

      $scope.leaveUserFeedbackDialog = function (userId, $event, userName) {
        ngDialog.open({
          template: "views/popups/leaveUserFeedback.html",
          controller: 'LeaveFeedbackCtrl',
          resolve: {
            trainingData: function () {
              return {
                userName: userName,
                userId: userId,
                trainingName : $scope.trainingName
              }
            }
          }
        });
        $event.stopPropagation();
      };

      $scope.openUserpage = function (trainerId) {
        if ($scope.isAdmin()) {
          console.log(trainerId);
          userService.setRedirectUserId(trainerId);
          $location.path('/userprofile/' + trainerId);
        }
      };

      $scope.openCreatePopup = function(type, trainingId) {
        ngDialog.open({
          template: "views/popups/createExternal.html",
          controller: 'CreateExternalUserCtrl',
          resolve: {
            data: function() {
              return {
                type : type,
                trainingId : trainingId
              };
            }
          }
        });
      };

      $scope.openWaitList = function() {
        ngDialog.open({
          template: "views/popups/waitList.html",
          controller: 'WaitListCtrl'
        })
      };

      $scope.leaveTrainingFeedback = function (trainingName) {
        ngDialog.open({
          template: "views/popups/leaveTrainingFeedback.html",
          controller: 'LeaveFeedbackCtrl',
          resolve: {
            trainingData: function() {
              return {
                trainingName : trainingName,
                trainingId : $scope.training.id
              }
            }
          }
        })
      };

      $scope.openInfo = function() {
        $scope.openTab($scope.translation.INFO);
        if(_.isEmpty($scope.training)) {
          training.getInfo({id: $route.current.params.trainingId}, function(resp){

            $scope.training = angular.copy(resp);
            console.log($scope.training);
            $scope.trainingName = $scope.training.name;
            $scope.ratings.current = $scope.training.rating;

            training.getEntry({id: $route.current.params.trainingId}, function(resp){
              _.extend($scope.training, {entry: angular.copy(resp)});
              $scope.duration = new Date(0);
              $scope.duration.setHours(new Date($scope.training.entry.endTime).getHours() - new Date($scope.training.entry.beginTime).getHours())
              $scope.duration.setMinutes(new Date($scope.training.entry.endTime).getMinutes() - new Date($scope.training.entry.beginTime).getMinutes())
            });
            training.getAttachments({id: $route.current.params.trainingId}, function(resp) {
              _.extend($scope.training, {attachments: angular.copy(resp)});
            })
          });
          training.checkParticipation({uid: $localStorage.userData.id, trainingId: $route.current.params.trainingId}, function(resp) {

            $scope.registrated = (angular.copy(resp)[0] !== 'N');
            $scope.waitL = (angular.copy(resp)[0] === 'R')
          });

        }
      };

      $scope.openParticipantsList = function() {
        $scope.openTab($scope.translation.PARTICIPANTS);
        _.isEmpty($scope.participants)
        training.getParticipants({id: $route.current.params.trainingId}, function(resp){
          $scope.participants = angular.copy(resp);
        });

      };

      $scope.openFeedbacks = function() {
        $scope.openTab($scope.translation.FEEDBACKS);
        if (_.isEmpty($scope.feedbacks)) {
          //TODO: make placeholder which is shown while request is ongoing
          training.getFeedbacks({id: $route.current.params.trainingId}, function (resp) {
            $scope.feedbacks = angular.copy(resp);
          });
        }
      };


      $scope.openEntries = function() {
        $scope.openTab($scope.translation.ENTRIES);
        if(_.isEmpty($scope.entries)) {
          training.getEntries({id: $route.current.params.trainingId}, function (resp) {
            $scope.entries = angular.copy(resp);
          });
        }
      };

      $scope.waitL = false;
      $scope.registrated = false;

      $scope.edit = function() {
            $location.path('/training/' + $route.current.params.trainingId + '/edit');
      };

      $scope.cancel = function() {
        training.cancel({id: $route.current.params.trainingId}, function (resp) {
          console.log(resp);
        });
      };

      $scope.deleteFeedback = function(fid) {
        training.deleteFeedback({feedbackId: fid}, function(resp) {
          training.getFeedbacks({id: $route.current.params.trainingId}, function (resp) {
            $scope.feedbacks = angular.copy(resp);
          });
        });
      };

      $scope.register = function() {
        $scope.pendingR = true;
        training.register({uid: $localStorage.userData.id, id: $route.current.params.trainingId}, function(resp) {
          training.checkParticipation({uid: $localStorage.userData.id, trainingId: $route.current.params.trainingId}, function(resp) {
            $scope.registrated = (angular.copy(resp)[0] !== 'N');
            $scope.waitL = (angular.copy(resp)[0] === 'R');
            $scope.pendingR = false;
          });
        });
      };

      $scope.unregister = function() {
        $scope.pendingR = true;
        training.unregister({uid: $localStorage.userData.id, id: $route.current.params.trainingId}, function(resp) {
          $scope.registrated = !$scope.registrated;
          $scope.pendingR = false;
        });
      };

      $scope.checkAvailable = function() {
        return ($scope.training.status !== 'COMPLETED') && ($scope.training.status !== 'CANCELLED');
      }

    }]);
