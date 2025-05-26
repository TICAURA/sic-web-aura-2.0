'use strict';
angular.module('theme.core.templates')
  .controller('registroFondoController',  function($scope, $location, $timeout,$http, CONFIG, $bootbox,$log) {

	$scope.regPatronas = false;
	$scope.desaparaceCancelar = true;
	$scope.desaparaceRegistrarPatr = true;
	$scope.desaparaceGuardar= true;
	
    $scope.muestraPatrona = function(){
    	$scope.regPatronas = true;
    	$scope.desaparaceCancelar = false;
    	$scope.desaparaceRegistrarPatr = false;
    	$scope.desaparaceGuardar= false;
    }
    
    $scope.hidePatrona = function(){
    	$scope.regPatronas = false;
    	$scope.desaparaceCancelar = true;
    	$scope.desaparaceRegistrarPatr = true;
    	$scope.desaparaceGuardar= true;
    }


    $scope.cancelar = function() {
    	location.href=CONFIG.APIURL+"#/crm/fondo/consultaFondo";
    }
 
  });
