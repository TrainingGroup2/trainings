'use strict';

angular.module('frontendApp')
  .directive('pageSelect', function() {
    return {
      restrict: 'E',
      template: '<input type="text" class="select-page" ng-model="inputPage" ng-change="selectPage(inputPage)" style="height: 20px;width: 40px">',
      link: function(scope, element, attrs) {
        scope.$watch('currentPage', function(c) {
          scope.inputPage = c;
        });
      }
    }
  });

