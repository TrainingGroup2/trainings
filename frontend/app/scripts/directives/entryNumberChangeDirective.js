'use strict';

angular.module('frontendApp')
  .directive('entryNumberChange',  function() {
    return {
      restrict: 'E',
      template: '<input type="text" style="height: 20px; width: 30px" ng-model="inputNum" ng-change="stItemsByPage = inputNum">',
      link: function(scope, element, attrs) {
        scope.$watch('stItemsByPage', function(c) {
          scope.inputNum = c;
        });
      }
    }
  });
