/**
 * Created by Natsik on 15.07.2015.
 */
angular.module('frontendApp')
  .controller('LoginCtrl', function ($scope,
                                     $localStorage) {
    $scope.$storage = $localStorage;

    user.email = $localStorage.email || "example@gmail.com";
    this.user = user;

    this.login = function () {
      alert("This functionality in progress");

      var rememberMeCheckbox = document.getElementById("rememberMe");
      if (rememberMeCheckbox && rememberMeCheckbox.type == "checkbox" && rememberMeCheckbox.checked) {
        $localStorage.email = this.user.email;
      }
      //TODO clear form after success
    }

    this.clearLocalStorage = function () {
      delete $localStorage.email;
    }
  });

var user = {
  email: "",
  password: ""
}
