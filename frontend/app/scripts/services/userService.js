angular.module('frontendApp').factory('userService', function ($resource, $q) {
  var userId = 0;
  var redirectUserId = 0;
  var getUserId  = function(){
    return userId;
  };
  var $userResource = $resource('http://localhost:8080/user?id=:id', {id: '@id'}, {
    getMentoringTrainings: {
      url: 'http://localhost:8080/user/mentoringTrainings?userId=:id',
      params:{id:getUserId},
      method: 'GET',
      isArray: true
    },
    getVisitingTrainings: {
      url: 'http://localhost:8080/user/visitingTrainings?userId=:id',
      params:{id:getUserId},
      method: 'GET',
      isArray: true
    },
    getUser: {
      method: 'GET'
    }
  });
  return {
    userResource: $userResource,
    getUserProfileData: function () {
      return $userResource.get({id: userId}).$promise;
    },
    getUserProfileDataById: function (requireUserId) {
      return $userResource.get({id: requireUserId}).$promise;
    },
    setUserId: function (_userId) {
      userId = _userId;
    },
    setRedirectUserId: function(setUserId) {
      redirectUserId = setUserId;
    },
    getRedirectUserId: function() {
      return redirectUserId;
    }
  }
});


