'use strict';

angular.module('frontendApp')
  .constant('Levels', [
      { Name: 'PreInt'},
      { Name: 'Int'},
      { Name: 'UppInt'},
      { Name: 'Ad'}
    ]
  )
  .constant('Marks', [
      { grade: 0},
      { grade: 1},
      { grade: 2},
      { grade: 3},
      { grade: 4}
  ]
);

