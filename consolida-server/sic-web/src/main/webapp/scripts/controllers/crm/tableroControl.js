angular.module('theme')
  .controller('tableroControlCtrl', ['$scope', '$theme', '$timeout','$http','CONFIG', function($scope, $theme, $timeout,$http,CONFIG) {
    'use strict';

    
    $scope.gridPostulantes = {
            enableColumnResizing : true,
            paginationPageSizes : [ 10, 25, 50, 100 ],
            enableFiltering : true,
            paginationPageSize : 10
        };
    
    
    $scope.gridPostulantes.columnDefs = [
    	{ name: "Suresh Dasari", age: 30, location: 'Chennai' },
    	{ name: "Rohini Alavala", age: 29, location: 'Chennai' } ];
  }]);
