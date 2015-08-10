/**
 * Created by Natsik on 11.07.2015.
 */
angular.module('frontendApp')
  .controller('HeaderCtrl', ['$location', '$scope', '$localStorage', '$http', '$rootScope', 'AuthenticationService',
    'userService', 'appConstants',
    function ($location, $scope, $localStorage, $http, $rootScope, AuthenticationService, userService, appConstants) {

    $scope.languagesList = appConstants.LANGUAGES;

    this.goToUserProfile = function (){
      userService.setUserId($localStorage.userData.id);
      $location.path('/userprofile');
    };

    $scope.logout = function() {
      $http.get('http://localhost:8080/logout',{})
        .finally(function(data){
          AuthenticationService.ClearCredentials();
          $location.path("/login");
        });
    };

      $scope.isLoginPage = function(){
        var url = $location.path();
        return url !== "/login";
      };


      $scope.isMailResp = function() {
        var resp = ($location.path().split('/')[2] !== 'confirm_participation') &&
          ($location.path().split('/')[2] !== 'cancel_participation');
        console.log(resp);
        return resp;
      }
  }]);
