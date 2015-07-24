'use strict';

angular.module('frontendApp').factory('EventService', function() {

    // temp data. no backend yet
    var messages = {};
    messages.list = [];
    var eventList = [
      {eventId: 1, eventDate: '2015-07-20 10:29', author: 'A', eventText: "New training 1"},
      {eventId: 2, eventDate: '2015-08-20 10:29', author: 'B', eventText: "New training 2"},
      {eventId: 3, eventDate: '2015-09-20 10:29', author: 'C', eventText: "New training 3"},
      {eventId: 4, eventDate: '2015-10-20 10:29', author: 'D', eventText: "New training 4"},
      {eventId: 5, eventDate: '2015-11-20 10:29', author: 'E', eventText: "New training 5"},
      {eventId: 6, eventDate: '2015-12-20 10:29', author: 'F', eventText: "New training 6"},
      {eventId: 7, eventDate: '2015-13-20 10:29', author: 'G', eventText: "New training 7"},
      {eventId: 8, eventDate: '2015-14-20 10:29', author: 'h', eventText: "New training 8"},
      {eventId: 9, eventDate: '2015-15-20 10:29', author: 'i', eventText: "New training 9"},
      {eventId: 10, eventDate: '2015-16-20 10:29', author: 'j', eventText: "New training 10"},
      {eventId: 11, eventDate: '2015-17-20 10:29', author: 'k', eventText: "New training 11"},
      {eventId: 12, eventDate: '2015-18-20 10:29', author: 'l', eventText: "New training 12"},
      {eventId: 13, eventDate: '2015-19-20 10:29', author: 'm', eventText: "New training 13"},
      {eventId: 14, eventDate: '2015-20-20 10:29', author: 'n', eventText: "New training 14"},
      {eventId: 15, eventDate: '2015-21-20 10:29', author: 'o', eventText: "New training 15"},
      {eventId: 16, eventDate: '2015-22-20 10:29', author: 'p', eventText: "New training 16"},
      {eventId: 17, eventDate: '2015-23-20 10:29', author: 'q', eventText: "New training 17"},
      {eventId: 18, eventDate: '2015-22-20 10:29', author: 'r', eventText: "New training 18"},
      {eventId: 19, eventDate: '2015-21-20 10:29', author: 's', eventText: "New training 19"},
      {eventId: 20, eventDate: '2015-20-20 10:29', author: 't', eventText: "New training 20"}
    ];
    messages.list = angular.copy(eventList);
    return messages;
  // end tmp data

    // this would be smth like final version
    /*return $resource('http://localhost:8080/eventList', {}, {
      getEventList: {method: 'GET',
        isArray: true
      }
    });*/
  });
