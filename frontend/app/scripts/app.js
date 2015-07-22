'use strict';

/**
 * @ngdoc overview
 * @name frontendApp
 * @description
 * # frontendApp
 *
 * Main module of the application.
 */
angular
  .module('frontendApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'smart-table',
    'ngStorage',
    'ngDialog',
    'ui.bootstrap',
    'ngAside'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/users', {
        templateUrl: 'views/userlist.html',
        controller: 'UserlistCtrl',
        controllerAs: 'userlist',
        resolve: {
          data: function(userlist) {
            return userlist.getUserList().$promise;
          }
        }})
      .when('/traininglist', {
        templateUrl: 'views/traininglist.html',
        controller: 'TrainingListCtrl',
        resolve : {
           data:  function(TrainingList) {
            return TrainingList.getTrainingList().$promise;
          }
         }
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/userprofile', {
        templateUrl: 'views/userprofile.html',
        controller: 'UserProfileCtrl',
        controllerAs: 'userprofile'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        controllerAs: 'loginController'
      })
      .when('/trainings/training/:trainingId', {
        templateUrl: 'views/training.html',
        controller: 'TrainingCtrl',
        controllerAs: 'training'
      })
      .otherwise({
        redirectTo: '/'
      })
  }).run(function ($rootScope, translationService) {
    //TODO provide role from userService here
    $rootScope.isAdmin = function () {
      return true;
    };
    $rootScope.isExternalTrainer = function () {
      return false;
    };

    $rootScope.isEmployee = function () {
      return false;
    };
    //perhaps there is need to add new module for localization
    $rootScope.translate = function(){
      translationService.getTranslation($rootScope.i10n.selectedLanguage);
    };

    $rootScope.i10n = {};
    $rootScope.i10n.selectedLanguage = 'en';

    $rootScope.translate();
  });

