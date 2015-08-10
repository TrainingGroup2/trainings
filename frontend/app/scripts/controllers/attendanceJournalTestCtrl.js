'use strict';
angular.module('frontendApp').controller('AttendanceJournalTestCtrl', ['$scope', '$rootScope', '$localStorage', '$q', 'training', 'attendanceJournalService', 'ngDialog', '$filter', '$interval', function ($scope, $rootScope, $localStorage, $q, training, attendanceJournalService, ngDialog, $filter, $interval) {

  $scope.gridAttendanceOptions = {};

  var now = new Date();
  var prevMonth = new Date();
  prevMonth.setMonth(now.getMonth() - 1);
  $scope.dateStart = prevMonth;
  $scope.dateEnd = now;

  var ABSENT = 'н';
  var PRESENT = '&nbsp;';
  var NOT_ATTEND = 'x';

  $scope.open = function (type) {
    $scope.status[type] = true;
  };
  $scope.status = {
    dateStart: false,
    dateEnd: false
  };
  /*/////
   app.controller('MainCtrl', ['$scope', '$http', function ($scope, $http) {
   $scope.gridOptions = {
   columnDefs: [
   { field: 'name' },
   { field: 'gender', visible: false},
   { field: 'company' }
   ],
   enableGridMenu: true,
   enableSelectAll: true,
   exporterCsvFilename: 'myFile.csv',
   exporterPdfDefaultStyle: {fontSize: 9},
   exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
   exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
   exporterPdfHeader: { text: "My Header", style: 'headerStyle' },
   exporterPdfFooter: function ( currentPage, pageCount ) {
   return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
   },
   exporterPdfCustomFormatter: function ( docDefinition ) {
   docDefinition.styles.headerStyle = { fontSize: 22, bold: true };
   docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
   return docDefinition;
   },
   exporterPdfOrientation: 'portrait',
   exporterPdfPageSize: 'LETTER',
   exporterPdfMaxGridWidth: 500,
   exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
   onRegisterApi: function(gridApi){
   $scope.gridApi = gridApi;
   }
   };
   //*/


  // $scope.loadAtt = function () {

  //  console.log($scope.dateStart.toLocaleDateString());

  $q.all({
    participants: training.getParticipants({id: $scope.trainingId}).$promise,
    participation: training.getParticipation({id: $scope.trainingId}).$promise,
    entries: training.getAllAbsentees({
      id: $scope.trainingId,
      endDate: $scope.dateEnd.getTime()
    }).$promise
  }).then(function (values) {
    var participants = values.participants;
    var participation = values.participation;
    var entries = values.entries;

    //sort by date
    entries.sort(function (a, b) {
      var c = new Date(a.beginTime);
      var d = new Date(b.beginTime);
      return c - d;
    });

    var ajArray = [];

    participants.forEach(function (participant) {
      var newUserRow = {};
      newUserRow.userName = participant.name + " " + participant.surname;
      newUserRow.userId = participant.id;
      var newUserAttendanceArray = [];

      entries.forEach(function (entry) {
        //by default suppose, that the user was present
        var entryAttendance = {entryId: entry.id, state: PRESENT};

        //check whether the user was registered to training for this entry
        for (var i = 0; i < participation.length; i++) {
          if (participation[i].userId === participant.id) {
            if (participation[i].beginDay > entry.beginTime || (participation[i].endDay != null && participation[i].endDay < entry.beginTime)) {
              entryAttendance.state = NOT_ATTEND;
              entryAttendance.reason = 'This user has not attended this training at all';
            }
            break;
          }
        }

        //check list with absentees for this entry
        var absenteesForEntry = entry.absentees;
        for (var j = 0; j < absenteesForEntry.length; j++) {
          if (absenteesForEntry[j].userId == participant.id) {
            //found that user was absent
            entryAttendance.state = ABSENT;
            entryAttendance.id = absenteesForEntry[j].id;
            entryAttendance.reason = absenteesForEntry[j].reason;
            break;
          }
        }
        newUserAttendanceArray.push(entryAttendance);
      });
      newUserRow.attendance = newUserAttendanceArray;
      ajArray.push(newUserRow);
    });

    $scope.entries = entries;
    $scope.ajArray = ajArray;

    // ******** ui-grid section ******* //
    var columnDef = [
      {name: 'user.userName', displayName: "Name", width: 200, pinnedLeft: true}
    ], data = [], gridApi, options = {
      onRegisterApi: function (_gridApi) {
        gridApi = _gridApi;
      },

//exporting
      enableSelectAll: true,
      enableGridMenu: true,
      exporterCsvFilename: 'myFile.csv',
      exporterPdfDefaultStyle: {fontSize: 9},
      exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
      exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
      exporterPdfHeader: {text: "My Header", style: 'headerStyle'},
      exporterPdfFooter: function (currentPage, pageCount) {
        return {text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle'};
      },
      exporterPdfCustomFormatter: function (docDefinition) {
        docDefinition.styles.headerStyle = {fontSize: 22, bold: true};
        docDefinition.styles.footerStyle = {fontSize: 10, bold: true};
        return docDefinition;
      },
      exporterPdfOrientation: 'landscape',
      exporterPdfPageSize: 'LETTER',
      exporterPdfMaxGridWidth: 500,
      exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),

//exporting

    };
    //dynamic columns

    entries.forEach(function (entity, index, array) {
      var key = entity.id.toString();
      columnDef.push({
        id: key,
        field: key + ".state",
        displayName: $filter('date')(entity.beginTime, 'dd-MM-yy'),
        width: 80,
        cellClass: 'text-center',
        enableSorting: false,
        enableColumnMenu: false,
        cellTemplate: '<div data-toggle="tooltip" title="{{row.entity[col.colDef.id].reason}}" ng-click="grid.appScope.cellClickHandler(row,col)"><span ng-bind-html="row.entity[col.colDef.id].state"></span></div>',

      });
    });
    options.columnDefs = columnDef;

    //dynamic rows
    ajArray.forEach(function (user, index, array) {
      var entity = {};
      entity.user = user;
      user.attendance.forEach(function (attendance, index, array) {
        entity[attendance.entryId] = attendance;
      });
      data.push(entity);
    });

    options.data = data;

    $scope.gridAttendanceOptions = options;


    // load rows asynchronously - HACK! need manual refresh by some reason - please investigate!
    $interval(function () {
      gridApi.grid.refresh();
    }, 1500, 70);
  });
  // }

  $scope.cellClickHandler = function (row, col) {
    var attend = row.entity[col.colDef.id];
    var user = row.entity.user;

    if (!isActionAllowed(user)) {
      return;
    }
    $scope.user = user;
    $scope.attend = attend;
    switch (attend.state) {
      case ABSENT:
        //delete or edit absence
        if (isListener(user.id)) {
          //listener cannot edit or delete absence
          return;
        }
        showEditAbsenceDialog();
        break;
      case PRESENT:
        //add absence
        showAddAbsenceDialog();
        break;
      case NOT_ATTEND:
        //is not editable
        return;
        break;
      default:
        return;
    }
  };

  function isActionAllowed(user) {
    //admin, trainer for this training and listener for his records can edit journal
    if ($rootScope.isAdmin() || isMentor() || isListener(user.id)) {
      return true;
    }
    return false;
  }

  //TODO take this method from trainingCtrl
  function isMentor() {
    return $localStorage.userData.id === $scope.training.trainer.id;
  };

  function isListener(editUserId) {
    return $localStorage.userData.id === editUserId;
  };


  function showEditAbsenceDialog() {
    ngDialog.open({
      template: "views/popups/editAbsenceInfo.html",
      controller: ['$scope', 'attendanceJournalService', function ($scope, attendanceJournalService) {
        $scope.reason = $scope.attend.reason;
        $scope.editState = 'edit';

        $scope.editAbsence = function () {
          if ($scope.editState == 'edit') {
            attendanceJournalService.editAbsence(getAbsenceDTO($scope.attend.id, $scope.user.userId, $scope.attend.entryId, $scope.reason), function (result) {
              $scope.attend.reason = $scope.reason;
            });
          } else {
            attendanceJournalService.deleteAbsence({absenteeId: $scope.attend.id}, function (result) {
              delete $scope.attend.reason;
              delete $scope.attend.id;
              $scope.attend.state = PRESENT;
            });
          }
          $scope.closeThisDialog();
        }
      }],
      scope: $scope
    })

  };

  function showAddAbsenceDialog() {
    ngDialog.open({
      template: "views/popups/addAbsenceInfo.html",
      controller: ['$scope', 'attendanceJournalService', function ($scope, attendanceJournalService) {
        $scope.reason;
        $scope.addAbsence = function () {
          attendanceJournalService.addAbsence(getNewAbsenceDTO($scope.user.userId, $scope.attend.entryId, $scope.reason), function (result) {
            $scope.attend.id = result.id;
            $scope.attend.reason = result.reason;
            $scope.attend.state = ABSENT;
          });
          $scope.closeThisDialog();
        };
      }],
      scope: $scope
    })
  };

  function getAbsenceDTO(_id, _userId, _entryId, _reason) {
    return {
      id: _id,
      userId: _userId,
      entryId: _entryId,
      reason: _reason
    }
  }

  function getNewAbsenceDTO(_userId, _entryId, _reason) {
    return {
      userId: _userId,
      entryId: _entryId,
      reason: _reason
    }
  }

}]);

