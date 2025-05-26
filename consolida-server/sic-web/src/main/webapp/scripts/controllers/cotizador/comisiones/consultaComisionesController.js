'use strict';
angular.module('theme.core.templates')
        .controller('consultaComisionesController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, pinesNotifications) {
        	
        	$scope.comisionistas = [];
        	$scope.filtroCompleto ='';
        	$scope.cvFilter ={};
        	 $scope.isVisibleSeccionEditarComisionista = false;
        	
        	$scope.cargaInicial = function(){
       		  
       		  $http.get(CONFIG.APIURL + "/comisiones/listComisionistas.json").then(
       					function(data) {
       						
       						$scope.comisionistas = data.data;
       						
       					}, function(data) {
       						console.log("error --> " + data);
       					});
       		  
       	/*	$http.get(CONFIG.APIURL + "/comisiones/catalogoComisionistas.json").then(
   					function(data) {
   						
   						$scope.oficinas = data.data.oficinas;
   						
   					}, function(data) {
   						console.log("error --> " + data);
   					});*/
       	  }
       	  
       	  $scope.cargaInicial();
       	  
       	  
       	  $scope.editarComisionista = function(row){
       		  
       		  $http.post(CONFIG.APIURL + "/comisiones/editarComisionista.json", row).then(
				function(data) {
			      	 $scope.isVisibleSeccionEditarComisionista = true;
				//	$location.path('/cotizador/canalVenta/canalVenta');
					
				}, function(data) {
					console.log("error --> " + data);
				});
       	  }
       	  
       	  
       	  $scope.eliminarCanalVenta = function(){
       		  
       	  }
       	  
       	  
      
      
       	  
});