angular.module('theme')
  .controller('registroNominasCtrl', ['$scope', '$theme', '$timeout','$http','CONFIG', function($scope, $theme, $timeout,$http,CONFIG) {
    'use strict';

    $scope.IsVisible = false;
    
    $scope.ShowHide = function(){
        $scope.IsVisible = true;
    }

    
    $scope.ShowColaboradores = function(){
    	$scope.regColaboradores = true;
    }
    


  }]);
