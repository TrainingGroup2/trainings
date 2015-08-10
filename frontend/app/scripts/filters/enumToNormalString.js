'use strict';

angular.module('frontendApp')
  .filter('enumToNormalString', function () {
    return function (input) {
      if (input === 'CANCELLED') {
        return 'Canceled';
      }
      if (input === 'COMPLETED') {
        return 'Completed';
      }
      if (input === 'APPROVED') {
        return 'Approved';
      }
      if (input === 'DRAFTED') {
        return 'Drafted';
      }
      if (input === 'ADMIN') {
        return 'Admin';
      }
      if (input === 'EMPLOYEE') {
        return 'Employee';
      }
      if (input === 'EXTERNAL_TRAINER') {
        return 'External Trainer';
      }
      if (input === 'EXTERNAL_VISITOR') {
        return 'External Visitor';
      }
      if (input === 'TRAINING') {
        return 'Training event';
      }
      if (input === 'USER_FEEDBACK') {
        return 'Feedback on User';
      }
      if (input === 'TRAINING_FEEDBACK') {
        return 'Feedback on Training'
      }
    }
  }
);
