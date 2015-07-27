angular.module('frontendApp')
  .filter('trainerListener', function() {
    return function(input, userId, trainerId) {
      if (userId === trainerId){
        return 'Trainer'
      }
      return 'Listener'
    }
  });
