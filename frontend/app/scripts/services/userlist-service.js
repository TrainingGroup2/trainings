  angular.module('frontendApp')
  .service('userlist',[ '$resource', function($resource) {
  return $resource('/users', {}, {
    getUserList: {
      method: 'GET',
      isArray: true
    }
  });
}]);
