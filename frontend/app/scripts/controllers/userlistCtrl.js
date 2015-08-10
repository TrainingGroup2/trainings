'use strict';


angular.module('frontendApp')
  .controller('UserlistCtrl', ['$scope', 'ngDialog',  'userService', '$location', 'userServiceDelegate',
    function ($scope, ngDialog, userService, $location, userServiceDelegate) {

      $scope.users = [];
      $scope.callServer = function (tableState) {
        $scope.isLoading = true;
        var pagination = tableState.pagination;

        var start = (pagination.start || 0) + 1;
        var number = pagination.number || 5;  // Number of entries showed per page.
        userServiceDelegate.getPageCount(start, number, tableState).then(function(res) {
          console.log(res);
          tableState.pagination.numberOfPages = res.data;
          console.log(tableState);
          userServiceDelegate.getPage(start, number, tableState).then(function(result) {
          console.log(result);
          $scope.users = result.data;
          //set the number of pages so the pagination can update
          $scope.isLoading = false;
        } )
      });
    };

      $scope.openCreatePopup = function (type) {
        ngDialog.open({
          template: "views/popups/createExternal.html",
          controller: 'CreateExternalUserCtrl',
          resolve: {
            data: function () {
              return {
                type: type
              };
            }
          }
        });
      };

      $scope.rowClickHandler = function (user){
        userService.setUserId(user.id);
        $location.path('/userprofile');
      }
    }]);


