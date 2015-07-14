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
angular.module('frontendApp')
  .directive('entryChange',  function() {
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
