'use strict';

angular.module('frontendApp')
  .controller('EditTrainingCtrl', ['$scope', '$rootScope', '$sce', '$templateRequest', '$route', '$compile', '$location',
    'FileUploader', 'editTraining', 'trainerlist', 'data', 'entries', 'attachments', 'postAttachLinks',
    function($scope, $rootScope, $sce, $templateRequest, $route, $compile, $location, FileUploader,
             editTraining, trainerlist, data, entries, attachments, postAttachLinks) {

    $scope.newTraining = false;

      $scope.attachments = [];
      $scope.attachment = {};


      $scope.statuses = ['DRAFTED', 'APPROVED', 'CANCELED'];

      $scope.data = angular.copy(data);
      var constTrainingData = angular.copy($scope.data);
      $scope.data.repeated = false;
      $scope.data.entries = angular.copy(entries);
      $scope.data.attachments = angular.copy(attachments);
      var constEntries = angular.copy($scope.data.entries);
      $scope.entryNum = 1;

      $scope.pendingR = false;

      $scope.compareEntryData = function() {
        if(constEntries){
          for (var i = 0; i < constEntries.length; i++) {
            $scope.description = $scope.description + "In lecture " + (i+1) + ":" + "\n";
            var hasKey = ['id', 'absentees', 'trainingId'];
            angular.forEach(constEntries[i], function (value, key) {
              if (!_.contains(hasKey, key) && value !== _.property(key)($scope.data.entries[i])) {
                if(_.property(key)($scope.data.entries[i]) != undefined){
                  if(key == "beginTime" || key =="endTime")
                  {
                    $scope.description = $scope.description + key + " from " + new Date(value) + " to " + _.property(key)($scope.data.entries[i]) + "\n";
                  }else{
                    $scope.description = $scope.description + key + " from " + value + " to " + _.property(key)($scope.data.entries[i]) + "\n";
                  }
                }
              }
            });
          }
        }
      };

      $scope.compareTrainingData = function(){
        $scope.description = "Training " +  constTrainingData.name + " has been changed." + "\n" + "Changes : " + "\n";
        var hasKey = ['trainer', 'attachments', 'entries', '$promise', '$resolved'];
        angular.forEach(constTrainingData, function(value, key) {
          if(!_.contains(hasKey, key) && value !== _.property(key)($scope.data)){
            $scope.description = $scope.description + key + " from " + value + " to " +  _.property(key)($scope.data) + "\n";
          }
          if((key === 'trainer'&& value.id !== $scope.data.trainer.id)){
              $scope.description = $scope.description + key + " from " + value.name + " to " + $scope.data.trainer.name + "\n";
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
        $scope.data.status = ($rootScope.isAdmin()) ? "APPROVED" : "DRAFTED";
        $scope.pendingR = true;
        var edit = new editTraining();
        edit.data = angular.copy($scope.data);
        console.log(edit.data);
        for(var i = 0; i < edit.data.entries.length; i++) {
          edit.data.entries[i].beginTime = edit.data.entries[i].beginTime.valueOf();
          edit.data.entries[i].endTime = edit.data.entries[i].endTime.valueOf();
        }
        if($scope.data.repeated) {
          edit.data.begin = edit.data.begin.valueOf();
          edit.data.end = edit.data.end.valueOf();
        }
        $scope.data.repeated = !(constTrainingData.repeated == $scope.data.repeated);
        $scope.compareTrainingData();
        $scope.compareEntryData();
        console.log($scope.description);
        postAttachLinks.save($scope.attachments).$promise.then(function(resp) {});
        editTraining.updateTraining($scope.data).$promise.then(function(resp) {
          console.log(resp);
          $scope.pendingR = false;
          $location.path('/training/' + $route.current.params.trainingId);
        });
      };


      $scope.addLink = function() {
        _.extend($scope.attachment, {trainingId: $route.current.params.trainingId});
        $scope.attachments.push(angular.copy($scope.attachment));
        angular.element('.links').append($compile('<li><a href="'+$scope.attachment.link+'">'+$scope.attachment.name+' </a>&nbsp; <i class="fa fa-close" ng-click="removeLink($event)"></i> </li>')($scope));
        $scope.attachment = {};

      };

      $scope.removeLink = function($event) {
        var name = angular.element($event.currentTarget).parents('li').children('a').html().trim();
        var link = angular.element($event.currentTarget).parents('li').children('a').attr('href');
        var index = _.findLastIndex($scope.attachments, {name: name, link: link});
        delete $scope.attachments[index];
        angular.element($event.currentTarget).parents('li').remove();
      };

      $scope.deleteAttachment = function(aid, $event) {
        editTraining.deleteAttachment({aid: aid}, function(resp) {
          angular.element($event.currentTarget).parents('li').remove();
        });
      };

      var uploader = $scope.uploader = new FileUploader({
        url: 'http://localhost:8080/files/upload?trainingId=' + $route.current.params.trainingId
      });

      uploader.withCredentials = true;

      $scope.users = [];
      if($rootScope.isAdmin() && _.isEmpty($scope.users)) {
        trainerlist.getUserList(function(resp) {
          $scope.users = angular.copy(resp);
        })
      } else {
        $scope.users = $localStorage.userData;
      }

  }]);
