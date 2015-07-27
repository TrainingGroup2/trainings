'use strict';

angular.module('frontendApp')
  .controller('EditTrainingCtrl', ['$scope', '$rootScope', '$sce', '$templateRequest', '$route', '$compile',
    'FileUploader', 'editTraining', 'userlist', 'data', 'entries', 'attachments',
    function($scope, $rootScope, $sce, $templateRequest, $route, $compile, FileUploader,
             editTraining, userlist, data, entries, attachments) {

    $scope.newTraining = false;

      $scope.statuses = ['DRAFTED', 'APPROVED', 'CANCELED'];

      $scope.data = angular.copy(data);
      var constTrainingData = angular.copy($scope.data);
      $scope.data.entries = angular.copy(entries);
      $scope.data.attachments = angular.copy(attachments);
      $scope.entries = angular.copy($scope.data.entries);
      var constEntries = angular.copy($scope.data.entries);
      $scope.entryNum = 1;


      $scope.compareEntryData = function() {
        if(constEntries){
          for (var i = 0; i < constEntries.length; i++) {
            $scope.description = $scope.description + "In lecture " + (i+1) + ":" + "\n";
            angular.forEach(constEntries[i], function (value, key) {
              if (key !== 'id' && key !== 'absentees' && key !== 'trainingId' && value !== _.property(key)($scope.data.entries[i])) {
                $scope.description = $scope.description + key + " from " + value + " to " + _.property(key)($scope.entries[i]) + "\n";
              }
            });
          }
        }
      };

      $scope.compareTrainingData = function(){
        $scope.description = "Training " +  constTrainingData.name + " has been changed." + "\n" + "Changes : " + "\n";
        angular.forEach(constTrainingData, function(value, key) {
          if(key !== 'attachments' && key !== 'entries' && key !== '$promise' && key !== '$resolved' && value !== _.property(key)($scope.data)){
            $scope.description = $scope.description + key + " from " + value + " to " +  _.property(key)($scope.data) + "\n";
          }
        });
      };

      $scope.addEntry = function() {
        $templateRequest(templateUrl).then(function(template) {
          angular.element('#trainingEntries').append($compile(template)($scope));
          $scope.entryNum++;
        });
      };

      var templateUrl = $sce.getTrustedResourceUrl('views/templates/newEntry.html');
      if(entries.size > 1) {
        $scope.addEntry();
      }


      $scope.save = function() {
        editTraining.updateTraining($scope.data).$promise.then(function(resp) {
          $scope.compareTrainingData();
          $scope.compareEntryData();
          console.log($scope.description);
          console.log(resp);
        });
      };

      var uploader = $scope.uploader = new FileUploader({
        url: 'http://localhost:8080/files/upload?trainingId' + $scope.data.trainingId
      });

      uploader.withCredentials = true;

      $scope.users = [];
      if($rootScope.isAdmin() && _.isEmpty($scope.users)) {
        userlist.getUserList(function(resp) {
          $scope.users = angular.copy(resp);
        })
      } else {
        $scope.users = $localStorage.userData;
      }

  }]);
