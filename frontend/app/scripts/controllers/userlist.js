'use strict';


  angular.module('frontendApp')
  .controller('UserlistCtrl', ['$scope', 'userlist',
    function($scope, userlist) {

      userlist.getUserList(function(resp) {
        $scope.userlist = resp;
      });

      $scope.rowCollection = [
        {id:"1" ,firstName: 'a', lastName: 'cadaba', role: 'admin', email: "cadaba1@exadel.com", login: "a", phone: "+375291488228"},
        {id:'2',firstName: 'i', lastName: 'cadaba', role: 'user', email: "cadaba2@exadel.com", login: "i", phone: "+375291488228"},
        {id:"3" ,firstName: 'u', lastName: 'cadaba', role: 'user', email: "cadaba3@exadel.com", login: "u", phone: "+375291488228"},
        {id:'4',firstName: 'e', lastName: 'cadaba', role: 'user', email: "cadaba4@exadel.com", login: "e", phone: "+375291488228"},
        {id:"5" ,firstName: 'o', lastName: 'cadaba', role: 'trainer', email: "cadaba5@exadel.com", login: "o", phone: "+375291488228"},
        {id:'6',firstName: 'ka', lastName: 'cadaba', role: 'user', email: "cadaba6@exadel.com", login: "ka", phone: "+375291488228"},
        {id:"7" ,firstName: 'ki', lastName: 'cadaba', role: 'user', email: "cadaba7@exadel.com", login: "ki", phone: "+375291488228"},
        {id:'8',firstName: 'ku', lastName: 'cadaba', role: 'user', email: "cadaba8@exadel.com", login: "ku", phone: "+375291488228"},
        {id:"9" ,firstName: 'ke', lastName: 'cadaba', role: 'user', email: "cadaba9@exadel.com", login: "ke", phone: "+375291488228"},
        {id:'10',firstName: 'ko', lastName: 'cadaba', role: 'trainer', email: "cadaba10@exadel.com", login: "ko", phone: "+375291488228"},
        {id:"11" ,firstName: 'sa', lastName: 'cadaba', role: 'user', email: "cadaba11@exadel.com", login: "sa", phone: "+375291488228"},
        {id:'12',firstName: 'shi', lastName: 'cadaba', role: 'user', email: "cadaba12@exadel.com", login: "shi", phone: "+375291488228"},
        {id:"13" ,firstName: 'su', lastName: 'cadaba', role: 'user', email: "cadaba13@exadel.com", login: "su", phone: "+375291488228"},
        {id:'14',firstName: 'se', lastName: 'cadaba', role: 'user', email: "cadaba14@exadel.com", login: "se", phone: "+375291488228"},
        {id:"15" ,firstName: 'so', lastName: 'cadaba', role: 'trainer', email: "cadaba15@exadel.com", login: "so", phone: "+375291488228"},
        {id:'16',firstName: 'ta', lastName: 'cadaba', role: 'user', email: "cadaba16@exadel.com", login: "ta", phone: "+375291488228"},
        {id:"17" ,firstName: 'chi', lastName: 'cadaba', role: 'user', email: "cadaba17@exadel.com", login: "chi", phone: "+375291488228"},
        {id:'18',firstName: 'tsu', lastName: 'cadaba', role: 'user', email: "cadaba18@exadel.com", login: "tsu", phone: "+375291488228"},
        {id:"19" ,firstName: 'te', lastName: 'cadaba', role: 'user', email: "cadaba19@exadel.com", login: "te", phone: "+375291488228"},
        {id:'20',firstName: 'to', lastName: 'cadaba', role: 'trainer', email: "cadaba20@exadel.com", login: "to", phone: "+375291488228"},
        {id:"21" ,firstName: 'na', lastName: 'cadaba', role: 'user', email: "cadaba21@exadel.com", login: "na", phone: "+375291488228"},
        {id:'22',firstName: 'ni', lastName: 'cadaba', role: 'user', email: "cadaba22@exadel.com", login: "ni", phone: "+375291488228"},
        {id:"23" ,firstName: 'nu', lastName: 'cadaba', role: 'admin', email: "cadaba23@exadel.com", login: "nu", phone: "+375291488228"},
        {id:'24',firstName: 'ne', lastName: 'cadaba', role: 'user', email: "cadaba24@exadel.com", login: "ne", phone: "+375291488228"},
        {id:'25',firstName: 'no', lastName: 'cadaba', role: 'trainer', email: "cadaba25@exadel.com", login: "no", phone: "+375291488228"},



      ];
      $scope.itemsByPage=5;
    }]);


