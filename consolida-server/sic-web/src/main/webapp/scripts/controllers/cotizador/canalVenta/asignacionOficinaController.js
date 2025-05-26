'use strict';
angular.module('theme.core.templates')
        .controller('asignacionOficinaController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, pinesNotifications) {
        	
        	$scope.oficinas = [];
        	$scope.rowFilter={};

        	 $scope.cargaInicial = function(){
          		  
          		  $http.get(CONFIG.APIURL + "/cotizador/oficinasCanalVenta.json").then(
          					function(data) {
          						
          						$scope.oficinas = data.data;
          						
          					}, function(data) {
          						console.log("error --> " + data);
          					});
          	  }
          	  
          	  $scope.cargaInicial();
          	  
          	  
          	  
          	 $scope.editarOficinaCanalVenta = function(row){
          		  
          		  $http.post(CONFIG.APIURL + "/cotizador/editarOficinaCanalVenta.json", row).then(
   				function(data) {
   					
   					$location.path('/cotizador/canalVenta/oficina');
   					
   				}, function(data) {
   					console.log("error --> " + data);
   				});
          	  }
          	 
          	 
          	 $scope.regresar = function(){
          		 $location.path("/cotizador/canalVenta/consultaCanalVenta");
          	 }
});