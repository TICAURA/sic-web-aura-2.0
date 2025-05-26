'use strict';
angular.module('theme.core.templates')
  .controller('nominasCotizadorController',  function($scope, $location, $timeout,$http, CONFIG, $bootbox,$log) {

    $scope.IsVisible = false;
    $scope.IsVisible2 = false;
    $scope.regColaboradores = false;
    
    $scope.ShowHide = function(){
        $scope.IsVisible = true;
        $scope.IsVisible2 = false;
        $scope.regColaboradores = false;
    }
    
    $scope.ShowHide2 = function(){
        $scope.IsVisible2 = true;
        $scope.IsVisible = false;
        $scope.regColaboradores = false;
    }
    
    $scope.ShowColaboradores = function(){
    	$scope.regColaboradores = true;
    }
    
    

    // combo de tipo de pago
    $scope.tipoPago = {
    	      selected: 'Semanal'
    };
    
    $scope.$watch('tipoPago.selected', function(newVal, oldVal) {});
    	    
	    $scope.getTiposDePago = function(search) {
	     var newSupes = $scope.tipoPago.slice();
	      if (search && newSupes.indexOf(search) === -1) {
	        newSupes.unshift(search);
	      }
	      return newSupes;
	    }
	    
	    $scope.tipoPago = [
	      'Semanal',
	      'Catorcenal',
	      'Quincenal',
	      'Mensual',
	      'Anual'
	    ].sort();

  });
