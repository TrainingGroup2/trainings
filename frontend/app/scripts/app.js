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
    'ngAside',
    'base64',
    'angularFileUpload',
    'ui.select'
  ])
  .config(function ($routeProvider,$httpProvider) {
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
          data: function (userlist) {
            return userlist.getUserList().$promise;
          }
        }
      })
      .when('/traininglist', {
        templateUrl: 'views/traininglist.html',
        controller: 'TrainingListCtrl',
        resolve: {
          data: function (TrainingList) {
            return TrainingList.getTrainingList().$promise;
          }
        }
      })
      .when('/userTrainings', {
        templateUrl: 'views/userTrainingsList.html',
        controller: 'UserTrainingsCtrl'
      })
      .when('/eventslist', {
        templateUrl: 'views/eventslist.html',
        controller: 'EventsListCtrl',
        resolve: {
          data: function (eventslistService) {
            return eventslistService.getEventsList().$promise;
          }
        }
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/userprofile/:userId/', {
        templateUrl: 'views/userprofile.html',
        controller: 'UserProfileCtrl',
        resolve: {
          userProfileData: function (userService) {
            var id = userService.getRedirectUserId();
            return userService.getUserProfileDataById(id);
          }
        }
      })
      .when('/userprofile', {
        templateUrl: 'views/userprofile.html',
        controller: 'UserProfileCtrl',
        resolve: {
          userProfileData: function (userService) {
            return userService.getUserProfileData();
          }
        }
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        controllerAs: 'loginController'
      })
      .when('/training/edit', {
        templateUrl: 'views/createTraining.html',
        controller: 'CreateTrainingCtrl',
        controllerAs: 'createTraining'
      })
      .when('/training/:trainingId', {
        templateUrl: 'views/training.html',
        controller: 'TrainingCtrl'
      })
      .when('/training/:trainingId/edit', {
        templateUrl: 'views/createTraining.html',
        controller: 'EditTrainingCtrl',
        resolve: {
          data: ['$route', 'training', function($route ,training) {
            return training.getInfo({id: $route.current.params.trainingId}).$promise;
          }],
          entries: ['$route', 'training', function($route ,training) {
            return training.getEntries({id: $route.current.params.trainingId, future: true}).$promise;
          }],
          attachments: ['$route', 'training', function($route, training) {
            return training.getAttachments({id: $route.current.params.trainingId, future: true}).$promise;
          }]
        }
      })
      .otherwise({
        redirectTo: '/'
      });
    $httpProvider.defaults.withCredentials = true;
  }).run(function ($http, $location, $cookies, $rootScope, $localStorage, translationService, appConstants) {
    $rootScope.globals = $cookies.get('globals');
    if ($rootScope.globals) {
      $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
      $rootScope.globals = $cookies.get('globals');
      var menu = angular.element('.modal.fade.horizontal.right.in');
      if(menu)
        menu.remove();
      if ($location.path() !== '/login' && !$rootScope.globals) {
        if ($location.path() !== '') {
          $rootScope.locationPath = $location.path();
        }
        $location.path('/login');
      }
    });


    //TODO provide role from userService here
    $rootScope.isAdmin = function () {
      return $localStorage.userData.role === appConstants.ADMIN;
    };
    $rootScope.isExternalTrainer = function () {
      return $localStorage.userData.role === appConstants.EXT_TRAINER;
    };

    $rootScope.isEmployee = function () {
      return $localStorage.userData.role === appConstants.EMPLOYEE;
    };

    //perhaps there is need to add new module for localization
    $rootScope.translate = function () {
      translationService.getTranslation($rootScope.i10n.selectedLanguage);
    };

    $rootScope.i10n = {};
    $rootScope.i10n.selectedLanguage = 'en';

    $rootScope.translate();
  });


