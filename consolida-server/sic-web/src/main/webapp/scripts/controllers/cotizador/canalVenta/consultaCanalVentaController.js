'use strict';
angular.module('theme.core.templates')
        .controller('consultaCanalVentaController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, pinesNotifications) {
        	
        	$scope.canalesVenta = [];
        	$scope.filtroCompleto ='';
        	$scope.cvFilter ={};
        	$scope.oficinaSeleccionada = {};
        	
        	
        	$scope.cargaInicial = function(){
       		  
       		  $http.get(CONFIG.APIURL + "/cotizador/canalesVenta.json").then(
       					function(data) {
       						
       						$scope.canalesVenta = data.data;
       						
       					}, function(data) {
       						console.log("error --> " + data);
       					});
       		  
       		$http.get(CONFIG.APIURL + "/cotizador/catalogoCanalesVenta.json").then(
   					function(data) {
   						
   						$scope.oficinas = data.data.oficinas;
   						
   					}, function(data) {
   						console.log("error --> " + data);
   					});
       	  }
       	  
       	  $scope.cargaInicial();
       	  
       	  
       	  $scope.editarCanalVenta = function(row){
       		  
       		  $http.post(CONFIG.APIURL + "/cotizador/editarCanalVenta.json", row).then(
				function(data) {
					
					$location.path('/cotizador/canalVenta/canalVenta');
					
				}, function(data) {
					console.log("error --> " + data);
				});
       	  }
       	  
//       	$scope.asignarOficinaCanalVenta = function(row){
//     		  
//     		  $http.post(CONFIG.APIURL + "/cotizador/editarCanalVenta.json", row).then(
//				function(data) {
//					
//					$location.path('/cotizador/canalVenta/asignacionOficina');
//					
//				}, function(data) {
//					console.log("error --> " + data);
//				});
//     	  }
       	  
       	  $scope.eliminarCanalVenta = function(){
       		  
       	  }
       	  
       	  
       	$scope.modalOficina = function(row){
       		$scope.canalVenta = angular.copy(row);
       		$scope.oficinaSeleccionada = angular.copy(row.oficina);
       		
       		$("#modalOficina").modal('show');
       		
       	}
       	
       	$scope.guardarOficina = function(){
       		$scope.canalVenta.oficina.idOficina =  $scope.oficinaSeleccionada.idOficina;

				
				  
				  
       	$http.post(CONFIG.APIURL + "/cotizador/asignarOficinaCanalVenta.json", $scope.canalVenta).then(
				function(data) {
					$scope.canalesVenta = data.data;
					
					 pinesNotifications.notify({
					        title: 'Oficina',
					        text: 'Se ha asignado la oficina exitosamente!! ',
					        type: 'success'
					      });
					 
					
				}, function(data) {
					console.log("error --> " + data);
					 pinesNotifications.notify({
					        title: 'Error',
					        text: 'El formulario contiene un error',
					        type: 'error'
					      });
				});
				
       	}

       		

       
	    
	     
      
       	  
});