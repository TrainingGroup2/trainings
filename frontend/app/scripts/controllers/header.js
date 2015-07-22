/**
 * Created by Natsik on 11.07.2015.
 */
angular.module('frontendApp')
  .controller('HeaderCtrl', ['$location', function ($location) {

    this.companyName = companyName;

    this.goToUserProfile = function (){
      $location.path('/userprofile');
    };

    this.logout = function (){
      alert("we are working...");
      //TODO logout functionality
    };

  }]);

var companyName = "Exadel";
