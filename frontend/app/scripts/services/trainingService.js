'use strict';

angular.module('frontendApp').factory('training',['$resource',
  function($resource) {
    return $resource('http://localhost:8080/training/:type?trainingId=:id', {}, {

      getFeedbacks: {
        method: 'GET',
        params: {
          type: 'feedbacks',
          id: '@id'
        },
        isArray: true
      },

      getParticipants: {
        method: 'GET',
        params: {
          type: 'participants',
          id: '@id'
        },
        isArray: true
      },

      getInfo: {
        method: 'GET',
        params: {
          type: 'info',
          id: '@id'
        }
      },

      getEntries: {
        method: 'GET',
        params: {
          type: 'entries',
          id: '@id'
        },
        isArray: true
      },

      getEntry: {
        method: 'GET',
        params: {
          type: 'nextEntry',
          id: '@id'
        }
      },

      getAttachments: {
        method: 'GET',
        params: {
          type: 'attachments',
          id: '@id'
        },
        isArray: true
      },

      getTrainer: {
        method: 'GET',
        params: {
          type: 'trainer',
          id: '@id'
        }
      },

      getParticipation: {
        method: 'GET',
        params: {
          type: 'participation',
          id: '@id'
        },
        isArray: true
      },

      getAbsentees: {
        method: 'GET',
        params: {
          type: 'absentees',
          id: '@id',
          beginDate: '@beginDate',
          endDate: '@endDate'
        },
        isArray: true
      },

      getAllAbsentees: {
        method: 'GET',
        params: {
          type: 'absentees',
          id: '@id',
          endDate: '@endDate'
        },
        isArray: true
      },

      getWaitList: {
        method: 'GET',
        url: 'http://localhost:8080/training/waitList?trainingId=:id',
        isArray: true
      },

      checkParticipation: {
        url: 'http://localhost:8080/training/check_participation?userId=:uid',
        method: 'GET'
      },

      deleteFeedback: {
        url: 'http://localhost:8080/training/feedbacks?feedbackId=:feedbackId',
        method: 'DELETE'
      },

      cancel: {
        url: 'http://localhost:8080/training?trainingId=:id',
        method: 'DELETE'
      },

      register: {
        url: 'http://localhost:8080/training/register?',
        params: {
          userId: '@uid',
          trainingId: '@id'
        },
        method: 'POST'
      },

      unregister: {
        url: 'http://localhost:8080/training/unregister?',
        params: {
          userId: '@uid',
          trainingId: "@id"
        },
        method: 'POST'
      },

      getTrainingIdByFeedbackId : {
        method : 'GET',
        url : 'http://localhost:8080/training/byFeedback',
        params : {
          id: '@id'
        }
      }

    });
  }]);
